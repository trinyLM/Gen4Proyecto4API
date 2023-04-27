package com.luna.app.repo.services;

import com.luna.app.repo.dtos.vuelta.ResponseDTO;

public interface IService<T, S> {
	public T create(S obj);

	public ResponseDTO<T> getAll(Integer numeroDePagina, Integer medidaDePagina, String ordenarPor, String sortDir);

	public T getById(Long id);

	public T update(S obj, Long id);

	public void delete(Long id);

}

