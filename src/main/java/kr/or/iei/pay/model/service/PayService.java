package kr.or.iei.pay.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.pay.model.dao.PayDao;
import kr.or.iei.pay.model.vo.Pay;
import kr.or.iei.product.model.vo.Product;

@Service
public class PayService {
	@Autowired
	private PayDao payDao;

	@Transactional
	public int insertPay(Pay p) {
		int result = payDao.insertPay(p);
		return result;
	}

	@Transactional
	public int updateProductStock(int productNo) {
		int result = payDao.updateProductStock(productNo);
		return result;
	}

	public Product checkStock(int productNo) {
		Product product = payDao.checkStock(productNo);
		return product;
	}

	public int cancelPay(int payNo) {
		int result = payDao.cancelPay(payNo);
		return result;
	}

	public Pay checkPayStatus(int payNo) {
		Pay pay = payDao.checkPayStatus(payNo);
		return null;
	}
}
