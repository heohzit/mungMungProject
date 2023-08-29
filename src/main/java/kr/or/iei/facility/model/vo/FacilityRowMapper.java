package kr.or.iei.facility.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FacilityRowMapper implements RowMapper<Facility>{

	@Override
	public Facility mapRow(ResultSet rs, int rowNum) throws SQLException {
		Facility f = new Facility();
		f.setFacilityAddr(rs.getString("facility_addr"));
		f.setFacilityCase(rs.getInt("facility_case"));
		f.setFacilityHomepage(rs.getString("facility_homepage"));
		f.setFacilityInfo(rs.getString("facility_info"));
		f.setFacilityLat(rs.getDouble("facility_lat"));
		f.setFacilityLng(rs.getDouble("facility_lng"));
		f.setFacilityMajor(rs.getString("facility_major"));
		f.setFacilityName(rs.getString("facility_name"));
		f.setFacilityNo(rs.getInt("facility_no"));
		f.setFacilityNotice(rs.getString("facility_notice"));
		f.setFacilityPhone(rs.getString("facility_phone"));
		f.setFacilityPrice(rs.getString("facility_price"));
		f.setFacilityRegion(rs.getInt("facility_region"));
		f.setFacilityTime(rs.getString("facility_time"));
		f.setFacilityWriter(rs.getInt("facility_writer"));
		
		return f;
	}

}
