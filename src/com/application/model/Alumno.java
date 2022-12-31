package com.application.model;

import java.util.Objects;

public class Alumno {
	@Override
	public int hashCode() {
		return Objects.hash(apellido, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;
		return Objects.equals(apellido, other.apellido) && Objects.equals(nombre, other.nombre);
	}

	private static int increment = 0;
	private Integer ID;
	private String apellido;
	private String nombre;
	
	public Alumno(String apellido, String nombre) {
		this.apellido = apellido;
		this.nombre = nombre;
		this.ID = ++increment;
	}
	
	public Alumno(Integer id, String apellido, String nombre) {
		if(increment < id) increment = id;
		this.apellido = apellido;
		this.nombre = nombre;
		this.ID = id;
	}

	public String getApellido() {
		return apellido;
	}

	public Integer getID() {
		return ID;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return ID + ":" + apellido + ":" + nombre;
	}
}
