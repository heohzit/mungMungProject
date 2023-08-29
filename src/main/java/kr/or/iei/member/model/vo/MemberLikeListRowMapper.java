package kr.or.iei.member.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MemberLikeListRowMapper implements RowMapper<MemberLikeList> {

	@Override
	public MemberLikeList mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberLikeList mll = new MemberLikeList();
		mll.setEnrollDate(rs.getString("enroll_date"));
		mll.setMemberEmail(rs.getString("member_email"));
		mll.setMemberId(rs.getString("member_id"));
		mll.setMemberLevel(rs.getInt("member_level"));
		mll.setMemberPhone(rs.getString("member_phone"));
		mll.setMemberPw(rs.getString("member_pw"));
		mll.setMemberName(rs.getString("member_name"));
		mll.setMemberNo(rs.getInt("member_no"));
		mll.setFavoriteFacilityNo(rs.getInt("favorite_facility_no"));
		mll.setFavoriteMemberNo(rs.getInt("favorite_member_no"));	
		mll.setFacilityAddr(rs.getString("facility_addr"));
		mll.setFacilityCase(rs.getInt("facility_case"));
		mll.setFacilityHomepage(rs.getString("facility_homepage"));
		mll.setFacilityInfo(rs.getString("facility_info"));
		mll.setFacilityLat(rs.getDouble("facility_lat"));
		mll.setFacilityLng(rs.getDouble("facility_lng"));
		mll.setFacilityMajor(rs.getString("facility_major"));
		mll.setFacilityName(rs.getString("facility_name"));
		mll.setFacilityNo(rs.getInt("facility_no"));
		mll.setFacilityNotice(rs.getString("facility_notice"));
		mll.setFacilityPhone(rs.getString("facility_phone"));
		mll.setFacilityPrice(rs.getString("facility_price"));
		mll.setFacilityRegion(rs.getInt("facility_region"));
		mll.setFacilityTime(rs.getString("facility_time"));
		mll.setFacilityWriter(rs.getInt("facility_writer"));
		return mll;

	}

}
