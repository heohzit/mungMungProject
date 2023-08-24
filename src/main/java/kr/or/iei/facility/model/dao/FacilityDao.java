package kr.or.iei.facility.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.facility.model.vo.FacilityRowMapper;

@Repository
public class FacilityDao {
	@Autowired
	public JdbcTemplate jdbc;
	@Autowired
	public FacilityRowMapper facilityRowMapper;

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
}
