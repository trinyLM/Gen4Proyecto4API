package com.luna.app.repo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luna.app.repo.models.Autor;

public interface IAutorDAO extends JpaRepository<Autor, Long> {

}
