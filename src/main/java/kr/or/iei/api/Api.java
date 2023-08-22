package kr.or.iei.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Api {
	private int facilityNo;
	private int facilityWriter;
	private int facilityRegion;
	private int facilityCase;
	private String facilityName;
	private String facilityPhone;
	private String facilityAddr;
	private double facilityLat;
	private double facilityLng;
	private String facilityTime;
	private String facilityHomepage;
	private String facilityInfo;
	private String facilityMajor;
	private String facilityPrice;
	private String facilityNotice;
}
