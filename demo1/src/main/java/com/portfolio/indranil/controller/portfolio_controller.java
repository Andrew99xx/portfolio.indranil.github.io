package com.portfolio.indranil.controller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.portfolio.indranil.Email.EmailSenderService;
import com.portfolio.indranil.certificate.entity.Certificate;
import com.portfolio.indranil.certificate.service.CertificateService;
import com.portfolio.indranil.resume.entity.*;
import com.portfolio.indranil.resume.repo.ResumeRepo;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class portfolio_controller {
	
		@Autowired
	    private CertificateService certService;
		
		@Autowired
		private ResumeRepo resumeRepo;
		
		@Autowired EmailSenderService emailService;
		
		@Value("${uploadDir}")
		private String uploadFolder;
		
		private final Logger log = LoggerFactory.getLogger(this.getClass());
		//-------------------calls cv and certificates------------------------
	 	@GetMapping(value={"/","/home"})
	    public String home(Model model) {
	 		System.out.println("hi");
	 		List<Certificate> certificate= certService.getAllCertificate();	 		
 	        model.addAttribute("certificates", certificate); 	      
	        return "home";
	    }
	 	//--------------saves certificate--------------------------------
	 	@PostMapping("/image/saveImageDetails")
		public @ResponseBody String createCertificate
				(@RequestParam("name") String name,
				 @RequestParam("description") String description, Model model, HttpServletRequest request,
				 final @RequestParam("image") MultipartFile file) throws IOException
	 	{
			Certificate cert= new Certificate();		
	 		
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			
			cert.setTitle(name);
			cert.setDescription(description);
			cert.setImage(fileName);
			certService.saveCertificate(cert);
			
			 Resource resource = new ClassPathResource("static/uploads/cv/");
			
			 Path path = Paths.get(resource.getURI());//----------very important
	        
	        System.out.println(path.toAbsolutePath());
	        
	        Files.copy(file.getInputStream(), path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
	        
	        return "redirect:/";
	 	}
	 //--------------saves CV-------------------------------
	 	@RestController
	 	class ImageController {

	 	    @Autowired
	 	    ResumeRepo imageDbRepository;

	 	    @PostMapping("/cv/saveCv")
	 	    Long uploadImage(@RequestParam MultipartFile multipartImage) throws Exception {
	 	        resumeEntity dbImage = new resumeEntity();
	 	        dbImage.setName(multipartImage.getName());
	 	        dbImage.setContent(multipartImage.getBytes());

	 	        return imageDbRepository.save(dbImage)
	 	            .getId();
	 	    }
	 	}

	 	
	 	//--------------DOWNLOAD CV-------------
	 	@GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
	 	Resource downloadImage(@PathVariable Long imageId) {
	 	    byte[] image = resumeRepo.findById(imageId)
	 	      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
	 	      .getContent();

	 	    return new ByteArrayResource(image);
	 	}
	 	
	 	//--------------send email
	 	@PostMapping("/sendmail")
	 	public String sendEmail(@RequestParam("mailId") String mailid,
	 											@RequestParam("subject") String subject,
	 											@RequestParam("text") String text) throws Exception  {
			
	 		emailService.sendEmail("hascalstech@gmail.com", mailid, subject , text);
	 		
	 		return "redirect:/";
	 		
	 	}
	 	
	 	
	 	@GetMapping("/uploadcert")
	 	public String uploadPageCert() {
	 		return "uploadCert";
	 	}
	 	
	 	@GetMapping("/uploadcv")
	 	public String uploadPageCV() {
	 		return "uploadCV";
	 	}
	    
	 	/*@RequestMapping("/error")
	    public String error() {
	        return "error";
	    }*/

}
