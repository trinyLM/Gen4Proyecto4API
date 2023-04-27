package com.luna.app.repo.dtos.ida;

import org.springframework.web.multipart.MultipartFile;

public class CreateArchivoDTO {

	private String titulo;

	private String materia;
	private String fechaPublicacion;// dd/mm/yyyy

	private String resumen;

	// llaves foraneas
	private Long autorId;

	private Long tipoPublicacionId;

	// propiedades de archivos
	private MultipartFile pdf; // multiparrt file

	private MultipartFile imagen;

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}

	public Long getTipoPublicacionId() {
		return tipoPublicacionId;
	}

	public void setTipoPublicacionId(Long tipoPublicacionId) {
		this.tipoPublicacionId = tipoPublicacionId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public MultipartFile getPdf() {
		return pdf;
	}

	public void setPdf(MultipartFile pdf) {
		this.pdf = pdf;
	}

	public MultipartFile getImagen() {
		return imagen;
	}

	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}

}
