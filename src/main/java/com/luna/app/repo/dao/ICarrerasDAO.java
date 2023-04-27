package com.luna.app.repo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luna.app.repo.models.Carrera;

public interface ICarrerasDAO extends JpaRepository<Carrera, Long>{
	

}
