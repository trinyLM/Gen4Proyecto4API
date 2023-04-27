package com.luna.app.repo.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.luna.app.repo.dao.IArchivoDAO;
import com.luna.app.repo.dao.IAutorDAO;
import com.luna.app.repo.dao.ITipoPublicacionDAO;
import com.luna.app.repo.dtos.ida.CreateArchivoDTO;
import com.luna.app.repo.dtos.vuelta.ArchivoListDTO;
import com.luna.app.repo.dtos.vuelta.ResponseDTO;
import com.luna.app.repo.models.Archivo;

@Component
public class ArchivoService implements IService<ArchivoListDTO, CreateArchivoDTO> {

	@Autowired
	private IArchivoDAO archivoDao;

	@Autowired
	private IAutorDAO autorDao;

	@Autowired
	private ITipoPublicacionDAO tipoPublicacionDao;

	@Override
	public ArchivoListDTO create(CreateArchivoDTO obj) {
		try {
			Archivo a = this.archivoDao.save(this.dtoToModel(obj));
			return this.modelToDto(a);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ResponseDTO<ArchivoListDTO> getAll(Integer numeroDePagina, Integer medidaDePagina, String ordenarPor,
			String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
				: Sort.by(ordenarPor).descending();
		Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
		Page<Archivo> archivos = this.archivoDao.findAll(pageable);
		List<Archivo> listaDeArchivos = archivos.getContent();
		List<ArchivoListDTO> listaArchivosDto = new ArrayList<ArchivoListDTO>();

		for (Archivo a : listaDeArchivos) {
			listaArchivosDto.add(this.modelToDto(a));
		}

		ResponseDTO<ArchivoListDTO> respuesta = new ResponseDTO<>();
		respuesta.setContenido(listaArchivosDto);
		respuesta.setMedidaPagina(archivos.getSize());
		respuesta.setNumeroPagina(archivos.getNumber());
		respuesta.setTotalElementos(archivos.getTotalElements());
		respuesta.setTotalPaginas(archivos.getTotalPages());
		respuesta.setUltima(archivos.isLast());

		return respuesta;
	}

	@Override
	public ArchivoListDTO getById(Long id) {
		return this.modelToDto(this.archivoDao.findById(id).orElse(new Archivo()));
	}

	@Override
	public ArchivoListDTO update(CreateArchivoDTO obj, Long id) {
		Archivo archivo = this.archivoDao.findById(id).orElse(null);
		if (archivo != null) {
			archivo.setId(id);
			archivo.setTitulo(obj.getTitulo());
			archivo.setMateria(obj.getMateria());
			archivo.setResumen(obj.getResumen());

			// LocalDate fecha formato correcto
			LocalDate fecha;
			try {
				fecha = LocalDate.parse(obj.getFechaPublicacion(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				archivo.setFechaPublicacion(fecha);
			} catch (DateTimeParseException e) {
				fecha = null;
				archivo.setFechaPublicacion(fecha);
			}
			archivo.setTipoPublicacion(this.tipoPublicacionDao.findById(obj.getTipoPublicacionId())
					.orElseThrow(() -> new RuntimeException()));

			archivo.setAutor(this.autorDao.findById(obj.getAutorId()).orElseThrow(() -> new RuntimeException()));

			// mapear el path y guardarlo
			// deserializar e

			archivo.setPdf(("/uploads/" + obj.getPdf().getOriginalFilename()).replaceAll("\\s+", ""));

			archivo.setImagen(("/uploads/" + obj.getImagen().getOriginalFilename()).replaceAll("\\s+", ""));
//			

		    // Guardar el archivo pdf en el sistema de archivos
			String pathPdf = ("src/main/resources/static/uploads/" + obj.getPdf().getOriginalFilename())
					.replaceAll("\\s+", "");
			try {
				File convFilePdf = new File(pathPdf);
				convFilePdf.createNewFile();
				FileOutputStream fos = new FileOutputStream(convFilePdf);
				fos.write(obj.getPdf().getBytes());
				fos.close();
			} catch (Exception e) {
				// TODO: handle exception
			}

			// guardar la imagen
			String pathImg = ("src/main/resources/static/uploads/" + obj.getImagen().getOriginalFilename())
					.replaceAll("\\s+", "");
			try {

				File convFileImg = new File(pathImg);
				convFileImg.createNewFile();
				try (FileOutputStream fosImg = new FileOutputStream(convFileImg)) {
					fosImg.write(obj.getImagen().getBytes());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			return this.modelToDto(this.archivoDao.save(archivo));
		} else {
			return new ArchivoListDTO();
		}
	}

	@Override
	public void delete(Long id) {
		this.archivoDao.delete(this.archivoDao.findById(id).get());
	}

	private Archivo dtoToModel(CreateArchivoDTO archivoDto) throws IOException {
		Archivo a = new Archivo();

		// implementar la conversion
		a.setTitulo(archivoDto.getTitulo());
		a.setMateria(archivoDto.getMateria());
		a.setResumen(archivoDto.getResumen());

		// convertir string a fecha
		String strFecha = archivoDto.getFechaPublicacion();
		LocalDate fecha;
		try {
			fecha = LocalDate.parse(strFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			a.setFechaPublicacion(fecha);
		} catch (DateTimeParseException e) {
			fecha = null;
			a.setFechaPublicacion(fecha);
		}

		// mapear tipo de publicacion
		a.setTipoPublicacion(this.tipoPublicacionDao.findById(archivoDto.getTipoPublicacionId())
				.orElseThrow(() -> new RuntimeException()));

		// mapear el autor
		a.setAutor(this.autorDao.findById(archivoDto.getAutorId()).orElseThrow(() -> new RuntimeException()));

		// mapear el path y guardarlo
		// deserializar e

		a.setPdf(("/uploads/" + archivoDto.getPdf().getOriginalFilename()).replaceAll("\\s+", ""));

		a.setImagen(("/uploads/" + archivoDto.getImagen().getOriginalFilename()).replaceAll("\\s+", ""));
//		

//	    // Guardar el archivo pdf en el sistema de archivos
		String pathPdf = ("src/main/resources/static/uploads/" + archivoDto.getPdf().getOriginalFilename())
				.replaceAll("\\s+", "");
		File convFilePdf = new File(pathPdf);
		convFilePdf.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFilePdf);
		fos.write(archivoDto.getPdf().getBytes());
		fos.close();

		// guardar la imagen
		String pathImg = ("src/main/resources/static/uploads/" + archivoDto.getImagen().getOriginalFilename())
				.replaceAll("\\s+", "");
		File convFileImg = new File(pathImg);
		convFileImg.createNewFile();
		try (FileOutputStream fosImg = new FileOutputStream(convFileImg)) {
			fosImg.write(archivoDto.getImagen().getBytes());
		}
		return a;

	}

	private ArchivoListDTO modelToDto(Archivo archivo) {
		ArchivoListDTO aux = new ArchivoListDTO();
		aux.setId(archivo.getId());
		aux.setTitulo(archivo.getTitulo());
		aux.setMateria(archivo.getMateria());
		aux.setResumen(archivo.getResumen());
		aux.setFechaDePublicacion(archivo.getFechaPublicacion().toString());
		aux.setTipoDePublicacion(archivo.getTipoPublicacion().getNombre());
		aux.setAutor(archivo.getAutor().getNombre() + " " + archivo.getAutor().getApellidoPaterno() + " "
				+ archivo.getAutor().getApellidoMaterno());
		aux.setImagen("http://localhost:8080" + archivo.getImagen());
		aux.setPdf("http://localhost:8080" + archivo.getPdf());

		return aux;
	}

}
