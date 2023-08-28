package kr.or.iei.pay.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.pay.model.vo.Pay;

@Repository
public class PayDao {
	@Autowired
	private JdbcTemplate jdbc;

	public int insertPay(Pay p) {
		String query = "insert into pay values(pay_seq_nextval, ?, ?, ?, ?, 1, ?)";
		Object[] params = {p.getPayProductNo(), p.getPayMemberNo(), p.getPayPrice(), p.getPayDate(), p.getPayBuyNo()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int updateProductStock(int productNo) {
		String query = "update product set product_stock = (product_stock - 1) where product_no = ?";
		Object[] params = {productNo};
		int result = jdbc.update(query, params);
		return result;
	}
}
