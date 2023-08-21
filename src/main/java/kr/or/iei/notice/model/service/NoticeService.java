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
	
	//공지사항 리스트
	public NoticeListData selectNoticeList(int reqPage) {
		int numPerPage = 5;
		int endPage = reqPage * numPerPage;
		int startPage = endPage-numPerPage+1;
		List noticeList = noticeDao.selectNoticeList(startPage,endPage);
		
		//공지사항 총 게시글
		int totalNum = noticeDao.selectNoticeTotalNum();
		int totalPage = totalNum%numPerPage ==0 ? totalNum/numPerPage : totalNum/numPerPage+1;
		
		int pageNaviSize =5;
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		
		String pageNavi ="<ul>";
		if(pageNo !=1) {
			pageNavi +="<li>";
			pageNavi +="<a href='/notice/list?reqPage="+(pageNo-1)+"'>";
			pageNavi +="<span class='material-icons-outlined'>navigate_before</span>";
			pageNavi +="</a>";
			pageNavi +="</li>";
		}
		for(int i=0;i<pageNaviSize;i++) {
			if(pageNo == reqPage) {
				pageNavi +="<li>";
				pageNavi +="<a href='/notice/list?reqPage="+(pageNo)+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi +="<li>";
				pageNavi +="<a href='/notice/list?reqPage="+(pageNo)+"'>";
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
				pageNavi +="<a href='/notice/list?reqPage="+(pageNo)+"'>";
				pageNavi +="<span class='material-icons-outlined'>navigate_next</span>";
				pageNavi +="</a>";
				pageNavi +="</li>";
			}
			pageNavi +="</ul>";
			
			NoticeListData nld = new NoticeListData(noticeList,pageNavi);
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
}
