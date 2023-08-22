package kr.or.iei.product.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
	private int productNo;
	private String productName;
	private String productContent;
	private int productPrice;
	private int productStock;
	private String productStart;
	private String productEnd;
	private int productDay;
}
