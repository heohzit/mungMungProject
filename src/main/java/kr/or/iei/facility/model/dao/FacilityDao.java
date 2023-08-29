package kr.or.iei.facility.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.facility.model.vo.Facility;
import kr.or.iei.facility.model.vo.FacilityFavorite;
import kr.or.iei.facility.model.vo.FacilityFavoriteRowMapper;
import kr.or.iei.facility.model.vo.FacilityFile;
import kr.or.iei.facility.model.vo.FacilityImageRowMapper;
import kr.or.iei.facility.model.vo.FacilityRowMapper;

@Repository
public class FacilityDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	public FacilityImageRowMapper facilityImageRowMapper;
	@Autowired
	private FacilityRowMapper facilityRowMapper;
	@Autowired
	private FacilityFavoriteRowMapper facilityFavoriteRowMapper;

	public List selectTourList(int startNum, int endNum) {
		String query = "select * from (select rownum as rnum, n.* from (select * from facility where facility_case = 3) n) where rnum between ? and ?";
		List list = jdbc.query(query, facilityRowMapper, startNum, endNum);
		return list;
	}

	public int selectTourListTotalCount() {
		String query = "select count(*) from facility where facility_case = 3";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public Facility selectOneTour(int facilityNo) {
		// TODO Auto-generated method stub
		String query = "select * from facility where facility_no = ?";
		List list = jdbc.query(query, facilityRowMapper, facilityNo);	
		return (Facility)list.get(0);
	}

	public String selectFacilityFile(int facilityNo) {
		String query = "select facility_filepath from facility_file where facility_file_no = (select min(facility_file_no) from facility_file where facility_no = ?)";
		try {
			String facilityFilepath = jdbc.queryForObject(query ,String.class, facilityNo);
			return facilityFilepath;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}		
	}

	public List selectSearchTourList(int startNum, int endNum, String searchName) {
		String query = "select * from (select rownum as rnum , n.* from (select * from facility where facility_case = 3 and facility_name like '%'||?||'%') n) where rnum between ? and ?";
		List list = jdbc.query(query, facilityRowMapper, searchName, startNum, endNum);
		return list;
	}

	public int selectSearchTourListTotalCount(String searchName) {
		String query = "select count(*) from facility where facility_case = 3 and facility_name like '%'||?||'%'";
		int totalCount = jdbc.queryForObject(query, Integer.class, searchName);
		return totalCount;
	}

	public List selectHotelList(int startNum, int endNum) {
		String query = "select * from (select rownum as rnum, n.* from (select * from facility where facility_case = 2) n) where rnum between ? and ?";
		List list = jdbc.query(query, facilityRowMapper, startNum, endNum);
		return list;
	}

	public int selectHotelListTotalCount() {
		String query = "select count(*) from facility where facility_case = 2";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public List selectSearchHotelList(int startNum, int endNum, String searchName) {
		String query = "select * from (select rownum as rnum , n.* from (select * from facility where facility_case = 2 and facility_name like '%'||?||'%') n) where rnum between ? and ?";
		List list = jdbc.query(query, facilityRowMapper, searchName, startNum, endNum);
		return list;
	}

	public int selectSearchHotelListTotalCount(String searchName) {
		String query = "select count(*) from facility where facility_case = 2 and facility_name like '%'||?||'%'";
		int totalCount = jdbc.queryForObject(query, Integer.class, searchName);
		return totalCount;
	}

	public List selectCafeList(int startNum, int endNum) {
		String query = "select * from (select rownum as rnum, n.* from (select * from facility where facility_case = 1) n) where rnum between ? and ?";
		List list = jdbc.query(query, facilityRowMapper, startNum, endNum);
		return list;
	}

	public int selectCafeListTotalCount() {
		String query = "select count(*) from facility where facility_case = 1";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public List selectSearchCafeList(int startNum, int endNum, String searchName) {
		String query = "select * from (select rownum as rnum , n.* from (select * from facility where facility_case = 1 and facility_name like '%'||?||'%') n) where rnum between ? and ?";
		List list = jdbc.query(query, facilityRowMapper, searchName, startNum, endNum);
		return list;
	}

	public int selectSearchCafeListTotalCount(String searchName) {
		String query = "select count(*) from facility where facility_case = 1 and facility_name like '%'||?||'%'";
		int totalCount = jdbc.queryForObject(query, Integer.class, searchName);
		return totalCount;
	}

	public List selectActivityList(int startNum, int endNum) {
		String query = "select * from (select rownum as rnum, n.* from (select * from facility where facility_case = 4) n) where rnum between ? and ?";
		List list = jdbc.query(query, facilityRowMapper, startNum, endNum);
		return list;
	}

	public int selectActivityListTotalCount() {
		String query = "select count(*) from facility where facility_case = 4";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public List selectSearchActivityList(int startNum, int endNum, String searchName) {
		String query = "select * from (select rownum as rnum , n.* from (select * from facility where facility_case = 4 and facility_name like '%'||?||'%') n) where rnum between ? and ?";
		List list = jdbc.query(query, facilityRowMapper, searchName, startNum, endNum);
		return list;
	}

	public int selectSearchActivityListTotalCount(String searchName) {
		String query = "select count(*) from facility where facility_case = 4 and facility_name like '%'||?||'%'";
		int totalCount = jdbc.queryForObject(query, Integer.class, searchName);
		return totalCount;
	}


	public List selectImageFile(int facilityNo) {
		// TODO Auto-generated method stub
		String query = "select * from facility_file where facility_no = ?";
		List list = jdbc.query(query, facilityImageRowMapper ,facilityNo);
		return list;
	}
	public int insertFacility(Facility f) {
		String query = "insert into facility values(facility_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = {f.getFacilityWriter(), f.getFacilityRegion(), f.getFacilityCase(), f.getFacilityName(), f.getFacilityPhone(), f.getFacilityAddr(), f.getFacilityLat(), f.getFacilityLng(), f.getFacilityTime(), f.getFacilityHomepage(), f.getFacilityInfo(), f.getFacilityMajor(), f.getFacilityPrice(), f.getFacilityNotice()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int getFacilityNo() {
		String query = "select max(facility_no) from facility";
		int facilityNo = jdbc.queryForObject(query, Integer.class);
		return facilityNo;
	}

	public int insertFacilityFile(FacilityFile file) {
		String query = "insert into facility_file values(facility_file_seq.nextval, ?, ?)";
		Object[] params = {file.getFacilityNo(), file.getFacilityFilepath()};
		int result = jdbc.update(query, params);
		return result;
	}

	public int addFavorite(int memberNo, int facilityNo) {
		// TODO Auto-generated method stub
		String query = "insert into favorite values(?,?)";
		int result = jdbc.update(query, memberNo, facilityNo);
		return result;
	}

	public int selectFavorite(int facilityNo, int memberNo) {
		// TODO Auto-generated method stub
		String query = "select count(*) from favorite where favorite_facility_no = ? and favorite_member_no = ?";
		Object[] params = {facilityNo, memberNo};
		int favorite = jdbc.queryForObject(query, Integer.class,params);
		return favorite;
	}

	public int removeFavorite(int memberNo, int facilityNo) {
		// TODO Auto-generated method stub
		String query ="delete favorite where favorite_facility_no = ? and favorite_member_no = ?";
		Object[] params = {facilityNo, memberNo};
		int result = jdbc.update(query, params);
		return result;
	}

	public List selectHotelList() {
		String query = "select * from facility where facility_case = 4";
		List list = jdbc.query(query, facilityRowMapper);
		return list;
	}
}
