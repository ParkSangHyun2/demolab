package step4_2.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class IdTypehandler implements TypeHandler<String>{

	@Override
	public String getResult(ResultSet resultSet, String columnName) throws SQLException {
		//
		return Integer.toString(resultSet.getInt(columnName));
	}

	@Override
	public String getResult(ResultSet resultSet, int columnIndex) throws SQLException {
		//
		return Integer.toString(resultSet.getInt(columnIndex));
	}

	@Override
	public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
		//
		return Integer.toString(cs.getInt(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement prep, int i, String parameter, JdbcType jdbcType) throws SQLException {
		//
		prep.setInt(i, Integer.parseInt(parameter));
	}


}
