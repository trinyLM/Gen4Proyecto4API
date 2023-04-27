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

import com.luna.app.repo.dtos.ida.CreateAutorDTO;
import com.luna.app.repo.dtos.vuelta.AutorListDTO;
import com.luna.app.repo.dtos.vuelta.MensajeResponseDTO;
import com.luna.app.repo.dtos.vuelta.ResponseDTO;
import com.luna.app.repo.services.AutorService;
import com.luna.app.repo.utils.AppConstantes;

@RestController
@RequestMapping("/api/autor")
@CrossOrigin(origins = "*")
public class AutorController {
	@Autowired
	private AutorService autorService;

	// listar
	@GetMapping
	public ResponseDTO<AutorListDTO> obtenerListaAutores(
			@RequestParam(name = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroPagina,
			@RequestParam(name = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaPagina,
			@RequestParam(name = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
			@RequestParam(name = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

		return this.autorService.getAll(numeroPagina, medidaPagina, ordenarPor, sortDir);
	}

	// crear
	@PostMapping
	public ResponseEntity<AutorListDTO> guardarAutor(@RequestBody CreateAutorDTO autorDto) {
		return new ResponseEntity<>(this.autorService.create(autorDto), HttpStatus.CREATED);
	}

	// obtenerPorId
	@GetMapping("/{id}")
	public ResponseEntity<AutorListDTO> getAutorById(@PathVariable(name = "id") Long id) {
		return new ResponseEntity<AutorListDTO>(this.autorService.getById(id), HttpStatus.OK);
	}

	// actualizar
	@PutMapping("/{id}")
	public ResponseEntity<AutorListDTO> actulizarAutor(@RequestBody CreateAutorDTO autorDto,
			@PathVariable(name = "id") Long id) {
		AutorListDTO autorRespuesta = this.autorService.update(autorDto, id);
		return new ResponseEntity<>(autorRespuesta, HttpStatus.CREATED);
	}

	/* eliminar una marca */
	@DeleteMapping("/{id}")
	public ResponseEntity<MensajeResponseDTO> eliminarAutor(@PathVariable(name = "id") Long id) {
		try {
			this.autorService.delete(id);
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

}
