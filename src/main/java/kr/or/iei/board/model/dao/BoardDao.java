package kr.or.iei.board.model.dao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.board.model.vo.Board;
import kr.or.iei.board.model.vo.BoardComment;
import kr.or.iei.board.model.vo.BoardCommentRowMapper;
import kr.or.iei.board.model.vo.BoardRowMapper;

@Repository
public class BoardDao {
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private BoardRowMapper boardRowMapper;
    @Autowired
    private BoardCommentRowMapper boardCommentRowMapper;
    
    //커뮤니티 리스트
    public List selectBoardList(int start, int end) {
		String query = "select * from (select rownum as rnum , n.* from (select * from board order by 1 desc)n)where rnum between ? and ?";
		List boardList = jdbc.query(query, boardRowMapper,start,end);
		return boardList;
	}
    
    //커뮤니티 총 게시글
	public int selectBoardTotalNum() {
		String query = "select count(*) from board";
		int totalNum = jdbc.queryForObject(query, Integer.class);
		return totalNum;
	}

	public List boardList() {
		String query = "select * from (select rownum as rnum , n.* from (select * from board order by 1 desc)n)";
		List boardList = jdbc.query(query, boardRowMapper);
		return boardList;
	}
    
    //커뮤니티 작성
	public int insertBoard(Board b) {
		String query = "insert into board values (board_seq.nextval,?,?,?,to_char(sysdate,'yyyy-mm-dd'),0,?)";
		Object[] params = {b.getMemberId(),b.getBoardTitle(),b.getBoardContent(),b.getBoardFilepath()};
		int result = jdbc.update(query,params);
		return result;
	}
	
	//커뮤니티 전체사진보기
	public List selectPhotoList(int start, int end) {
		String query = "select * from (select rownum as rnum, p.* from(select * from board order by 1 desc)p)where rnum between ? and ?";
		List boardList = jdbc.query(query,boardRowMapper, start,end);
		return boardList;
	}
	
	//커뮤니티 조회수
	public int updateReadCount(int boardNo) {
		String query = "update board set board_read_count =board_read_count+1 where board_no=?";
		Object[] params = {boardNo};
		int count = jdbc.update(query,params);
		return count;
	}
	
	//커뮤니티 상세보기
	public Board selectOneBoard(int boardNo) {
		//String query = "select * from notice join member on (member_no = notice_writer) where notice_no = ?";
		String query = "select * from board where board_no = ?";
		List list = jdbc.query(query, boardRowMapper,boardNo);
		return (Board)list.get(0);
	}
	
	//커뮤니티 삭제
	public int deleteBoard(int boardNo) {
		String query = "delete from board where board_no = ?";
		Object[] params = {boardNo};
		int result = jdbc.update(query,params);
		return result;
	}

	//커뮤니티 수정
	public int updateBoard(Board b) {
		String query = "update board set board_title=? ,board_content=? where board_no =?";
		Object[] params = {b.getBoardTitle(),b.getBoardContent(),b.getBoardNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	
	//커뮤니티 댓글쓰기
	public int insertComment(BoardComment bc) {
		String query = "insert into board_comment values(board_comment_seq.nextval,?,?,to_char(sysdate,'yyyy-mm-dd'),?,?)";
		String boardCommentRefNo = bc.getBoardCommentRefNo()==0?null:String.valueOf(bc.getBoardCommentRefNo());
		Object[] params = {bc.getBoardCommentWriter(),bc.getBoardCommentContent(),bc.getBoardNo(),boardCommentRefNo};
		int result = jdbc.update(query,params);
		return result;
	}
	
	//커뮤니티 댓글 조회
	public List selectCommentList(int boardNo) {
		String query = "select * from board_comment join member on (member.MEMBER_NO=board_comment.board_comment_writer) where board_comment.board_no=? and board_comment_ref_no is null order by 1 ";
		List list = jdbc.query(query, boardCommentRowMapper,boardNo);
		return list;
	}
	
	//커뮤니티 대댓글 조회
	public List selectReCommentList(int boardNo) {
		String query = "select * from board_comment join member on (member.MEMBER_NO=board_comment.board_comment_writer) where board_comment.board_no=? and board_comment_ref_no is not null order by 1 ";
		List list = jdbc.query(query, boardCommentRowMapper,boardNo);
		return list;
	}

	//커뮤니티 댓글 수정
	public int updateComment(BoardComment bc) {
		String query = "update board_comment set board_comment_content=? where board_comment_no=?";
		Object[] params = {bc.getBoardCommentContent(),bc.getBoardCommentNo()};
		int result = jdbc.update(query,params);
		return result;
	}
	
	//커뮤니티 댓글 삭제
	public int deleteComment(int boardCommentNo) {
		String query = "delete from board_comment where board_comment_no=?";
		Object[] params = {boardCommentNo};
		int result = jdbc.update(query,params);
		return  result;
	}
	
	//제목 검색
	public List searchBoardTitle(int start, int end, String searchName) {
		String query = "select * from (select rownum as rnum , n.* from (select * from board where board_title like '%'||?||'%' order by 1 desc)n)where rnum between ? and ?";
		List boardList = jdbc.query(query, boardRowMapper,searchName,start,end);
		return boardList;
	}
	
	//작성자 검색
	public List searchBoardWriter(int start, int end, String searchName) {
		String query = "select * from (select rownum as rnum , n.* from (select * from board where board_writer like '%'||?||'%' order by 1 desc)n)where rnum between ? and ?";
		List boardList = jdbc.query(query, boardRowMapper,searchName,start,end);
		return boardList;
	}
	
	//내용 검색
	public List searchBoardContent(int start, int end, String searchName) {
		String query = "select * from (select rownum as rnum , n.* from (select * from board where board_content like '%'||?||'%' order by 1 desc)n)where rnum between ? and ?";
		List boardList = jdbc.query(query, boardRowMapper,searchName,start,end);
		return boardList;
	}

	
}
