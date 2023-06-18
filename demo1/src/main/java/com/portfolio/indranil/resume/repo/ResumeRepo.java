package com.portfolio.indranil.resume.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.portfolio.indranil.resume.entity.resumeEntity;

@Repository
public interface ResumeRepo extends JpaRepository<resumeEntity, Long> {
	


}
