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
	public String productWrite(Product p,MultipartFile productImg) {
		String savepath = root+"productmain/";
		String filepath = fileUtil.getFilepath(savepath, productImg.getOriginalFilename());
		p.setProductFilepath(filepath);
		File upFile = new File(savepath+filepath);
		try {
			productImg.transferTo(upFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int result = productService.insertProduct(p);
		if(result>0) {
			return "redirect:/product/list";
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
	@GetMapping(value = "/view")
	public String productView(int productNo ,Model model) {
		Product p = productService.selectOneProduct(productNo);
		if(p != null) {
			model.addAttribute("p", p);
			return "product/productView";
		}else {
			return "product/productList";
		}
	}
	
	@GetMapping(value="/pay")
	public String pay() {
		return "product/pay";

	@GetMapping(value = "/delete")
	public String deleteProduct(int productNo , Model model) {
		int result = productService.deleteProduct(productNo);
		if (result != 0) {
			model.addAttribute("title", "삭제완료");
			model.addAttribute("msg", "패키지가 삭제되었습니다.");
			model.addAttribute("icon", "success");
			model.addAttribute("loc", "/product/list");
		}else {
			model.addAttribute("title", "삭제실패");
			model.addAttribute("msg", "이미 삭제된 글이거나 삭제 할 수 없는 글입니다");
			model.addAttribute("icon", "error");
			model.addAttribute("loc", "/notice/view?productNo="+productNo);	
		}
		return"common/msg";
	}
	@GetMapping(value = "/updateFrm")
	public String updateFrm(int productNo , Model model) {
		Product p = productService.getProduct(productNo);
		model.addAttribute("p", p );
		return "product/productUpdateFrm";
	}
	
	@PostMapping(value = "/update")
	public String update(Product p , Model model,MultipartFile productUpdateImg) {
		String savepath = root+"productmain/";
		if(!productUpdateImg.isEmpty()) {
			String filepath = fileUtil.getFilepath(savepath, productUpdateImg.getOriginalFilename());
			p.setProductFilepath(filepath);
			File upFile = new File(savepath+filepath);
			
		try {
			productUpdateImg.transferTo(upFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		int result = productService.updateProduct(p);
		if(result>0) {
			model.addAttribute("title", "수정완료");
			model.addAttribute("msg", "패키지가 수정되었습니다");
			model.addAttribute("icon", "success");	
		}else {
			model.addAttribute("title", "수정실패");
			model.addAttribute("msg", "수정에 실패하였습니다 ");
			model.addAttribute("icon", "error");
		}
		model.addAttribute("loc", "/product/view?productNo="+p.getProductNo());
		return "common/msg";
	}
}
