package com.portfolio.indranil.resume.service;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.portfolio.indranil.resume.entity.resumeEntity;
import com.portfolio.indranil.resume.repo.ResumeRepo;

@Service
public class ResumeService  {
	
	@Autowired
	private ResumeRepo resumeRepo;

	public void save(resumeEntity resume) {
		resumeRepo.save(resume);
	}
	
	public void deleteAll() {
		resumeRepo.deleteAll();
	}
	
	public resumeEntity returnBypick() {
		
		return resumeRepo.findByPic();
		
	}
}
