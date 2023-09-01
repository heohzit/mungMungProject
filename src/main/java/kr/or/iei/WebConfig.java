package kr.or.iei;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	// 자원위치 관리
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
			.addResourceLocations("classpath:templates/","classpath:/static/");
		
		registry.addResourceHandler("/boardmain/**")
		.addResourceLocations("file:///C:/Temp/upload/boardmain/");
		
		registry.addResourceHandler("/board/**")
		.addResourceLocations("file:///C:/Temp/upload/board/");
		
		registry.addResourceHandler("/notice/**")
		.addResourceLocations("file:///C:/Temp/upload/notice/");
		
		registry.addResourceHandler("/qna/**")
		.addResourceLocations("file:///C:/Temp/upload/qna/");

		registry.addResourceHandler("/editor/**")
		.addResourceLocations("file:///C:/Temp/upload/editor/");
		
		registry.addResourceHandler("/product/**")
		.addResourceLocations("file:///C:/Temp/upload/product/");
		
		registry.addResourceHandler("/productmain/**")
		.addResourceLocations("file:///C:/Temp/upload/productmain/");

	}


	// 인터셉터 관리
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/member/**")
				.excludePathPatterns("/member/signup","/member/login","/member/signin","/member/searchId","/member/searchPw","/member/ajaxSearchId", "/member/loginMsg","/member/ajaxSearchPw");
		
		registry.addInterceptor(new AdminInterceptor())
				.addPathPatterns("/member/admin","/member/cancelManage");
	}
}
