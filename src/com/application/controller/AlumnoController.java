package com.application.controller;

import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

import com.application.model.Alumno;

public class AlumnoController {
	private Set<Alumno> alumnos = new HashSet<Alumno>();
	
	public Set<Alumno> getAlumnos() {
		return alumnos;
	}
	
	public void clearAlumnos() {
		alumnos.clear();
	}
	
	public Alumno getAlumnoById(Integer id) {
		return (Alumno)alumnos.stream().filter(alumno -> alumno.getID() == id).toArray()[0];
	}
	
	public boolean agregarAlumno(Alumno alumno) {
		try {
			return alumnos.add(alumno);
		}catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "El alumno es nulo.");
			return false;
		}
	}
	
	public void modificarAlumno(Alumno alumno) {
		alumnos.stream().forEach(alu -> {
			if(alu.getID() == alumno.getID()) {
				alu.setNombre(alumno.getNombre());
				alu.setApellido(alumno.getApellido());
			}
		});
	}
	
	public boolean quitarAlumno(int index) {
		return alumnos.removeIf(alu -> alu.getID() == index);
	}
}
