package com.luna.app.repo.dtos.ida;

public class CreateAutorDTO {

	private String nombre;

	private String apellidoPaterno;

	private String apellidoMaterno;

	private String matricula;

	private String nombreDelAsesor;

	private Long carreraId;

	private Long campusId;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombreDelAsesor() {
		return nombreDelAsesor;
	}

	public void setNombreDelAsesor(String nombreDelAsesor) {
		this.nombreDelAsesor = nombreDelAsesor;
	}

	public Long getCarreraId() {
		return carreraId;
	}

	public void setCarreraId(Long carreraId) {
		this.carreraId = carreraId;
	}

	public Long getCampusId() {
		return campusId;
	}

	public void setCampusId(Long campusId) {
		this.campusId = campusId;
	}

}
