package kr.or.iei.facility.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FacilityFile {
	private int facilityFileNo;
	private int facilityNo;
	private String facilityFilepath;
}
