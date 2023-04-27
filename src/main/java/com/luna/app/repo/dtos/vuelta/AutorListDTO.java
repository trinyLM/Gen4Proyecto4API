package com.luna.app.repo.dtos.vuelta;

public class AutorListDTO {
	private Long id;
	
	private String nombre;
	
	private String apellidoPaterno;

	private String apellidoMaterno;

	private String matricula;

	private String nombreDelAsesor;

	private String carrera;

	private String campus;

	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

}
