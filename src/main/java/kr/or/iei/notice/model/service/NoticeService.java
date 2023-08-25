package kr.or.iei.notice.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.iei.notice.model.dao.NoticeDao;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeListData;

@Service
public class NoticeService {
	@Autowired
	private NoticeDao noticeDao;
	
	//공지사항 리스트 검색없을때
	public NoticeListData selectNoticeList(int reqPage) {
		int numPerPage = 10;
		int end = reqPage * numPerPage;
		int start = end-numPerPage+1;
		List noticeList = noticeDao.selectNoticeList(start, end);
		//공지사항 총 게시글
		int totalCount = noticeDao.selectNoticeTotalNum();
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
				pageNavi +="<a class='page-btn select-page' href='/notice/list?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi +="<li>";
				pageNavi +="<a class='page-btn' href='/notice/list?reqPage="+(pageNo)+"'>";
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
				pageNavi +="<a class='page-btn' href='/notice/list?reqPage="+(pageNo)+"'>";
				pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
				pageNavi +="</a>";
				pageNavi +="</li>";
			}
			pageNavi +="</ul>";
			NoticeListData nld = new NoticeListData(noticeList, pageNavi);
			return nld;

		}
	
	//공지사항 리스트 검색있을때
	public NoticeListData selectNoticeList(int reqPage,String searchType, String searchName) {
		int numPerPage = 5;
		int end = reqPage * numPerPage;
		int start = end-numPerPage+1;
		
		List noticeList = null;
	    if ("title".equals(searchType)) {
	        noticeList = noticeDao.searchNoticeTitle(start, end, searchName);
	    } else if ("writer".equals(searchType)) {
	        noticeList = noticeDao.searchNoticeWriter(start, end, searchName);
	    } else if ("content".equals(searchType)) {
	        noticeList = noticeDao.searchNoticeContent(start, end, searchName);
	    } 
	    
	    int totalCount=0;
	    if ("title".equals(searchType)) {
	    	totalCount = noticeDao.searchNoticeTitleTotalNum(searchName);
	    } else if ("writer".equals(searchType)) {
	    	totalCount = noticeDao.searchNoticeWriterTotalNum(searchName);
	    } else if ("content".equals(searchType)) {
	    	totalCount = noticeDao.searchNoticeContentTotalNum(searchName);
	    } 
		int totalPage = totalCount%numPerPage == 0 ? totalCount/numPerPage : totalCount/numPerPage+1;
		
		int pageNaviSize =5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		String pageNavi ="<ul class='pagination'>";
		if(pageNo !=1) {
			pageNavi +="<li>";
			pageNavi +="<a class='page-btn' href='/notice/searchNoticeList?reqPage="+(pageNo-1)+"&searchType"+searchType+"&searchName="+searchName+"'>";
			pageNavi += "<span class='material-icons'>arrow_back_ios_new</span>";
			pageNavi +="</a>";
			pageNavi +="</li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			if(pageNo == reqPage) {
				pageNavi +="<li>";
				pageNavi +="<a class='page-btn select-page' href='/notice/searchNoticeList?reqPage="+pageNo+"&searchType="+searchType+"&searchName="+searchName+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi +="<li>";
				pageNavi +="<a class='page-btn' href='/notice/searchNoticeList?reqPage="+pageNo+"&searchType="+searchType+"&searchName="+searchName+"'>";
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
				pageNavi +="<a class='page-btn' href='/notice/searchNoticeList?reqPage="+pageNo+"&searchType="+searchType+"&searchName="+searchName+"'>";
				pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
				pageNavi +="</a>";
				pageNavi +="</li>";
			}
			pageNavi +="</ul>";
			NoticeListData nld = new NoticeListData(noticeList, pageNavi);
			return nld;
		}
	

	//공지사항 보기
	public Notice selectOneNotice(int noticeNo) {
		//공지사항 조회수
		int count = noticeDao.updateReadCount(noticeNo);
		if(count>0) {
			Notice n = noticeDao.selectOneNotice(noticeNo);	
			return n;
		}else {
			return null;
		}
	}
		
	//공지사항 작성
	public int insertNotice(Notice n) {
		int result = noticeDao.insertNotice(n);
		if(result > 0) {
			return result;
		}else {
			return 0;
		}
	}
	
	//공지사항 삭제
	public int deleteNotice(int noticeNo) {
		int result = noticeDao.deleteNotice(noticeNo);
		if(result > 0) {
			return result;
		}else {
			return 0;
		}
	}
	
	//공지사항 보기
	public Notice getNotice(int noticeNo) {
		Notice n = noticeDao.selectOneNotice(noticeNo);
		return n;
	}
	
	//공지사항 수정
	public int updateNotice(Notice n) {
		int result = noticeDao.updateNotice(n);
		if(result > 0) {
			return result;
		}else {
			return 0;
		}
	}




}
