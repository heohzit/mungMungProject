package kr.or.iei.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DBDao {
	@Autowired
	private JdbcTemplate jdbc;
	
	public int insertApi(Facility api) {
		// TODO Auto-generated method stub
						//INSERT INTO FACILITY VALUES(FACILITY_SEQ.NEXTVAL,21,1,1,'시설이름','시설번호','시설주소',111.111,111.111,'영업시간','홈페이지','정보','주요시설','가격','주요사항');
		String query = "INSERT INTO FACILITY VALUES(FACILITY_SEQ.NEXTVAL, 1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params =  {api.getFacilityRegion(),api.getFacilityCase(),api.getFacilityName(),api.getFacilityPhone(),api.getFacilityAddr(),api.getFacilityLat(),api.getFacilityLng(),api.getFacilityTime(),api.getFacilityHomepage(),api.getFacilityInfo(),api.getFacilityMajor(),api.getFacilityPrice(),api.getFacilityNotice()};
		int result = jdbc.update(query,params);
		return result;
	}

	public int insertFile(FacilityFile facilityFile) {
		// TODO Auto-generated method stub
		String query = "INSERT INTO FACILITY_FILE VALUES(FACILITY_FILE_SEQ.NEXTVAL, ?, ?)";
		Object[] params =  {facilityFile.getFacilityFileNo(), facilityFile.getFacilityFilePath()};
		int result = jdbc.update(query,params);
		return result;
	}

}
