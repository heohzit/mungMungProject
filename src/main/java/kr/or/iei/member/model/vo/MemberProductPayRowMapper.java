package kr.or.iei.member.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MemberProductPayRowMapper implements RowMapper<MemberProductPay>{

	@Override
	public MemberProductPay mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberProductPay mpp = new MemberProductPay();
		mpp.setEnrollDate(rs.getString("enroll_date"));
		mpp.setMemberEmail(rs.getString("member_email"));
		mpp.setMemberId(rs.getString("member_id"));
		mpp.setMemberLevel(rs.getInt("member_level"));
		mpp.setMemberPhone(rs.getString("member_phone"));
		mpp.setMemberPw(rs.getString("member_pw"));
		mpp.setMemberName(rs.getString("member_name"));
		mpp.setMemberNo(rs.getInt("member_no"));
		mpp.setProductContent(rs.getString("product_content"));
		mpp.setProductDay(rs.getInt("product_day"));
		mpp.setProductEnd(rs.getString("product_end"));
		mpp.setProductName(rs.getString("product_name"));
		mpp.setProductNo(rs.getInt("product_no"));
		mpp.setProductPrice(rs.getInt("product_price"));
		mpp.setProductStart(rs.getString("product_start"));
		mpp.setProductStock(rs.getInt("product_stock"));
		mpp.setProductFilepath(rs.getString("product_filepath"));
		mpp.setPayBuyNo(rs.getString("pay_buy_no"));
		mpp.setPayDate(rs.getString("pay_date"));
		mpp.setPayEnd(rs.getString("pay_end"));
		mpp.setPayMemberNo(rs.getInt("pay_member_no"));
		mpp.setPayNo(rs.getInt("pay_no"));
		mpp.setPayPrice(rs.getInt("pay_price"));
		mpp.setPayProductNo(rs.getInt("pay_product_no"));
		mpp.setPayStart(rs.getString("pay_start"));
		mpp.setPayStatus(rs.getInt("pay_status"));
		return mpp;
	}

}
