package com.luna.app.repo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luna.app.repo.models.Campus;

public interface ICampusDAO extends JpaRepository<Campus, Long> {
	

}
