package com.portfolio.indranil.certificate.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.portfolio.indranil.certificate.entity.Certificate;
import com.portfolio.indranil.certificate.repo.CertificateRepository;

@Service
public class CertificateService {
	@Autowired
    private CertificateRepository certRepo;
	
	public void saveCertificate(Certificate cert) {
		certRepo.save(cert);
	}
	
	public List<Certificate> getAllCertificate(){
		
		return certRepo.findAll();
	}
	
	public Optional<Certificate> getCertificateById(Long id){
		return certRepo.findById(id);
	}
}
