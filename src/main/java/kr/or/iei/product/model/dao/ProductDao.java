package kr.or.iei.product.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.product.model.vo.Product;
import kr.or.iei.product.model.vo.ProductRowMapper;

@Repository
public class ProductDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private ProductRowMapper productRowMapper;
	
	public List selectProductList() {
	String query = "select * from product";
	List list = jdbc.query(query, productRowMapper);
		return list;
	}

	public int insertProduct(Product p) {
		String query = "insert into product values(product_seq.nextval,?,?,?,?,?,?,?,?)";
		Object[] params = {p.getProductName(),p.getProductContent(),p.getProductPrice(),p.getProductStock(),p.getProductStart(),p.getProductEnd(),p.getProductDay(),p.getProductFilpath()};
		int result = jdbc.update(query,params);
		return result;
	}
}
