package kr.or.iei.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberProductPay {
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberPhone;
	private String memberEmail;
	private int memberLevel;
	private String enrollDate;
	private String memberName;
	private int productNo;
	private String productName;
	private String productContent;
	private int productPrice;
	private int productStock;
	private String productStart;
	private String productEnd;
	private int productDay;
	private String productFilepath;
	private int payNo;
	private int payProductNo;
	private int payMemberNo;
	private int payPrice;
	private String payDate;
	private int payStatus;
	private String payBuyNo;
	private String payStart;
	private String payEnd;
}
