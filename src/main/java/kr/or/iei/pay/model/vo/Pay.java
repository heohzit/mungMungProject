package kr.or.iei.pay.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pay {
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
