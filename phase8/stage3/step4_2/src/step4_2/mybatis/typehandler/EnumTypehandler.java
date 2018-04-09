package step4_2.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import step1.share.domain.entity.club.RoleInClub;

public class EnumTypehandler implements TypeHandler<RoleInClub>{

	@Override
	public RoleInClub getResult(ResultSet rs, String columnName) throws SQLException {
		//
		return valueOfRole(rs.getString(columnName));
	}

	@Override
	public RoleInClub getResult(ResultSet rs, int columnIndex) throws SQLException {
		//
		return valueOfRole(rs.getString(columnIndex));
	}

	@Override
	public RoleInClub getResult(CallableStatement cs, int coumnIndex) throws SQLException {
		//
		return valueOfRole(cs.getString(coumnIndex));
	}

	@Override
	public void setParameter(PreparedStatement prep, int i, RoleInClub role, JdbcType jdbcType) throws SQLException {
		//
		prep.setString(i, role.toString());
	}

	private RoleInClub valueOfRole(String role) {
		//
		RoleInClub roleInClub = null;
		switch (role) {
		case "Member":
			roleInClub = RoleInClub.Member;
			break;
		case "President":
			roleInClub = RoleInClub.President;
			break;
		}
		return roleInClub;
	}
}
