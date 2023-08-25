package kr.or.iei.facility.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FacilityImageRowMapper implements RowMapper<FacilityImage>{
	@Override
	public FacilityImage mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		FacilityImage f = new FacilityImage();
		f.setFacilityFileNo(rs.getInt("facility_file_no"));
		f.setFacilityNo(rs.getInt("facility_no"));
		f.setFacilityFilepath(rs.getString("facility_filepath"));
		return f;
	}
}
