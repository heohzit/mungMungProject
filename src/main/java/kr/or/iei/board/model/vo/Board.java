package kr.or.iei.board.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
	private int boardNo;
	private int boardWriter;
	private String boardTitle;
	private String boardContent;
	private String boardWriteDate;
	private int boardReadCount;
	private String memberId;
	private String boardFilepath;
}
