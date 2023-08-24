package kr.or.iei.facility.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.iei.facility.model.dao.FacilityDao;
import kr.or.iei.facility.model.vo.Facility;
import kr.or.iei.facility.model.vo.FacilityListData;

@Service
public class FacilityService {
	@Autowired
	public FacilityDao facilityDao;

	public FacilityListData selectTourList(int reqPage) {
		// 1. 한 페이지당 게시물 수 설정
		int numPerPage = 12;
		// 게시물을  DB에서 조회 후 가져옴
		int endNum = reqPage * numPerPage;
		int startNum = endNum - numPerPage + 1;
		List facilityList = facilityDao.selectTourList(startNum, endNum);
		// 게시물리스트에 filepath 추가
		for(int i = 0; i < facilityList.size(); i++) {
			Object facilityObj = facilityList.get(i);
			Facility facilityFacility = (Facility)facilityObj;
			int facilityNo = facilityFacility.getFacilityNo();
			String facilityFilepath = facilityDao.selectFacilityFile(facilityNo);
			facilityFacility.setFacilityFilepath(facilityFilepath);
		}
		
		
		
		// 2. 페이지 내비게이션 생성
		// 총 게시물 수 DB에서 조회해서 가져옴
		int totalList = facilityDao.selectTourListTotalCount();
		// 총 페이지 수 계산
		int totalPage = totalList / numPerPage;
		// 총 게시물 수를 한 페이지당 게시물 수로 나눈 나머지 값이 0이 아니면 총 페이지 수에 1을 더함
		if(totalList % numPerPage != 0) {
			totalPage += 1;
		}
		// 페이지 내비게이션 사이즈 설정
		int pageNaviSize = 5;
		// 페이지 내비게이션 시작번호 설정
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;
		
		// 페이지 내비게이션 html 제작
		String pageNavi = "<ul class='pagination'>";
		// 페이지 내비게이션 번호가 1이 아닌 경우에만 이전버튼 활성화
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-btn' href='/facility/tourList?reqPage="+(pageNo-1)+"'>";
			pageNavi += "<span class='material-icons'>arrow_back_ios_new</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		// 페이지 내비게이션 숫자버튼 제작
		// 요청페이지와 페이지 내비게이션 번호가 같은 경우만 css활성화
		// 페이지 내비게이션 번호가 총 페이지 수보다 크면 반복문 종료
		for(int i = 0; i < pageNaviSize; i++) {
			if(pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-btn select-page' href='/facility/tourList?reqPage="+pageNo+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-btn' href='/facility/tourList?reqPage="+pageNo+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		
		// 페이지 내비게이션 번호가 총 페이지 수보다 크지 않은 경우만 다음버튼 활성화
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-btn' href='/facility/tourList?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		pageNavi += "</ul>";
		
		
		// 게시물과 페이지 내비게이션을 1개의 객체로 통합
		FacilityListData fld = new FacilityListData(facilityList, pageNavi);
		
		return fld;
	}

	public FacilityListData selectSearchTourList(int reqPage, String searchName) {
				// 1. 한 페이지당 게시물 수 설정
				int numPerPage = 12;
				// 게시물을  DB에서 조회 후 가져옴
				int endNum = reqPage * numPerPage;
				int startNum = endNum - numPerPage + 1;
				List facilityList = facilityDao.selectSearchTourList(startNum, endNum, searchName);
				// 게시물리스트에 filepath 추가
				for(int i = 0; i < facilityList.size(); i++) {
					Object facilityObj = facilityList.get(i);
					Facility facilityFacility = (Facility)facilityObj;
					int facilityNo = facilityFacility.getFacilityNo();
					String facilityFilepath = facilityDao.selectFacilityFile(facilityNo);
					facilityFacility.setFacilityFilepath(facilityFilepath);
				}
				
				
				
				// 2. 페이지 내비게이션 생성
				// 총 게시물 수 DB에서 조회해서 가져옴
				int totalList = facilityDao.selectSearchTourListTotalCount(searchName);
				// 총 페이지 수 계산
				int totalPage = totalList / numPerPage;
				// 총 게시물 수를 한 페이지당 게시물 수로 나눈 나머지 값이 0이 아니면 총 페이지 수에 1을 더함
				if(totalList % numPerPage != 0) {
					totalPage += 1;
				}
				// 페이지 내비게이션 사이즈 설정
				int pageNaviSize = 5;
				// 페이지 내비게이션 시작번호 설정
				int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;
				
				// 페이지 내비게이션 html 제작
				String pageNavi = "<ul class='pagination'>";
				// 페이지 내비게이션 번호가 1이 아닌 경우에만 이전버튼 활성화
				if(pageNo != 1) {
					pageNavi += "<li>";
					pageNavi += "<a class='page-btn' href='/facility/searchTourList?reqPage="+(pageNo-1)+"&searchName="+searchName+"'>";
					pageNavi += "<span class='material-icons'>arrow_back_ios_new</span>";
					pageNavi += "</a>";
					pageNavi += "</li>";
				}
				// 페이지 내비게이션 숫자버튼 제작
				// 요청페이지와 페이지 내비게이션 번호가 같은 경우만 css활성화
				// 페이지 내비게이션 번호가 총 페이지 수보다 크면 반복문 종료
				for(int i = 0; i < pageNaviSize; i++) {
					if(pageNo == reqPage) {
						pageNavi += "<li>";
						pageNavi += "<a class='page-btn select-page' href='/facility/searchTourList?reqPage="+pageNo+"&searchName="+searchName+"'>";
						pageNavi += pageNo;
						pageNavi += "</a>";
						pageNavi += "</li>";
					}else {
						pageNavi += "<li>";
						pageNavi += "<a class='page-btn' href='/facility/searchTourList?reqPage="+pageNo+"&searchName="+searchName+"'>";
						pageNavi += pageNo;
						pageNavi += "</a>";
						pageNavi += "</li>";
					}
					pageNo++;
					if(pageNo > totalPage) {
						break;
					}
				}
				
				// 페이지 내비게이션 번호가 총 페이지 수보다 크지 않은 경우만 다음버튼 활성화
				if(pageNo <= totalPage) {
					pageNavi += "<li>";
					pageNavi += "<a class='page-btn' href='/facility/searchTourList?reqPage="+pageNo+"&searchName="+searchName+"'>";
					pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
					pageNavi += "</a>";
					pageNavi += "</li>";
				}
				
				pageNavi += "</ul>";
				
				
				// 게시물과 페이지 내비게이션을 1개의 객체로 통합
				FacilityListData fld = new FacilityListData(facilityList, pageNavi);
				
				return fld;
	}

	public FacilityListData selectHotelList(int reqPage) {
		// 1. 한 페이지당 게시물 수 설정
				int numPerPage = 12;
				// 게시물을  DB에서 조회 후 가져옴
				int endNum = reqPage * numPerPage;
				int startNum = endNum - numPerPage + 1;
				List facilityList = facilityDao.selectHotelList(startNum, endNum);
				// 게시물리스트에 filepath 추가
				for(int i = 0; i < facilityList.size(); i++) {
					Object facilityObj = facilityList.get(i);
					Facility facilityFacility = (Facility)facilityObj;
					int facilityNo = facilityFacility.getFacilityNo();
					String facilityFilepath = facilityDao.selectFacilityFile(facilityNo);
					facilityFacility.setFacilityFilepath(facilityFilepath);
				}
				
				
				
				// 2. 페이지 내비게이션 생성
				// 총 게시물 수 DB에서 조회해서 가져옴
				int totalList = facilityDao.selectHotelListTotalCount();
				// 총 페이지 수 계산
				int totalPage = totalList / numPerPage;
				// 총 게시물 수를 한 페이지당 게시물 수로 나눈 나머지 값이 0이 아니면 총 페이지 수에 1을 더함
				if(totalList % numPerPage != 0) {
					totalPage += 1;
				}
				// 페이지 내비게이션 사이즈 설정
				int pageNaviSize = 5;
				// 페이지 내비게이션 시작번호 설정
				int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;
				
				// 페이지 내비게이션 html 제작
				String pageNavi = "<ul class='pagination'>";
				// 페이지 내비게이션 번호가 1이 아닌 경우에만 이전버튼 활성화
				if(pageNo != 1) {
					pageNavi += "<li>";
					pageNavi += "<a class='page-btn' href='/facility/hotelList?reqPage="+(pageNo-1)+"'>";
					pageNavi += "<span class='material-icons'>arrow_back_ios_new</span>";
					pageNavi += "</a>";
					pageNavi += "</li>";
				}
				// 페이지 내비게이션 숫자버튼 제작
				// 요청페이지와 페이지 내비게이션 번호가 같은 경우만 css활성화
				// 페이지 내비게이션 번호가 총 페이지 수보다 크면 반복문 종료
				for(int i = 0; i < pageNaviSize; i++) {
					if(pageNo == reqPage) {
						pageNavi += "<li>";
						pageNavi += "<a class='page-btn select-page' href='/facility/hotelList?reqPage="+pageNo+"'>";
						pageNavi += pageNo;
						pageNavi += "</a>";
						pageNavi += "</li>";
					}else {
						pageNavi += "<li>";
						pageNavi += "<a class='page-btn' href='/facility/hotelList?reqPage="+pageNo+"'>";
						pageNavi += pageNo;
						pageNavi += "</a>";
						pageNavi += "</li>";
					}
					pageNo++;
					if(pageNo > totalPage) {
						break;
					}
				}
				
				// 페이지 내비게이션 번호가 총 페이지 수보다 크지 않은 경우만 다음버튼 활성화
				if(pageNo <= totalPage) {
					pageNavi += "<li>";
					pageNavi += "<a class='page-btn' href='/facility/hotelList?reqPage="+pageNo+"'>";
					pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
					pageNavi += "</a>";
					pageNavi += "</li>";
				}
				
				pageNavi += "</ul>";
				
				
				// 게시물과 페이지 내비게이션을 1개의 객체로 통합
				FacilityListData fld = new FacilityListData(facilityList, pageNavi);
				
				return fld;
	}

	public FacilityListData selectSearchHotelList(int reqPage, String searchName) {
		// 1. 한 페이지당 게시물 수 설정
		int numPerPage = 12;
		// 게시물을  DB에서 조회 후 가져옴
		int endNum = reqPage * numPerPage;
		int startNum = endNum - numPerPage + 1;
		List facilityList = facilityDao.selectSearchHotelList(startNum, endNum, searchName);
		// 게시물리스트에 filepath 추가
		for(int i = 0; i < facilityList.size(); i++) {
			Object facilityObj = facilityList.get(i);
			Facility facilityFacility = (Facility)facilityObj;
			int facilityNo = facilityFacility.getFacilityNo();
			String facilityFilepath = facilityDao.selectFacilityFile(facilityNo);
			facilityFacility.setFacilityFilepath(facilityFilepath);
		}
		
		
		
		// 2. 페이지 내비게이션 생성
		// 총 게시물 수 DB에서 조회해서 가져옴
		int totalList = facilityDao.selectSearchHotelListTotalCount(searchName);
		// 총 페이지 수 계산
		int totalPage = totalList / numPerPage;
		// 총 게시물 수를 한 페이지당 게시물 수로 나눈 나머지 값이 0이 아니면 총 페이지 수에 1을 더함
		if(totalList % numPerPage != 0) {
			totalPage += 1;
		}
		// 페이지 내비게이션 사이즈 설정
		int pageNaviSize = 5;
		// 페이지 내비게이션 시작번호 설정
		int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;
		
		// 페이지 내비게이션 html 제작
		String pageNavi = "<ul class='pagination'>";
		// 페이지 내비게이션 번호가 1이 아닌 경우에만 이전버튼 활성화
		if(pageNo != 1) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-btn' href='/facility/searchHotelList?reqPage="+(pageNo-1)+"&searchName="+searchName+"'>";
			pageNavi += "<span class='material-icons'>arrow_back_ios_new</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		// 페이지 내비게이션 숫자버튼 제작
		// 요청페이지와 페이지 내비게이션 번호가 같은 경우만 css활성화
		// 페이지 내비게이션 번호가 총 페이지 수보다 크면 반복문 종료
		for(int i = 0; i < pageNaviSize; i++) {
			if(pageNo == reqPage) {
				pageNavi += "<li>";
				pageNavi += "<a class='page-btn select-page' href='/facility/searchHotelList?reqPage="+pageNo+"&searchName="+searchName+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-btn' href='/facility/searchHotelList?reqPage="+pageNo+"&searchName="+searchName+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}
			pageNo++;
			if(pageNo > totalPage) {
				break;
			}
		}
		
		// 페이지 내비게이션 번호가 총 페이지 수보다 크지 않은 경우만 다음버튼 활성화
		if(pageNo <= totalPage) {
			pageNavi += "<li>";
			pageNavi += "<a class='page-btn' href='/facility/searchHotelList?reqPage="+pageNo+"&searchName="+searchName+"'>";
			pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		pageNavi += "</ul>";
		
		
		// 게시물과 페이지 내비게이션을 1개의 객체로 통합
		FacilityListData fld = new FacilityListData(facilityList, pageNavi);
		
		return fld;
	}

}
