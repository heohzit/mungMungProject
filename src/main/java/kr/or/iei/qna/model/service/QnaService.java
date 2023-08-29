package kr.or.iei.qna.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.qna.model.dao.QnaDao;
import kr.or.iei.qna.model.vo.Qna;
import kr.or.iei.qna.model.vo.QnaListData;

@Service
public class QnaService {
	@Autowired
	QnaDao qnaDao;
	
	public int insertQna(Qna q) {
		// TODO Auto-generated method stub
		int result = qnaDao.insertQna(q);
		return result;
	}

	public QnaListData selectQnaList(int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end-numPerPage+1;
		List qnaList = qnaDao.selectQnaList(start, end);
		
		int totalCount = qnaDao.selectQnaTotalNum();
		int totalPage = totalCount%numPerPage == 0 ? totalCount/numPerPage : totalCount/numPerPage+1;
		
		int pageNaviSize =5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		
		String pageNavi ="<ul class='pagination'>";
		if(pageNo !=1) {
			pageNavi +="<li>";
			pageNavi +="<a class='page-btn' href='/notice/list?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>arrow_back_ios_new</span>";
			pageNavi +="</a>";
			pageNavi +="</li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			if(pageNo == reqPage) {
				pageNavi +="<li>";
				pageNavi +="<a class='page-btn select-page' href='/qna/list?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi +="<li>";
				pageNavi +="<a class='page-btn' href='/qna/list?reqPage="+(pageNo)+"'>";
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
				pageNavi +="<a class='page-btn' href='/qna/list?reqPage="+(pageNo)+"'>";
				pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
				pageNavi +="</a>";
				pageNavi +="</li>";
			}
			pageNavi +="</ul>";
			QnaListData qld = new QnaListData(qnaList, pageNavi);
			return qld;
	}

	public Qna selectOneQna(int qnaNo) {
		// TODO Auto-generated method stub
			Qna q = qnaDao.selectOneQna(qnaNo);	
			return q;
	}

	public int deleteQna(int qnaNo) {
		int result = qnaDao.deleteQna(qnaNo);
		if(result > 0) {
			return result;
		}else {
			return 0;
		}
	}

}
