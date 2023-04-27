package com.luna.app.repo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luna.app.repo.models.Archivo;

public interface IArchivoDAO extends JpaRepository<Archivo, Long> {

}
