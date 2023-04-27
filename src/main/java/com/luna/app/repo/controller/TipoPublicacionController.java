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
import com.luna.app.repo.dtos.ida.CreateTipoPublicacionDTO;
import com.luna.app.repo.dtos.vuelta.MensajeResponseDTO;
import com.luna.app.repo.dtos.vuelta.ResponseDTO;
import com.luna.app.repo.dtos.vuelta.TipoPublicacionListDTO;
import com.luna.app.repo.services.TiposPublicacionService;
import com.luna.app.repo.utils.AppConstantes;

@RestController
@RequestMapping("/api/tiposDePublicacion")
@CrossOrigin(origins = "*")
public class TipoPublicacionController {
	@Autowired
	TiposPublicacionService tiposPublicacionService;

	// listar
	@GetMapping
	public ResponseDTO<TipoPublicacionListDTO> obtenerListaTiposPublicacion(
			@RequestParam(name = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroPagina,
			@RequestParam(name = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaPagina,
			@RequestParam(name = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
			@RequestParam(name = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

		return this.tiposPublicacionService.getAll(numeroPagina, medidaPagina, ordenarPor, sortDir);
	}

	// crear
	@PostMapping
	public ResponseEntity<TipoPublicacionListDTO> guardarTipoPublicacion(
			@RequestBody CreateTipoPublicacionDTO publicacionDto) {
		return new ResponseEntity<>(this.tiposPublicacionService.create(publicacionDto), HttpStatus.CREATED);
	}

	// obtenerPorId
	@GetMapping("/{id}")
	public ResponseEntity<TipoPublicacionListDTO> getTipoPublicacionById(@PathVariable(name = "id") Long id) {
		return new ResponseEntity<TipoPublicacionListDTO>(this.tiposPublicacionService.getById(id), HttpStatus.OK);
	}

	// actualizar
	@PutMapping("/{id}")
	public ResponseEntity<TipoPublicacionListDTO> actulizarTipoPublicacion(
			@RequestBody CreateTipoPublicacionDTO tipoPublicacionDto, @PathVariable(name = "id") Long id) {
		TipoPublicacionListDTO tipoPublicacionRespuesta = this.tiposPublicacionService.update(tipoPublicacionDto, id);
		return new ResponseEntity<>(tipoPublicacionRespuesta, HttpStatus.CREATED);
	}

	/* eliminar */
	@DeleteMapping("/{id}")
	public ResponseEntity<MensajeResponseDTO> eliminartipoPublicacion(@PathVariable(name = "id") Long id) {
		try {
			this.tiposPublicacionService.delete(id);
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
