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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.indranil.Email.EmailSenderService;
import com.portfolio.indranil.certificate.entity.Certificate;
import com.portfolio.indranil.certificate.repo.CertificateRepository;
import com.portfolio.indranil.certificate.service.CertificateService;
import com.portfolio.indranil.resume.entity.resumeEntity;
import com.portfolio.indranil.resume.service.ResumeService;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class portfolio_controller {
	
		@Autowired
	    private CertificateService certService;
		
		@Autowired
		private ResumeService resumeService;
		
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
			
			
			Path path = Paths.get("src/main/resources/static/uploads/" + fileName);//----------very important
	        
	        System.out.println(path.toAbsolutePath());
	        
	        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	        
	        return "redirect:/";
	 	}
	 //--------------saves CV-------------------------------
	 	@PostMapping("/cv/saveCv")
	 	public @ResponseBody String createCertificate(final @RequestParam("image") MultipartFile file) throws IOException {
	 		
	 		 // deleting all sql entries before
	 	    resumeService.deleteAll();
	 	    
	 		 // Step 2: Insert new value
	 	    resumeEntity resume = new resumeEntity();
	 	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());	 	    
	 	    resume.setImage(fileName);
	 	    resume.setPick(1);
	 	    resumeService.save(resume);

	 	    // Step 3: Remove all physical copies from server folder
	 	    Resource resource = new ClassPathResource("static/uploads/cv");
	 	    File folder = resource.getFile();
	 	    if (folder.exists()) {
	 	        File[] files = folder.listFiles();
	 	        for (File f : files) {
	 	            f.delete();
	 	        }
	 	    }

	 	    // Step 4: Save new physical copy in server folder
	 	    Path path = Paths.get("src/main/resources/static/uploads/" + fileName);
	 	    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	 	    return "redirect:/";
	 	}
	 	
	 	//--------------DOWNLOAD CV-------------
	 	@GetMapping("/downloadPDF")
	    public ResponseEntity<InputStreamResource> downloadPDF() throws IOException {
	        // your code to read the file from the server's folder
	 		resumeEntity resume=resumeService.returnBypick();
	        File file = new File("src/main/resources/static/uploads/" + resume.getImage());
	        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	        HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName());

	        return ResponseEntity.ok()
	                .headers(headers)
	                .contentLength(file.length())
	                .contentType(MediaType.parseMediaType("application/pdf"))
	                .body(resource);
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
