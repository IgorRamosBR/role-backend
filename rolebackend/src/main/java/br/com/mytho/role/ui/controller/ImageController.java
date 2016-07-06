package br.com.mytho.role.ui.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mytho.role.domain.service.PhotoService;

@RestController
@RequestMapping("/photo")
public class ImageController {
	private PhotoService photo;
	private ServletContext context;
	
	@Autowired
	public ImageController(PhotoService photo, ServletContext context) {
		this.photo = photo;
		this.context = context;
	}

	@RequestMapping(method=RequestMethod.POST) 
	public String upload(String image, HttpServletRequest request) {
		String imageName = photo.save(image, context);
		return request.getContextPath() + "/" + imageName; 
	}
	
}

