package kr.or.iei.product.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.iei.FileUtil;
import kr.or.iei.product.model.service.ProductService;
import kr.or.iei.product.model.vo.Product;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	 @Value("${file.root}")
		private String root;
	 @Autowired
		private FileUtil fileUtil;
	
	@GetMapping(value = "/list")//패키지 리스트
	public String productList(Model model) {
		List productList = productService.selectProductList();
		model.addAttribute("productList", productList);
		return "product/productList";
	}
	@GetMapping(value = "/writeFrm")//관리자용 패키지 등록 페이지 이동 양식
		public String productWriteFrm() {
			return "product/writeFrm";
	}
	@PostMapping(value = "/write")
	public String productWrite(Product p,MultipartFile productFilepath) {
		String savepath = root+"photo/";
		String filepath = fileUtil.getFilepath(savepath, productFilepath.getOriginalFilename());
		p.setProductFilpath(filepath);
		File upFile = new File(savepath+filepath);
		try {
			productFilepath.transferTo(upFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int result = productService.insertProduct(p);
		if(result>0) {
			return "product/productList";
		}else {
			return "product/writeFrm";
		}
	}
	@ResponseBody
  	@PostMapping (value="editor",produces ="plain/text;charset=utf-8")
  	public String editorUpload(MultipartFile file) {
  		String savepath = root+"product/";
  		String filepath = fileUtil.getFilepath(savepath, file.getOriginalFilename());
  		File image = new File(savepath+filepath);
  			try {
  				file.transferTo(image);
  			} catch (IllegalStateException | IOException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  		return "/product/"+filepath;
  	}
}
