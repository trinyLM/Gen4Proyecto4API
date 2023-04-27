package com.luna.app.repo.dtos.vuelta;

import java.util.List;

public class ResponseDTO<T> {
	private Integer numeroPagina;
	private Integer medidaPagina;
	private Long totalElementos;
	private Integer totalPaginas;
	private Boolean ultima;
	
	private List<T> contenido;

	public Integer getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public Integer getMedidaPagina() {
		return medidaPagina;
	}

	public void setMedidaPagina(Integer medidaPagina) {
		this.medidaPagina = medidaPagina;
	}

	public Long getTotalElementos() {
		return totalElementos;
	}

	public void setTotalElementos(Long totalElementos) {
		this.totalElementos = totalElementos;
	}

	public Integer getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(Integer totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public Boolean getUltima() {
		return ultima;
	}

	public void setUltima(Boolean ultima) {
		this.ultima = ultima;
	}

	public List<T> getContenido() {
		return contenido;
	}

	public void setContenido(List<T> contenido) {
		this.contenido = contenido;
	}
	
	
	

}

