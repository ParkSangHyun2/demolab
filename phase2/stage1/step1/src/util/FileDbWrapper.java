package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import com.google.gson.Gson;

public class FileDbWrapper {
	//
	private static final String DEFAULT_DELIMETER = "||";

	private Map<String, Integer> keyIndexMap;
	private String fileName;
	private String delimiter;
	private File file;

	public FileDbWrapper(String folderName, String fileName) {
		//
		this(folderName, fileName, DEFAULT_DELIMETER);
	}

	public FileDbWrapper(String folderName, String fileName, String delimiter) {
		//
		this.delimiter = delimiter;
		this.fileName = fileName;
		this.file = ResourceUtil.getFile("filedb", folderName, fileName);
	}

	public void setKeyIndexMap(Map<String, Integer> keyIndexMap) {
		//
		this.keyIndexMap = keyIndexMap;
	}

	public int getKeyIndex(String key) {
		//
		return keyIndexMap.get(key);
	}

	public boolean hasValueOf(String key, String value, String line) {
		//
		Integer keyIndex = this.keyIndexMap.get(key);

		if (keyIndex == null) {
			return false;
		}

		StringTokenizer tokenizer = new StringTokenizer(line, delimiter);

		for (int i = 0; i < keyIndex; i++) {
			tokenizer.nextToken();
		}

		String token = tokenizer.nextToken();
		if (value.equals(token)) {
			return true;
		} else {
			return false;
		}
	}

	public String getName() {
		return fileName;
	}

	public File getFile() {
		return file;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public File create() throws IOException {
		//
		if (this.file == null) {
			this.file = new File(fileName);
		}

		if (file.exists()) {
			return file;
		} else {
			if (file.createNewFile()) {
				return file;
			}
		}

		throw new RuntimeException("Can't create a file: " + fileName);
	}

	public Object convertTo(String readLine, Class<?> clazz) {
		//
		StringTokenizer tokenizer = new StringTokenizer(readLine, getDelimiter());
		int tokenCount = this.keyIndexMap.size();

		for (int i = 0; i < tokenCount; i++) {
			tokenizer.nextToken();
		}

		String json = tokenizer.nextToken();
		return ((new Gson()).fromJson(json, clazz));
	}

	public String convertFrom(Object object) {
		//
		StringBuilder builder = new StringBuilder();
		Iterator<String> keyIter = this.keyIndexMap.keySet().iterator();
		while (keyIter.hasNext()) {
			String keyName = keyIter.next();
			builder.append(callGetMethod(object, keyName)).append(getDelimiter());
		}

		builder.append((new Gson()).toJson(object));

		return builder.toString();
	}

	private String callGetMethod(Object object, String attrName) {
		//
		String result = null;
		try {
			String methodName = "get" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
			Method getMethod = object.getClass().getMethod(methodName);
			result = (String) getMethod.invoke(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		return result;
	}

	public BufferedReader requestReader() throws IOException {
		//
		return new BufferedReader(new FileReader(create()));
	}

	public RandomAccessFile requestAccessReader() throws IOException {
		//
		return new RandomAccessFile(this.create(), "rw");
	}

	public FileWriter requestFileWriter() throws IOException {
		//
		return new FileWriter(create(), true);
	}
	
	public BufferedWriter requestBufferedFileWriter() throws IOException{
		//
		return new BufferedWriter(new FileWriter(create(), true));
	}

	public PrintWriter requestPrintWriter() throws IOException {
		//
		return new PrintWriter(requestFileWriter());
	}

	public boolean renameTo(FileDbWrapper fileWrapper) {
		//
		return this.file.renameTo(fileWrapper.getFile());
	}

	public boolean delete() {
		//
		return file.delete();
	}
}