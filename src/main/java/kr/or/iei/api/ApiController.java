package kr.or.iei.api;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@RequestMapping(value = "/api")
public class ApiController {
	@GetMapping(value = "/apiControl")
	public String apiControl() {
		System.out.println("잘뜨는지 확인점");
		String url = "https://www.pettravel.kr/api/detailSeqPart.do";
		String partCode = "PC01";
		String contentNum = "15";
		
		try {
			int count = 0;
			
				String result = Jsoup.connect(url)
						.data("partCode", partCode)
						.data("contentNum", contentNum)
						.ignoreContentType(true)	//응답형식을 무시함(문자열로 받아서 직접 편집하기 위해)
						.get()						//get요청
						.text()						//받은 데이터를 문자열로 변환
						;
				//System.out.println(result);
				System.out.println(result.getClass().getName());
				//문자열 -> json
				JsonArray array = (JsonArray)JsonParser.parseString(result);
				System.out.println("array"+array);
				//키값으로 받아오고 타입지정
				JsonObject arr1 = (JsonObject)array.get(0).getAsJsonObject();
				System.out.println("arr1"+arr1);
				String contentSeq = arr1.get("contentSeq").getAsString();
				System.out.println(contentSeq);
			
			//System.out.println(count);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/facility/tourDetail";
	}
}
