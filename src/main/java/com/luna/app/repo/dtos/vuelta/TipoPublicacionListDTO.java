package com.luna.app.repo.dtos.vuelta;

public class TipoPublicacionListDTO {

	private Long id;

	private String tipoDePublicacion;

	private String descripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoDePublicacion() {
		return tipoDePublicacion;
	}

	public void setTipoDePublicacion(String tipoDePublicacion) {
		this.tipoDePublicacion = tipoDePublicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
