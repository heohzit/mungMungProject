package kr.or.iei.facility.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FacilityFileRowMapper implements RowMapper<FacilityFile>{

	@Override
	public FacilityFile mapRow(ResultSet rs, int rowNum) throws SQLException {
		FacilityFile ff = new FacilityFile();
		ff.setFacilityFileNo(rs.getInt("facility_file_no"));
		ff.setFacilityFilepath(rs.getString("facility_filepath"));
		ff.setFacilityNo(rs.getInt("facility_no"));
		return ff;
	}
	
}
