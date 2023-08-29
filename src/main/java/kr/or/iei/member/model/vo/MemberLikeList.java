package kr.or.iei.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberLikeList {
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberPhone;
	private String memberEmail;
	private int memberLevel;
	private String enrollDate;
	private String memberName;
	private int favoriteMemberNo;
	private int favoriteFacilityNo;
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
