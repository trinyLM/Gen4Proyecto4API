package com.luna.app.repo.dtos.vuelta;

public class ArchivoListDTO {

	private Long id;
	private String titulo;
	private String imagen;
	private String materia;
	private String fechaDePublicacion;
	private String pdf;
	private String resumen;
	private String tipoDePublicacion;
	private String Autor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getFechaDePublicacion() {
		return fechaDePublicacion;
	}

	public void setFechaDePublicacion(String fechaDePublicacion) {
		this.fechaDePublicacion = fechaDePublicacion;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getTipoDePublicacion() {
		return tipoDePublicacion;
	}

	public void setTipoDePublicacion(String tipoDePublicacion) {
		this.tipoDePublicacion = tipoDePublicacion;
	}

	public String getAutor() {
		return Autor;
	}

	public void setAutor(String autor) {
		Autor = autor;
	}

}
