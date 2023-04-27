package com.luna.app.repo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luna.app.repo.dtos.ida.CreateCampusDTO;
import com.luna.app.repo.dtos.vuelta.CampusListDTO;
import com.luna.app.repo.dtos.vuelta.MensajeResponseDTO;
import com.luna.app.repo.dtos.vuelta.ResponseDTO;
import com.luna.app.repo.services.CampusService;
import com.luna.app.repo.utils.AppConstantes;

@RestController
@RequestMapping("/api/campus")
@CrossOrigin(origins = "*")
public class CampusController {
	@Autowired
	CampusService campusService;

	// listar
	@GetMapping
	public ResponseDTO<CampusListDTO> obtenerListaCampus(
			@RequestParam(name = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroPagina,
			@RequestParam(name = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaPagina,
			@RequestParam(name = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
			@RequestParam(name = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

		return this.campusService.getAll(numeroPagina, medidaPagina, ordenarPor, sortDir);
	}

	// crear
	@PostMapping
	public ResponseEntity<CampusListDTO> guardarCampus(@RequestBody CreateCampusDTO campusDto) {
		return new ResponseEntity<>(this.campusService.create(campusDto), HttpStatus.CREATED);
	}

	// obtenerPorId
	@GetMapping("/{id}")
	public ResponseEntity<CampusListDTO> getCampusById(@PathVariable(name = "id") Long id) {
		return new ResponseEntity<CampusListDTO>(this.campusService.getById(id), HttpStatus.OK);
	}

	// actualizar
	@PutMapping("/{id}")
	public ResponseEntity<CampusListDTO> actulizarCampus(@RequestBody CreateCampusDTO campusDto,
			@PathVariable(name = "id") Long id) {
		CampusListDTO campusRespuesta = this.campusService.update(campusDto, id);
		return new ResponseEntity<>(campusRespuesta, HttpStatus.CREATED);
	}

	/* eliminar una marca */
	@DeleteMapping("/{id}")
	public ResponseEntity<MensajeResponseDTO> eliminarCampus(@PathVariable(name = "id") Long id) {
		try {
			this.campusService.delete(id);
			MensajeResponseDTO mensajeRespuesta = new MensajeResponseDTO();
			mensajeRespuesta.setMsg("Elemnto borrado correctamente");
			mensajeRespuesta.setStatus("OK");
			return new ResponseEntity<>(mensajeRespuesta, HttpStatus.OK);
		} catch (Exception e) {
			MensajeResponseDTO mensajeRespuesta = new MensajeResponseDTO();
			mensajeRespuesta.setMsg("no se pudo borrar elemnto");
			mensajeRespuesta.setStatus("FAIL");
			return new ResponseEntity<>(mensajeRespuesta, HttpStatus.BAD_REQUEST);

		}
	}

}// fin de la clase controller
