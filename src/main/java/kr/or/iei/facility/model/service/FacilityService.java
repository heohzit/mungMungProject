package kr.or.iei.facility.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.iei.facility.model.dao.FacilityDao;
import kr.or.iei.facility.model.vo.FacilityListData;

@Service
public class FacilityService {
	@Autowired
	public FacilityDao facilityDao;

	public FacilityListData selectTourList(int reqPage) {
		int numPerPage = 12;
		int endNum = reqPage * numPerPage;
		int startNum = endNum - numPerPage + 1;
		List facilityList = facilityDao.selectTourList(startNum, endNum);
		
		int totalCount = facilityDao.selectTourListTotalCount();
		
		
		return null;
	}
}
