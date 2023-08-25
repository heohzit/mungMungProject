package kr.or.iei.facility.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FacilityImage {
	private int facilityFileNo;
	private int facilityNo;
	private String facilityFilepath;
}
