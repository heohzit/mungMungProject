package kr.or.iei;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

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

	}


}
