package com.luna.app.repo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.luna.app.repo.dao.ITipoPublicacionDAO;
import com.luna.app.repo.dtos.ida.CreateTipoPublicacionDTO;
import com.luna.app.repo.dtos.vuelta.ResponseDTO;
import com.luna.app.repo.dtos.vuelta.TipoPublicacionListDTO;
import com.luna.app.repo.models.TipoPublicacion;

@Component
public class TiposPublicacionService implements IService<TipoPublicacionListDTO, CreateTipoPublicacionDTO> {

	@Autowired
	ITipoPublicacionDAO tipoPublicacionDao;

	@Override
	public TipoPublicacionListDTO create(CreateTipoPublicacionDTO obj) {
		TipoPublicacion t = this.tipoPublicacionDao.save(this.dtoToModel(obj));
		return this.modelToDto(t);
	}

	@Override
	public ResponseDTO<TipoPublicacionListDTO> getAll(Integer numeroDePagina, Integer medidaDePagina, String ordenarPor,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
				: Sort.by(ordenarPor).descending();
		Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
		Page<TipoPublicacion> tiposPublicaciones = this.tipoPublicacionDao.findAll(pageable);
		List<TipoPublicacion> listaDeTiposCalificaciones = tiposPublicaciones.getContent();
		List<TipoPublicacionListDTO> listaPublicacionesDto = new ArrayList<TipoPublicacionListDTO>();

		for (TipoPublicacion t : listaDeTiposCalificaciones) {
			listaPublicacionesDto.add(this.modelToDto(t));
		}

		ResponseDTO<TipoPublicacionListDTO> respuesta = new ResponseDTO<>();
		respuesta.setContenido(listaPublicacionesDto);
		respuesta.setMedidaPagina(tiposPublicaciones.getSize());
		respuesta.setNumeroPagina(tiposPublicaciones.getNumber());
		respuesta.setTotalElementos(tiposPublicaciones.getTotalElements());
		respuesta.setTotalPaginas(tiposPublicaciones.getTotalPages());
		respuesta.setUltima(tiposPublicaciones.isLast());

		return respuesta;
	}

	@Override
	public TipoPublicacionListDTO getById(Long id) {
		return this.modelToDto(this.tipoPublicacionDao.findById(id).orElse(new TipoPublicacion()));
	}

	@Override
	public TipoPublicacionListDTO update(CreateTipoPublicacionDTO obj, Long id) {
		TipoPublicacion tipoPublicacion = this.tipoPublicacionDao.findById(id).orElse(null);
		if (tipoPublicacion != null) {
			tipoPublicacion.setId(id);
			tipoPublicacion.setNombre(obj.getTipoDePublicacion());
			tipoPublicacion.setDescripcion(obj.getDescripcion());
			return this.modelToDto(this.tipoPublicacionDao.save(tipoPublicacion));
		} else {
			return new TipoPublicacionListDTO();
		}
	}

	@Override
	public void delete(Long id) {
		this.tipoPublicacionDao.delete(this.tipoPublicacionDao.findById(id).get());

	}

	private TipoPublicacion dtoToModel(CreateTipoPublicacionDTO publicacionDto) {
		TipoPublicacion aux = new TipoPublicacion();
		aux.setNombre(publicacionDto.getTipoDePublicacion());
		aux.setDescripcion(publicacionDto.getDescripcion());
		return aux;
	}

	private TipoPublicacionListDTO modelToDto(TipoPublicacion tipoPublicacion) {
		TipoPublicacionListDTO aux = new TipoPublicacionListDTO();
		aux.setId(tipoPublicacion.getId());
		aux.setTipoDePublicacion(tipoPublicacion.getNombre());
		aux.setDescripcion(tipoPublicacion.getDescripcion());
		return aux;
	}

}
