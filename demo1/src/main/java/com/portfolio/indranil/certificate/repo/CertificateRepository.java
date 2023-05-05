package com.portfolio.indranil.certificate.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.indranil.certificate.entity.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
		
}

