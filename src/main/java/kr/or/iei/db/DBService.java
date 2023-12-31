package kr.or.iei.db;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DBService {
	@Autowired
	DBDao DBDao;
	
	@Transactional
	public int insertApi(ArrayList<Facility> list) {
		// TODO Auto-generated method stub
		int result = 0;
		for(int i=0;i<list.size();i++) {
			int serviceInsert = DBDao.insertApi(list.get(i));
			if(serviceInsert>0) {
				result++;				
			} else {
				System.out.println("insert실패 rollback확인");
				return 0;
			}
		}
		System.out.println("result : "+result+", list.size() : "+list.size());
		return result;
	}
	
	@Transactional
	public int insertFile(ArrayList<FacilityFile> list) {
		// TODO Auto-generated method stub
		int result = 0;
		for(int i=0;i<list.size();i++) {
			int serviceInsert = DBDao.insertFile(list.get(i));
			if(serviceInsert>0) {
				result++;				
			} else {
				System.out.println("insert실패 rollback확인");
				return 0;
			}
		}
		System.out.println("result : "+result+", list.size() : "+list.size());
		return result;
	}
	
	
}
