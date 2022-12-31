package com.application.model;

import java.util.Objects;

public class Inscripcion {
	@Override
	public int hashCode() {
		return Objects.hash(nroLibreta, nroMateria);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inscripcion other = (Inscripcion) obj;
		return Objects.equals(nroLibreta, other.nroLibreta) && Objects.equals(nroMateria, other.nroMateria);
	}

	private static int increment = 0;
	private Integer ID;
	private Integer nroMateria;
	private Integer nroLibreta;
	private Integer notaParcial1;
	private Integer notaParcial2;
	private String estado;
	
	public Inscripcion(Integer nroLibreta, Integer nroMateria, Integer notaParcial1, Integer notaParcial2) {
			this.nroMateria = nroMateria;
			this.nroLibreta = nroLibreta;
			this.notaParcial1 = notaParcial1;
			this.notaParcial2 = notaParcial2;
			this.ID = ++increment;
	}
	
	public Inscripcion(Integer id, Integer nroLibreta, Integer nroMateria, Integer notaParcial1, Integer notaParcial2) {
		if(increment < id) increment = id;
		this.nroMateria = nroMateria;
		this.nroLibreta = nroLibreta;
		this.notaParcial1 = notaParcial1;
		this.notaParcial2 = notaParcial2;
		this.ID = id;
}
	
	public Integer getNotaParcial1() {
		return notaParcial1;
	}

	public void setNotaParcial1(Integer notaParcial1) {
		this.notaParcial1 = notaParcial1;
	}

	public Integer getNotaParcial2() {
		return notaParcial2;
	}

	public void setNotaParcial2(Integer notaParcial2) {
		this.notaParcial2 = notaParcial2;
	}

	public Integer getID() {
		return ID;
	}

	public Integer getNroMateria() {
		return nroMateria;
	}

	public Integer getNroLibreta() {
		return nroLibreta;
	}

	public String getEstado() {
		return estado;
	}
	
	@Override
	public String toString() {
		return ID + ":" + nroMateria + ":" + nroLibreta + ":"
				+ notaParcial1 + ":" + notaParcial2 + ":" + estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
