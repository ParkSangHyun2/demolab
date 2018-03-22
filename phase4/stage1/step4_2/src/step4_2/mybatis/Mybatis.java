package step4_2.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Mybatis {
	//
	private static SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	private final static String RESOURCE = "./mybatis/MybatisConfig.xml";
	private static InputStream inputStream;
	private static SqlSessionFactory factory;

	public static SqlSession openSession() {
		//
		if (factory == null) {
			try {
				inputStream = Resources.getResourceAsStream(RESOURCE);
				factory = builder.build(inputStream);
			} catch (IOException e) {
				//
				System.out.println("Failed.. OpenSession ");
			}
		}
		return factory.openSession();
	}

	public static void closeSession(SqlSession... sessions) {
		//
		for (SqlSession session : sessions) {
			session.close();
		}
	}
}
