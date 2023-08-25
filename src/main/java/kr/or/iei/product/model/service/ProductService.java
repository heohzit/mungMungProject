package kr.or.iei.product.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.iei.product.model.dao.ProductDao;
import kr.or.iei.product.model.vo.Product;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;

	public List selectProductList() {
		List productList = productDao.selectProductList();
		return productList;
	}

	public int insertProduct(Product p) {
		int result = productDao.insertProduct(p);
		if(result > 0) {
			return result;
		}else {
			return 0;			
		}
	}

	public Product selectOneProduct(int productNo) {
		Product p = productDao.selectOneProduct(productNo);
		return p;
	}

	public int deleteProduct(int productNo) {
		int result = productDao.deleteProduct(productNo);
		if(result > 0) {
			return result;
		}else {
			return 0;
		}
	}

	public Product getProduct(int productNo) {
		Product p = productDao.selectOneProduct(productNo);
		return p;
	}

	public int updateProduct(Product p) {
		int result = productDao.updateProduct(p);
		if(result > 0) {
			return result;
		}else {
			return 0;
		}
	}
}
