package com.luna.app.repo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.luna.app.repo.dao.ICampusDAO;
import com.luna.app.repo.dtos.ida.CreateCampusDTO;
import com.luna.app.repo.dtos.vuelta.CampusListDTO;
import com.luna.app.repo.dtos.vuelta.ResponseDTO;
import com.luna.app.repo.models.Campus;

@Component
public class CampusService implements IService<CampusListDTO, CreateCampusDTO> {

	@Autowired
	ICampusDAO campusdao;

	@Override
	public CampusListDTO create(CreateCampusDTO obj) {
		Campus c = this.campusdao.save(this.dtoToModel(obj));
		return this.modelToDto(c);
	}

	@Override
	public ResponseDTO<CampusListDTO> getAll(Integer numeroDePagina, Integer medidaDePagina, String ordenarPor,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
				: Sort.by(ordenarPor).descending();
		Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
		Page<Campus> campus = this.campusdao.findAll(pageable);
		List<Campus> listaDeCampus = campus.getContent();
		List<CampusListDTO> listaCampusDto = new ArrayList<CampusListDTO>();

		for (Campus c : listaDeCampus) {
			listaCampusDto.add(this.modelToDto(c));
		}

		ResponseDTO<CampusListDTO> respuesta = new ResponseDTO<>();
		respuesta.setContenido(listaCampusDto);
		respuesta.setMedidaPagina(campus.getSize());
		respuesta.setNumeroPagina(campus.getNumber());
		respuesta.setTotalElementos(campus.getTotalElements());
		respuesta.setTotalPaginas(campus.getTotalPages());
		respuesta.setUltima(campus.isLast());

		return respuesta;
	}

	@Override
	public CampusListDTO getById(Long id) {
		return this.modelToDto(this.campusdao.findById(id).orElse(new Campus()));
	}

	@Override
	public CampusListDTO update(CreateCampusDTO obj, Long id) {
		Campus campus = this.campusdao.findById(id).orElse(null);
		if (campus != null) {
			campus.setId(id);
			campus.setNombre(obj.getNombre());
			campus.setDireccion(obj.getDireccion());
			return this.modelToDto(this.campusdao.save(campus));
		} else {
			return new CampusListDTO();
		}
	}

	@Override
	public void delete(Long id) {
		this.campusdao.delete(this.campusdao.findById(id).get());

	}

	private Campus dtoToModel(CreateCampusDTO campusDto) {
		Campus c = new Campus();
		c.setNombre(campusDto.getNombre());
		c.setDireccion(campusDto.getDireccion());
		return c;

	}

	private CampusListDTO modelToDto(Campus campus) {

		CampusListDTO aux = new CampusListDTO();
		aux.setId(campus.getId());
		aux.setNombre(campus.getNombre());
		aux.setDireccion(campus.getDireccion());
		return aux;
	}

}
