package kr.or.iei.product.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductRowMapper implements RowMapper<Product>{

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product p = new Product();
		p.setProductContent(rs.getString("product_content"));
		p.setProductDay(rs.getInt("product_day"));
		p.setProductEnd(rs.getString("product_end"));
		p.setProductName(rs.getString("product_name"));
		p.setProductNo(rs.getInt("product_no"));
		p.setProductPrice(rs.getInt("product_price"));
		p.setProductStart(rs.getString("product_start"));
		p.setProductStock(rs.getInt("product_stock"));
		p.setProductFilpath(rs.getString("product_filepath"));
		return p;
	}

}
