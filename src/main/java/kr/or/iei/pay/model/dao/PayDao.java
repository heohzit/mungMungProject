package kr.or.iei.pay.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.pay.model.vo.Pay;
import kr.or.iei.product.model.vo.Product;
import kr.or.iei.product.model.vo.ProductRowMapper;

@Repository
public class PayDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private ProductRowMapper productRowMapper;


	public int insertPay(Pay p) {
		String query = "insert into pay values(pay_seq.nextval, ?, ?, ?, ?, 1, ?, ?, ?)";
		Object[] params = {p.getPayProductNo(), p.getPayMemberNo(), p.getPayPrice(), p.getPayDate(), p.getPayBuyNo(), p.getPayStart(), p.getPayEnd()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int updateProductStock(int productNo) {
		String query = "update product set product_stock = (product_stock - 1) where product_no = ?";
		Object[] params = {productNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public Product checkStock(int productNo) {
		String query = "select * from product where product_no = ?";
		Product product = jdbc.queryForObject(query, productRowMapper, productNo);
		return product;
	}

	public int cancelPay(int payNo) {
		String query = "update pay set pay_status = 2 where pay_no = ?";
		Object[] params = {payNo};
		int result = jdbc.update(query, params);
		return result;
	}
}
