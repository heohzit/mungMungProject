package kr.or.iei.product.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.board.model.vo.BoardListData;
import kr.or.iei.facility.model.vo.Facility;
import kr.or.iei.pay.model.vo.Pay;
import kr.or.iei.product.model.dao.ProductDao;
import kr.or.iei.product.model.vo.Product;
import kr.or.iei.product.model.vo.ProductListData;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;

	public ProductListData selectProductList(int reqPage) {
		int numPerPage = 8;
		int end = reqPage * numPerPage;
		int start = end-numPerPage+1;
		List productList = productDao.selectProductList(start,end);
		//패키지 총 게시글
		int totalCount = productDao.selectProductTotalNum();
		int totalPage = totalCount%numPerPage == 0 ? totalCount/numPerPage : totalCount/numPerPage+1;
		int pageNaviSize =5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		String pageNavi ="<ul class='pagination'>";
  		if(pageNo !=1) {
  			pageNavi +="<li>";
  			pageNavi +="<a class='page-btn' href='/product/list?reqPage="+(pageNo-1)+"'>";
  			pageNavi +="<span class='material-icons'>arrow_back_ios_new</span>";
  			pageNavi +="</a>";
  			pageNavi +="</li>";
  		}
  		for(int i=0;i<pageNaviSize;i++) {
  			if(pageNo == reqPage) {
  				pageNavi +="<li>";
  				pageNavi +="<a class='page-btn select-page' href='/product/list?reqPage="+(pageNo)+"'>";
  				pageNavi += pageNo;
  				pageNavi += "</a>";
  				pageNavi += "</li>";
  			}else {
  				pageNavi +="<li>";
  				pageNavi +="<a class='page-btn' href='/product/list?reqPage="+(pageNo)+"'>";
  				pageNavi += pageNo;
  				pageNavi += "</a>";
  				pageNavi += "</li>";
  			}
  			pageNo++;
  			if(pageNo>totalPage) {
  				break;
  			}
  		}		
  			if(pageNo <= totalPage) {
  				pageNavi +="<li>";
  				pageNavi +="<a class='page-btn' href='/product/list?reqPage="+(pageNo)+"'>";
  				pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
  				pageNavi +="</a>";
  				pageNavi +="</li>";
  			}
  			pageNavi +="</ul>";
  			ProductListData pld = new ProductListData(productList, pageNavi);
  			return pld;
		
	}

	@Transactional
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

	@Transactional
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

	@Transactional
	public int updateProduct(Product p) {
		int result = productDao.updateProduct(p);
		if(result > 0) {
			return result;
		}else {
			return 0;
		}
	}

	public List selectProductList() {
		List productList = productDao.selectProductList();
		return productList;
	}

}
