package kr.or.iei.api;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ApiRowMapper implements RowMapper<Api>{

	@Override
	public Api mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Api api = new Api();
		api.setFacilityNo(rs.getInt("FACILITY_NO"));
		api.setFacilityWriter(rs.getInt("FACILITY_WRITER"));
		api.setFacilityRegion(rs.getInt("FACILITY_REGION"));
		api.setFacilityCase(rs.getInt("FACILITY_CASE"));

		api.setFacilityLat(rs.getDouble("FACILITY_LAT"));
		api.setFacilityLng(rs.getDouble("FACILITY_LNG"));

		api.setFacilityAddr(rs.getString("FACILITY_ADDR"));
		api.setFacilityHomepage(rs.getString("FACILITY_HOMEPAGE"));
		api.setFacilityInfo(rs.getString("FACILITY_INFO"));
		api.setFacilityMajor(rs.getString("FACILITY_MAJOR"));
		api.setFacilityName(rs.getString("FACILITY_NAME"));
		api.setFacilityNotice(rs.getString("FACILITY_NOTICE"));
		api.setFacilityPhone(rs.getString("FACILITY_PHONE"));
		api.setFacilityPrice(rs.getString("FACILITY_PRICE"));
		api.setFacilityTime(rs.getString("FACILITY_CASE"));
		return null;
	}

}