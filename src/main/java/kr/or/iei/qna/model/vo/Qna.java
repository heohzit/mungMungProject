package kr.or.iei.qna.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Qna {
	private int qnaNo;
	private int qnaCase;
	private int qnaWriter;
	private String qnaTitle;
	private String qnaContent;
	private String qnaWriteDate;
	private int qnaAnswerCase;
	private String memberId;
}
