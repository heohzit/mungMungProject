package kr.or.iei.member.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FavoriteListData {
	private List favoriteList;
	private String pageNavi;
}
