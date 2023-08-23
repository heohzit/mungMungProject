package kr.or.iei.api;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Controller
@RequestMapping(value = "/api")
public class ApiController {
	@Autowired
	ApiService apiService;
	
	@GetMapping(value = "/apiControl12313154")
	public String apiControl() {
		System.out.println("잘뜨는지 확인점");
		String url = "https://www.pettravel.kr/api/detailSeqPart.do";
		String partCode = "PC04";	/*분야코드 변경*/
		int facilityCase = 4; 
		
		try {
			ArrayList<Api> list = new ArrayList<Api>();
			int count = 0;
			for(int i=1;i<510;i++) {
				String result = Jsoup.connect(url)
						.data("partCode", partCode)
						.data("contentNum", Integer.toString(i))
						.ignoreContentType(true)	//응답형식을 무시함(문자열로 받아서 직접 편집하기 위해)
						.get()						//get요청
						.text()						//받은 데이터를 문자열로 변환
						;
				//문자열 -> json
				JsonArray array = (JsonArray)JsonParser.parseString(result);
//				System.out.println("array : "+array);
				//키값으로 받아오고 타입지정
				JsonObject resultList = array.get(0).getAsJsonObject();
//				System.out.println("resultList : "+resultList);
				System.out.println("i : "+ i);

				
				
///////////////////데이터 추출////////////////////////////////////////////////////////////////////////////////
				
				
				
				if(resultList.size() == 2) {	//값이 정상적으로 들어있는 경우 2, error : 1
					JsonObject resultList2 = resultList.get("resultList").getAsJsonObject();
//					System.out.println("resultList2 : "+resultList2);
					
					
					String contentSeq = resultList2.get("contentSeq").getAsString();
					
					String facilityName = resultList2.get("title").getAsString();
					String facilityPhone = resultList2.get("tel").getAsString();
					String facilityAddr = resultList2.get("address").getAsString();
					
					double facilityLat = resultList2.get("latitude").getAsDouble();
					double facilityLng = resultList2.get("longitude").getAsDouble();
					
					String facilityTime = resultList2.get("usedTime").getAsString();
					String facilityHomepage = resultList2.get("homePage").getAsString();
					String facilityInfo = resultList2.get("content").getAsString();
					String facilityMajor = resultList2.get("mainFacility").getAsString();
					String facilityPrice = resultList2.get("usedCost").getAsString();
					String facilityNotice = resultList2.get("policyCautions").getAsString();
					
					System.out.println("--------------확인해볼게요--------------------");
					System.out.println("facilityCase : "+facilityCase);
					System.out.println("facilityName : "+facilityName);
					System.out.println("facilityPhone : "+facilityPhone);
					System.out.println("facilityAddr : "+facilityAddr);
					System.out.println("facilityLat : "+facilityLat);
					System.out.println("facilityLng : "+facilityLng);
					System.out.println("facilityTime : "+facilityTime);
					System.out.println("facilityHomepage : "+facilityHomepage);
					System.out.println("facilityInfo : "+facilityInfo);
					System.out.println(facilityMajor);
					System.out.println(facilityPrice);
					System.out.println("facilityNotice : "+facilityNotice);
					
					
					
					String areaName = resultList2.get("areaName").getAsString();
					System.out.println(areaName);
					
					int facilityRegion = 0;
					
					switch(areaName) {
					case "춘천시":
						facilityRegion = 1;
						break;
					case "원주시":
						facilityRegion = 2;
						break;
					case "강릉시":
						facilityRegion = 3;
						break;
					case "동해시":
						facilityRegion = 4;
						break;
					case "태백시":
						facilityRegion = 5;
						break;
					case "속초시":
						facilityRegion = 6;
						break;
					case "삼척시":
						facilityRegion = 7;
						break;
					case "홍천군":
						facilityRegion = 8;
						break;
					case "횡성군":
						facilityRegion = 9;
						break;
					case "영월군":
						facilityRegion = 10;
						break;
					case "평창군":
						facilityRegion = 11;
						break;
					case "정선군":
						facilityRegion = 12;
						break;
					case "철원군":
						facilityRegion = 13;
						break;
					case "화천군":
						facilityRegion = 14;
						break;
					case "양구군":
						facilityRegion = 15;
						break;
					case "인제군":
						facilityRegion = 16;
						break;
					case "고성군":
						facilityRegion = 17;
						break;
					case "양양군":
						facilityRegion = 18;
						break;
					default:
						facilityRegion = 0;
						break;
					}
					
					System.out.println("facilityRegion : "+facilityRegion);
					System.out.println("--------------------------------------------");
					
					count++;
					
	/////////////////데이터 삽입///////////////////////////////////////////////////////////////////////				
					Api api = new Api();
					api.setFacilityAddr(facilityAddr);
					api.setFacilityCase(facilityCase);
					api.setFacilityHomepage(facilityHomepage);
					api.setFacilityInfo(facilityInfo);
					api.setFacilityLat(facilityLat);
					api.setFacilityLng(facilityLng);
					api.setFacilityMajor(facilityMajor);
					api.setFacilityName(facilityName);
//					api.setFacilityNo(facilityNo);
					api.setFacilityNotice(facilityNotice);
					api.setFacilityPhone(facilityPhone);
					api.setFacilityPrice(facilityPrice);
					api.setFacilityRegion(facilityRegion);
					api.setFacilityTime(facilityTime);
//					api.setFacilityWriter(facilityWriter);
					
					list.add(api);
					
					
				}//size == 2
			}//for문 종료
			System.out.println("get(0)"+list.get(0));
//			System.out.println("get(4)"+list.get(4));
			System.out.println(list);
			System.out.println("size : "+ list.size());
			System.out.println(count);
			
			///////apiService로 전달//////////////
			int result1 = apiService.insertApi(list);
			if(result1>0) {
				System.out.println("성공"+result1);
			} else {
				System.out.println("실패");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/facility/tourDetail";
	}
	
}
