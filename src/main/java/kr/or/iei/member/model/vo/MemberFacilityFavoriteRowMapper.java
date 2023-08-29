package kr.or.iei.member.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MemberFacilityFavoriteRowMapper implements RowMapper<MemberFacilityFavorite> {

	@Override
	public MemberFacilityFavorite mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberFacilityFavorite mff = new MemberFacilityFavorite();
		mff.setEnrollDate(rs.getString("enroll_date"));
		mff.setMemberEmail(rs.getString("member_email"));
		mff.setMemberId(rs.getString("member_id"));
		mff.setMemberLevel(rs.getInt("member_level"));
		mff.setMemberPhone(rs.getString("member_phone"));
		mff.setMemberPw(rs.getString("member_pw"));
		mff.setMemberName(rs.getString("member_name"));
		mff.setMemberNo(rs.getInt("member_no"));
		mff.setFavoriteFacilityNo(rs.getInt("favorite_facility_no"));
		mff.setFavoriteMemberNo(rs.getInt("favorite_member_no"));	
		mff.setFacilityAddr(rs.getString("facility_addr"));
		mff.setFacilityCase(rs.getInt("facility_case"));
		mff.setFacilityHomepage(rs.getString("facility_homepage"));
		mff.setFacilityInfo(rs.getString("facility_info"));
		mff.setFacilityLat(rs.getDouble("facility_lat"));
		mff.setFacilityLng(rs.getDouble("facility_lng"));
		mff.setFacilityMajor(rs.getString("facility_major"));
		mff.setFacilityName(rs.getString("facility_name"));
		mff.setFacilityNo(rs.getInt("facility_no"));
		mff.setFacilityNotice(rs.getString("facility_notice"));
		mff.setFacilityPhone(rs.getString("facility_phone"));
		mff.setFacilityPrice(rs.getString("facility_price"));
		mff.setFacilityRegion(rs.getInt("facility_region"));
		mff.setFacilityTime(rs.getString("facility_time"));
		mff.setFacilityWriter(rs.getInt("facility_writer"));
		return mff;

	}

}
