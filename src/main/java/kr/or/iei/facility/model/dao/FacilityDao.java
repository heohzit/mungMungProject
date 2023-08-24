package kr.or.iei.facility.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.iei.facility.model.vo.Facility;
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
		String query = "select count(*) from facility";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}

	public Facility selectOneTour(int facilityNo) {
		// TODO Auto-generated method stub
		String query = "select * from facility where facility_no = ?";
		List list = jdbc.query(query, facilityRowMapper, facilityNo);
		
		return (Facility)list.get(0);
	}
}
