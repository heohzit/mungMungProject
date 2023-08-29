package kr.or.iei.facility.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.iei.facility.model.dao.FacilityDao;
import kr.or.iei.facility.model.vo.Facility;
import kr.or.iei.facility.model.vo.FacilityFavorite;
import kr.or.iei.facility.model.vo.FacilityFile;
import kr.or.iei.facility.model.vo.FacilityListData;

@Service
public class FacilityService {
	@Autowired
	private FacilityDao facilityDao;

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
	public Facility selectOneTour(int facilityNo, int memberNo) {
		// TODO Auto-generated method stub
		Facility facility = facilityDao.selectOneTour(facilityNo);
		//소개
		facility.setFacilityInfo(facility.getFacilityInfo().replaceAll("\\* ", "<br>\\* "));
		facility.setFacilityInfo(facility.getFacilityInfo().replaceFirst("<br>\\* ", "\\* "));
		//주요시설
		facility.setFacilityMajor(facility.getFacilityMajor().replaceAll("- ", "<br>- "));
		facility.setFacilityMajor(facility.getFacilityMajor().replaceFirst("<br>- ", "- "));
		//이용요금
		facility.setFacilityPrice(facility.getFacilityPrice().replaceAll("- ", "<br>- "));
		facility.setFacilityPrice(facility.getFacilityPrice().replaceFirst("<br>- ", "- "));
		
		facility.setFacilityPrice(facility.getFacilityPrice().replaceAll("\\[", "<br><br>\\["));
		facility.setFacilityPrice(facility.getFacilityPrice().replaceFirst("<br><br>\\[", "\\["));
		
		facility.setFacilityPrice(facility.getFacilityPrice().replaceAll("\\]-", "\\]<br>-"));
				
		facility.setFacilityPrice(facility.getFacilityPrice().replaceAll("\\* ", "<br>\\* "));
		facility.setFacilityPrice(facility.getFacilityPrice().replaceFirst("<br>\\* ", "<br><br>\\* "));
		//주의사항
		facility.setFacilityNotice(facility.getFacilityNotice().replaceAll("- ", "<br>- "));
		facility.setFacilityNotice(facility.getFacilityNotice().replaceFirst("<br>- ", "- "));
		
		facility.setFacilityNotice(facility.getFacilityNotice().replaceAll("\\* ", "<br>\\* "));
		facility.setFacilityNotice(facility.getFacilityNotice().replaceFirst("<br>\\* ", "<br><br>\\* "));
		
		List imgList = facilityDao.selectImageFile(facilityNo);
		int favorite = facilityDao.selectFavorite(facilityNo, memberNo);
		facility.setFavorite(favorite);
		facility.setFacilityFilepathArr(imgList);
		return facility;
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

	public FacilityListData selectCafeList(int reqPage) {
		// 1. 한 페이지당 게시물 수 설정
		int numPerPage = 12;
		// 게시물을  DB에서 조회 후 가져옴
		int endNum = reqPage * numPerPage;
		int startNum = endNum - numPerPage + 1;
		List facilityList = facilityDao.selectCafeList(startNum, endNum);
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
		int totalList = facilityDao.selectCafeListTotalCount();
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
			pageNavi += "<a class='page-btn' href='/facility/cafeList?reqPage="+(pageNo-1)+"'>";
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
				pageNavi += "<a class='page-btn select-page' href='/facility/cafeList?reqPage="+pageNo+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-btn' href='/facility/cafeList?reqPage="+pageNo+"'>";
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
			pageNavi += "<a class='page-btn' href='/facility/cafeList?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		pageNavi += "</ul>";
		
		
		// 게시물과 페이지 내비게이션을 1개의 객체로 통합
		FacilityListData fld = new FacilityListData(facilityList, pageNavi);
		
		return fld;
	}

	public FacilityListData selectSearchCafeList(int reqPage, String searchName) {
		// 1. 한 페이지당 게시물 수 설정
		int numPerPage = 12;
		// 게시물을  DB에서 조회 후 가져옴
		int endNum = reqPage * numPerPage;
		int startNum = endNum - numPerPage + 1;
		List facilityList = facilityDao.selectSearchCafeList(startNum, endNum, searchName);
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
		int totalList = facilityDao.selectSearchCafeListTotalCount(searchName);
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
			pageNavi += "<a class='page-btn' href='/facility/searchCafeList?reqPage="+(pageNo-1)+"&searchName="+searchName+"'>";
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
				pageNavi += "<a class='page-btn select-page' href='/facility/searchCafeList?reqPage="+pageNo+"&searchName="+searchName+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-btn' href='/facility/searchCafeList?reqPage="+pageNo+"&searchName="+searchName+"'>";
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
			pageNavi += "<a class='page-btn' href='/facility/searchCafeList?reqPage="+pageNo+"&searchName="+searchName+"'>";
			pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		pageNavi += "</ul>";
		
		
		// 게시물과 페이지 내비게이션을 1개의 객체로 통합
		FacilityListData fld = new FacilityListData(facilityList, pageNavi);
		
		return fld;
	}

	public FacilityListData selectActivityList(int reqPage) {
		// 1. 한 페이지당 게시물 수 설정
		int numPerPage = 12;
		// 게시물을  DB에서 조회 후 가져옴
		int endNum = reqPage * numPerPage;
		int startNum = endNum - numPerPage + 1;
		List facilityList = facilityDao.selectActivityList(startNum, endNum);
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
		int totalList = facilityDao.selectActivityListTotalCount();
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
			pageNavi += "<a class='page-btn' href='/facility/activityList?reqPage="+(pageNo-1)+"'>";
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
				pageNavi += "<a class='page-btn select-page' href='/facility/activityList?reqPage="+pageNo+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-btn' href='/facility/activityList?reqPage="+pageNo+"'>";
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
			pageNavi += "<a class='page-btn' href='/facility/activityList?reqPage="+pageNo+"'>";
			pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		pageNavi += "</ul>";
		
		
		// 게시물과 페이지 내비게이션을 1개의 객체로 통합
		FacilityListData fld = new FacilityListData(facilityList, pageNavi);
		
		return fld;
	}

	public FacilityListData selectSearchActivityList(int reqPage, String searchName) {
		// 1. 한 페이지당 게시물 수 설정
		int numPerPage = 12;
		// 게시물을  DB에서 조회 후 가져옴
		int endNum = reqPage * numPerPage;
		int startNum = endNum - numPerPage + 1;
		List facilityList = facilityDao.selectSearchActivityList(startNum, endNum, searchName);
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
		int totalList = facilityDao.selectSearchActivityListTotalCount(searchName);
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
			pageNavi += "<a class='page-btn' href='/facility/searchActivityList?reqPage="+(pageNo-1)+"&searchName="+searchName+"'>";
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
				pageNavi += "<a class='page-btn select-page' href='/facility/searchActivityList?reqPage="+pageNo+"&searchName="+searchName+"'>";
				pageNavi += pageNo;
				pageNavi += "</a>";
				pageNavi += "</li>";
			}else {
				pageNavi += "<li>";
				pageNavi += "<a class='page-btn' href='/facility/searchActivityList?reqPage="+pageNo+"&searchName="+searchName+"'>";
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
			pageNavi += "<a class='page-btn' href='/facility/searchActivityList?reqPage="+pageNo+"&searchName="+searchName+"'>";
			pageNavi += "<span class='material-icons'>arrow_forward_ios</span>";
			pageNavi += "</a>";
			pageNavi += "</li>";
		}
		
		pageNavi += "</ul>";
		
		
		// 게시물과 페이지 내비게이션을 1개의 객체로 통합
		FacilityListData fld = new FacilityListData(facilityList, pageNavi);
		
		return fld;
	}
	public List selectImageFile(int facilityNo) {
		// TODO Auto-generated method stub
//		List list = facilityDao.selectImageFile(facilityNo);
//		return list;
		return null;
		
	}
	

	@Transactional
	public int insertFacility(Facility f, ArrayList<FacilityFile> fileList) {
		// Facility는 무조건 존재, fileList는 파일이 없는 경우는 null, 있으면 list객체 존재
		int result = facilityDao.insertFacility(f);
		
		// 첨부파일이 있는 경우만 수행
		if(fileList != null) {
			// if문 위에서 insert된 facility_no를 조회
			int facilityNo = facilityDao.getFacilityNo();
			// fileList에 조회한 facility_no를 붙이고 dao로 보냄
			for(FacilityFile file : fileList) {
				file.setFacilityNo(facilityNo);
				result += facilityDao.insertFacilityFile(file);
			}
		}
		return result;
	}
	@Transactional
	public int addFavorite(int memberNo, int facilityNo) {
		// TODO Auto-generated method stub
		int result = facilityDao.addFavorite(memberNo, facilityNo);
		return result;
	}
	@Transactional
	public int removeFavorite(int memberNo, int facilityNo) {
		// TODO Auto-generated method stub
		int result = facilityDao.removeFavorite(memberNo, facilityNo);
		System.out.println("삭제 성공함? 1: yes/ 2: no");
		return result;
	}
	public List selectHotelList() {
		List hotelList = facilityDao.selectHotelList();
		return hotelList;
	}
}
