package com.application.model;

import java.util.Objects;

public class Materia {
	private static int increment = 0;
	private Integer ID;
	private String nombre;
	
	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getID() {
		return ID;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Materia other = (Materia) obj;
		return Objects.equals(nombre, other.nombre);
	}

	public Materia(String nombre) {
		this.nombre = nombre;
		this.ID = ++increment;
	}
	
	public Materia(Integer id, String nombre) {
		if(increment < id) increment = id;
		this.nombre = nombre;
		this.ID = id;
	}

	@Override
	public String toString() {
		return ID + ":" + nombre;
	}
}
