package kr.or.iei.board.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardListData {
	private List boardList;
	private String pageNavi;
}
