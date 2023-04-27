package com.luna.app.repo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luna.app.repo.dtos.ida.CreateArchivoDTO;
import com.luna.app.repo.dtos.vuelta.ArchivoListDTO;
import com.luna.app.repo.dtos.vuelta.MensajeResponseDTO;
import com.luna.app.repo.dtos.vuelta.ResponseDTO;
import com.luna.app.repo.services.ArchivoService;
import com.luna.app.repo.utils.AppConstantes;

//imports to files
@RestController
@RequestMapping("/api/archivo")
@CrossOrigin(origins = "*")
public class ArchivoController {

	@Autowired
	private ArchivoService archivoService;

	@PostMapping
	public ResponseEntity<ArchivoListDTO> SubirArchivo(@ModelAttribute CreateArchivoDTO pdfDataDto) {// er

		return new ResponseEntity<>(this.archivoService.create(pdfDataDto), HttpStatus.CREATED);
	}

	// listar
	@GetMapping
	public ResponseDTO<ArchivoListDTO> obtenerListaArchivos(
			@RequestParam(name = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroPagina,
			@RequestParam(name = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaPagina,
			@RequestParam(name = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
			@RequestParam(name = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

		return this.archivoService.getAll(numeroPagina, medidaPagina, ordenarPor, sortDir);
	}

	// obtenerPorId
	@GetMapping("/{id}")
	public ResponseEntity<ArchivoListDTO> getArchivoById(@PathVariable(name = "id") Long id) {
		return new ResponseEntity<ArchivoListDTO>(this.archivoService.getById(id), HttpStatus.OK);
	}

	// actualizar
	@PutMapping("/{id}")
	public ResponseEntity<ArchivoListDTO> actulizarArchivo(@ModelAttribute CreateArchivoDTO archivoDto,
			@PathVariable(name = "id") Long id) {
		ArchivoListDTO archivoRespuesta = this.archivoService.update(archivoDto, id);
		return new ResponseEntity<>(archivoRespuesta, HttpStatus.CREATED);
	}

	/* eliminar */
	@DeleteMapping("/{id}")
	public ResponseEntity<MensajeResponseDTO> eliminarArchivo(@PathVariable(name = "id") Long id) {
		try {
			this.archivoService.delete(id);
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
