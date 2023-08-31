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
	
	public List selectProductList(int start, int end) {
	String query = "select * from (select rownum as rnum , n.* from (select * from product order by 1 desc)n)where rnum between ? and ?";
	List list = jdbc.query(query, productRowMapper, start, end);
		return list;
	}

	public int insertProduct(Product p) {
		String query = "insert into product values(product_seq.nextval,?,?,?,?,?,?,?,?)";
		Object[] params = {p.getProductName(),p.getProductContent(),p.getProductPrice(),p.getProductStock(),p.getProductStart(),p.getProductEnd(),p.getProductDay(),p.getProductFilepath()};
		int result = jdbc.update(query,params);
		return result;
	}

	public Product selectOneProduct(int productNo) {
		String query = "select * from product where product_no=?";
		List list = jdbc.query(query, productRowMapper,productNo);
		return (Product)list.get(0);
	}

	public int deleteProduct(int productNo) {
		String query = "delete from product where product_no = ?";
		Object[] params = {productNo};
		int result = jdbc.update(query,params);
		return result;
	}

	public int updateProduct(Product p) {
		String query = "update product set product_name=? ,product_content=? ,product_price=? ,product_Stock=? ,product_start=? ,product_end=? ,product_day=? ,product_filepath=? where product_no=? ";
		Object[] params = {p.getProductName(),p.getProductContent(),p.getProductPrice(),p.getProductStock(),p.getProductStart(),p.getProductEnd(),p.getProductDay(),p.getProductFilepath(),p.getProductNo()};
		int result = jdbc.update(query,params);
		return result;
	}

	public int selectProductTotalNum() {
		String query = "select count(*) from product";
		int totalNum = jdbc.queryForObject(query, Integer.class);
		return totalNum;
	}

	public List selectProductList() {
		String query = "select * from product order by 1 desc";
		List list = jdbc.query(query, productRowMapper);
		return list;
	}

}
