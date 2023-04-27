package com.luna.app.repo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.luna.app.repo.dao.IAutorDAO;
import com.luna.app.repo.dao.ICampusDAO;
import com.luna.app.repo.dao.ICarrerasDAO;
import com.luna.app.repo.dtos.ida.CreateAutorDTO;
import com.luna.app.repo.dtos.vuelta.AutorListDTO;
import com.luna.app.repo.dtos.vuelta.ResponseDTO;
import com.luna.app.repo.models.Autor;

@Component
public class AutorService implements IService<AutorListDTO, CreateAutorDTO> {

	@Autowired
	private IAutorDAO autorDao;

	@Autowired
	private ICampusDAO campusDao;

	@Autowired
	private ICarrerasDAO carreraDao;

	@Override
	public AutorListDTO create(CreateAutorDTO obj) {
		Autor a = this.autorDao.save(this.dtoToModel(obj));
		return this.modelToDto(a);
	}

	@Override
	public ResponseDTO<AutorListDTO> getAll(Integer numeroDePagina, Integer medidaDePagina, String ordenarPor,
			String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
				: Sort.by(ordenarPor).descending();
		Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
		Page<Autor> autores = this.autorDao.findAll(pageable);
		List<Autor> listaDeAutores = autores.getContent();
		List<AutorListDTO> listaAutoresDto = new ArrayList<AutorListDTO>();

		for (Autor a : listaDeAutores) {
			listaAutoresDto.add(this.modelToDto(a));
		}

		ResponseDTO<AutorListDTO> respuesta = new ResponseDTO<>();
		respuesta.setContenido(listaAutoresDto);
		respuesta.setMedidaPagina(autores.getSize());
		respuesta.setNumeroPagina(autores.getNumber());
		respuesta.setTotalElementos(autores.getTotalElements());
		respuesta.setTotalPaginas(autores.getTotalPages());
		respuesta.setUltima(autores.isLast());

		return respuesta;
	}

	@Override
	public AutorListDTO getById(Long id) {
		return this.modelToDto(this.autorDao.findById(id).orElse(new Autor()));
	}

	@Override
	public AutorListDTO update(CreateAutorDTO obj, Long id) {
		Autor autor = this.autorDao.findById(id).orElse(null);
		if (autor != null) {
			autor.setId(id);
			autor.setNombre(obj.getNombre());
			autor.setApellidoPaterno(obj.getApellidoPaterno());
			autor.setApellidoMaterno(obj.getApellidoMaterno());
			autor.setAsesor(obj.getNombreDelAsesor());
			autor.setMatricula(obj.getMatricula());
			autor.setCampus(this.campusDao.findById(obj.getCampusId()).orElseThrow(() -> new RuntimeException()));
			autor.setCarrera(this.carreraDao.findById(obj.getCarreraId()).orElseThrow(() -> new RuntimeException()));

			return this.modelToDto(this.autorDao.save(autor));
		} else {
			return new AutorListDTO();
		}

	}

	@Override
	public void delete(Long id) {
		this.autorDao.delete(this.autorDao.findById(id).get());

	}

	private Autor dtoToModel(CreateAutorDTO autorDto) {
		Autor a = new Autor();
		a.setNombre(autorDto.getNombre());
		a.setApellidoPaterno(autorDto.getApellidoPaterno());
		a.setApellidoMaterno(autorDto.getApellidoMaterno());
		a.setAsesor(autorDto.getNombreDelAsesor());
		a.setMatricula(autorDto.getMatricula());
		a.setCampus(this.campusDao.findById(autorDto.getCampusId()).orElse(null));
		a.setCarrera(this.carreraDao.findById(autorDto.getCarreraId()).orElse(null));
		return a;

	}

	private AutorListDTO modelToDto(Autor autor) {
		AutorListDTO aux = new AutorListDTO();
		aux.setId(autor.getId());
		aux.setNombre(autor.getNombre());
		aux.setApellidoPaterno(autor.getApellidoPaterno());
		aux.setApellidoMaterno(autor.getApellidoMaterno());
		aux.setNombreDelAsesor(autor.getAsesor());
		aux.setMatricula(autor.getMatricula());
		aux.setCampus(autor.getCampus().getNombre());
		aux.setCarrera(autor.getCarrera().getNombre());
		return aux;
	}

}
