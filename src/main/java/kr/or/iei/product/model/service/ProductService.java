package kr.or.iei.product.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.iei.product.model.dao.ProductDao;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
}
