package kr.or.iei.notice.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeRowMapper;
import kr.or.iei.notice.model.vo.NoticeSearchRowMapper;

@Repository
public class NoticeDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private NoticeRowMapper noticeRowMapper;
	@Autowired
	private NoticeSearchRowMapper noticeSearchRowMapper;
	
	//공지사항 리스트
	public List selectNoticeList(int start, int end) {
		//String query = "select * from (select rownum as rnum , n.*,member_id from (select * from notice order by 1 desc)n join member on (member_no = notice_writer))where rnum between ? and ?";
		String query = "select * from (select rownum as rnum , n.* from (select * from notice join member on (member.member_no = notice.notice_writer) order by 1 desc)n)where rnum between ? and ?";
		List noticeList = jdbc.query(query, noticeRowMapper,start,end);
		return noticeList;
	}
	
	//공지사항 총 게시글
	public int selectNoticeTotalNum() {
		String query = "select count(*) from notice";
		int totalNum = jdbc.queryForObject(query, Integer.class);
		return totalNum;
	}
	
	
	//공지사항 보기 
	public Notice selectOneNotice(int noticeNo) {
		//String query = "select * from notice join member on (member_no = notice_writer) where notice_no = ?";
		String query = "select * from notice join member on (member.member_no = notice.notice_writer) where notice_no = ?";
		List list = jdbc.query(query, noticeRowMapper,noticeNo);
		return (Notice)list.get(0);
	}
	
	//공지사항 조회수
	public int updateReadCount(int noticeNo) {
		String query = "update notice set notice_read_count =notice_read_count+1 where notice_no=?";
		Object[] params = {noticeNo};
		int count = jdbc.update(query,params);
		return count;
	}
	
	//공지사항 작성
	public int insertNotice(Notice n) {
		String query = "insert into notice values (notice_seq.nextval,?,?,?,to_char(sysdate,'yyyy-mm-dd'),0)";
		Object[] params = {n.getNoticeWriter(),n.getNoticeTitle(),n.getNoticeContent()};
		int result = jdbc.update(query,params);
		return result;
	}

	//공지사항 삭제
	public int deleteNotice(int noticeNo) {
		String query = "delete from notice where notice_no = ?";
		Object[] params = {noticeNo};
		int result = jdbc.update(query,params);
		return result;
	}
	
	//공지사항 수정
	public int updateNotice(Notice n) {
		String query = "update notice set notice_title=? ,notice_content=? where notice_no =?";
		Object[] params = {n.getNoticeTitle(),n.getNoticeContent(),n.getNoticeNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	
	//제목 검색
	public List searchNoticeTitle(int start, int end, String searchName) {
		String query = "select * from (select rownum as rnum , n.* from (select * from notice where notice_title like '%'||?||'%' order by 1 desc)n)where rnum between ? and ?";
		List noticeList = jdbc.query(query, noticeSearchRowMapper,searchName,start,end);
		return noticeList;
	}
	
	//작성자 검색
	public List searchNoticeWriter(int start, int end, String searchName) {
		String query = "select * from (select rownum as rnum , n.* from (select * from notice join member on (member.member_no = notice.notice_writer) where member_id like '%'||?||'%' order by 1 desc)n)where rnum between ? and ?";
		List noticeList = jdbc.query(query, noticeRowMapper,searchName,start,end);
		return noticeList;
	}
	
	//내용 검색
	public List searchNoticeContent(int start, int end, String searchName) {
		String query = "select * from (select rownum as rnum , n.* from (select * from notice where notice_content like '%'||?||'%' order by 1 desc)n)where rnum between ? and ?";
		List noticeList = jdbc.query(query, noticeSearchRowMapper,searchName,start,end);
		return noticeList;
	}
	
	//제목 검색 총 수
	public int searchNoticeTitleTotalNum(String searchName) {
		String query = "select count(*) from notice where notice_title like '%'||?||'%'";
		int totalCount = jdbc.queryForObject(query, Integer.class,searchName);
		return totalCount;
	}
	
	//작성자 검색 총 수
	public int searchNoticeWriterTotalNum(String searchName) {
		String query = "select count(*) from notice join member on (member.member_no = notice.notice_writer) where member_id like '%'||?||'%'";
		int totalCount = jdbc.queryForObject(query, Integer.class,searchName);
		return totalCount;
	}
	
	//내용 검색 총 수
	public int searchNoticeContentTotalNum(String searchName) {
		String query = "select count(*) from notice where notice_content like '%'||?||'%'";
		int totalCount = jdbc.queryForObject(query, Integer.class,searchName);
		return totalCount;
	}

	public List selectNoticeList() {
		String query = "select * from notice order by 1 desc";
		List list = jdbc.query(query, noticeSearchRowMapper);
		return list;
	}
	


}
