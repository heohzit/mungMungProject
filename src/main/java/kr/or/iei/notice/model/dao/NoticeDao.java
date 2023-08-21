package kr.or.iei.notice.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeRowMapper;

@Repository
public class NoticeDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private NoticeRowMapper noticeRowMapper;
	
	//공지사항 리스트
	public List selectNoticeList(int startPage, int endPage) {
		String query = "select * from (select rownum as rnum , n.*,member_id from (select * from notice order by 1 desc)n join member on (member_no = notice_writer))where rnum between ? and ?";
		List noticeList = jdbc.query(query, noticeRowMapper,startPage,endPage);
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
		System.out.println(noticeNo);
		String query = "select * from notice join member on (member_no = notice_writer) where notice_no = ?";
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
		Object[] params = {n.getMemberId(),n.getNoticeTitle(),n.getNoticeContent()};
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
}
