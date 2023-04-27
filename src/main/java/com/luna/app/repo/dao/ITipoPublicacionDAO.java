package com.luna.app.repo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luna.app.repo.models.TipoPublicacion;

public interface ITipoPublicacionDAO extends JpaRepository<TipoPublicacion, Long>{

}
