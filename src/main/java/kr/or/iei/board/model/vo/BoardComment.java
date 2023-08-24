package kr.or.iei.board.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardComment {
	private int boardCommentNo;
	private int boardCommentWriter;
	private String boardCommentContent;
	private String boardCommentWriteDate;
	private int boardNo;
	private int boardCommentRefNo;
	private String memberId;
}
