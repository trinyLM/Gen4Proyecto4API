package com.luna.app.repo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.luna.app.repo.dao.ICarrerasDAO;
import com.luna.app.repo.dtos.ida.CreateCarreraDTO;
import com.luna.app.repo.dtos.vuelta.CarreraListDTO;
import com.luna.app.repo.dtos.vuelta.ResponseDTO;
import com.luna.app.repo.models.Carrera;

@Component
public class CarrerasService implements IService<CarreraListDTO, CreateCarreraDTO> {

	@Autowired
	ICarrerasDAO carrerasDao;

	@Override
	public CarreraListDTO create(CreateCarreraDTO obj) {
		Carrera c = carrerasDao.save(this.dtoToModel(obj));
		return this.modelToDto(c);
	}

	@Override
	public ResponseDTO<CarreraListDTO> getAll(Integer numeroDePagina, Integer medidaDePagina, String ordenarPor,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
				: Sort.by(ordenarPor).descending();
		Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
		Page<Carrera> carreras = this.carrerasDao.findAll(pageable);
		List<Carrera> listaDeCarreras = carreras.getContent();
		List<CarreraListDTO> listaCarrerasDto = new ArrayList<CarreraListDTO>();

		for (Carrera c : listaDeCarreras) {
			listaCarrerasDto.add(this.modelToDto(c));
		}

		ResponseDTO<CarreraListDTO> respuesta = new ResponseDTO<>();
		respuesta.setContenido(listaCarrerasDto);
		respuesta.setMedidaPagina(carreras.getSize());
		respuesta.setNumeroPagina(carreras.getNumber());
		respuesta.setTotalElementos(carreras.getTotalElements());
		respuesta.setTotalPaginas(carreras.getTotalPages());
		respuesta.setUltima(carreras.isLast());

		return respuesta;
	}

	@Override
	public CarreraListDTO getById(Long id) {
		return this.modelToDto(this.carrerasDao.findById(id).orElse(new Carrera()));
	}

	@Override
	public CarreraListDTO update(CreateCarreraDTO obj, Long id) {
		Carrera carrera = this.carrerasDao.findById(id).orElse(null);
		if (carrera != null) {
			carrera.setId(id);
			carrera.setNombre(obj.getNombreCarrera());
			carrera.setClave(obj.getClaveCarrera());
			return this.modelToDto(this.carrerasDao.save(carrera));
		} else {
			return new CarreraListDTO();
		}
	}

	@Override
	public void delete(Long id) {
		
		this.carrerasDao.delete(this.carrerasDao.findById(id).get());
	}

	private Carrera dtoToModel(CreateCarreraDTO carreraDto) {
		Carrera c = new Carrera();
		c.setNombre(carreraDto.getNombreCarrera());
		c.setClave(carreraDto.getClaveCarrera());
		return c;

	}

	private CarreraListDTO modelToDto(Carrera carrera) {

		CarreraListDTO aux = new CarreraListDTO();
		aux.setId(carrera.getId());
		aux.setNombre(carrera.getNombre());
		aux.setClave(carrera.getClave());

		return aux;
	}

}
