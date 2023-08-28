package kr.or.iei.facility.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class FacilityFavoriteRowMapper implements RowMapper<FacilityFavorite>{

	@Override
	public FacilityFavorite mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		FacilityFavorite f= new FacilityFavorite();
		f.setFavoriteFacilityNo(rs.getInt("favorite_facility_no"));
		f.setFavoriteMemberNo(rs.getInt("favorite_member_no"));
		return f;
	}
	
}
