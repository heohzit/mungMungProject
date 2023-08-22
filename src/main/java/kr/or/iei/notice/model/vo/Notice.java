package kr.or.iei.notice.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notice {
	private int noticeNo;
	private int noticeWriter;
	private String noticeTitle;
	private String noticeContent;
	private String noticeWriteDate;
	private int noticeReadCount;
	private String memberId;
	private List fileList;
}
