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

import com.luna.app.repo.dtos.ida.CreateCarreraDTO;
import com.luna.app.repo.dtos.vuelta.CarreraListDTO;
import com.luna.app.repo.dtos.vuelta.MensajeResponseDTO;
import com.luna.app.repo.dtos.vuelta.ResponseDTO;
import com.luna.app.repo.services.CarrerasService;
import com.luna.app.repo.utils.AppConstantes;

@RestController
@RequestMapping("/api/carreras")
@CrossOrigin(origins = "*")
public class CarrerasController {

	@Autowired
	CarrerasService carrerasService;

	// listar
	@GetMapping
	public ResponseDTO<CarreraListDTO> obtenerListaCarreras(
			@RequestParam(name = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroPagina,
			@RequestParam(name = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaPagina,
			@RequestParam(name = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
			@RequestParam(name = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

		return this.carrerasService.getAll(numeroPagina, medidaPagina, ordenarPor, sortDir);
	}

	// crear
	@PostMapping
	public ResponseEntity<CarreraListDTO> guardarCarrera(@RequestBody CreateCarreraDTO carreraDto) {
		return new ResponseEntity<>(this.carrerasService.create(carreraDto), HttpStatus.CREATED);
	}

	// obtenerPorId

	@GetMapping("/{id}")
	public ResponseEntity<CarreraListDTO> getCarreraById(@PathVariable(name = "id") Long id) {
		return new ResponseEntity<CarreraListDTO>(this.carrerasService.getById(id), HttpStatus.OK);
	}

	// actualizar
	@PutMapping("/{id}")
	public ResponseEntity<CarreraListDTO> actulizarCarrera(@RequestBody CreateCarreraDTO carreraDto,
			@PathVariable(name = "id") Long id) {
		CarreraListDTO carreraRespuesta = this.carrerasService.update(carreraDto, id);
		return new ResponseEntity<>(carreraRespuesta, HttpStatus.CREATED);
	}

	/* eliminar una marca */
	@DeleteMapping("/{id}")
	public ResponseEntity<MensajeResponseDTO> eliminarCarrera(@PathVariable(name = "id") Long id) {
		try {
			this.carrerasService.delete(id);
			MensajeResponseDTO mensajeRespuesta = new MensajeResponseDTO();
			mensajeRespuesta.setMsg("Elemnto borrado correctamente");
			mensajeRespuesta.setStatus("OK");
			return new ResponseEntity<>(mensajeRespuesta, HttpStatus.OK);
		}catch (Exception e) {
			MensajeResponseDTO mensajeRespuesta = new MensajeResponseDTO();
			mensajeRespuesta.setMsg("no se pudo borrar elemnto");
			mensajeRespuesta.setStatus("FAIL");
			return new ResponseEntity<>(mensajeRespuesta, HttpStatus.BAD_REQUEST);
		
		}
	}

}
