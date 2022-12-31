package com.application.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.application.model.Alumno;
import com.application.model.Inscripcion;
import com.application.model.Materia;
import com.application.view.Bozzola;

public class InscripcionController {
private Set<Inscripcion> inscripciones = new HashSet<Inscripcion>();
	
	public Set<Inscripcion> getInscripciones() {
		return inscripciones;
	}
	
	public void clearInscripciones() {
		inscripciones.clear();
	}
	
	public Inscripcion getInscripcionById(Integer id) {
		return (Inscripcion)inscripciones.stream().filter(ins -> ins.getID() == id).toArray()[0];
	}
	
	public boolean agregarInscripcion(Inscripcion inscripcion) {
		try {
			if(verificarExistenciaAlumnoMateria(inscripcion)){
				String estado = evaluarEstado(inscripcion.getNotaParcial1(), inscripcion.getNotaParcial2());
				inscripcion.setEstado(estado);
				return inscripciones.add(inscripcion);
			}
			
			return false;
		}catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "La materia es nula.");
			return false;
		}
	}
	
	private boolean verificarExistenciaAlumnoMateria(Inscripcion inscripcion) {
		List<Alumno> alumnos = Bozzola.alumnoController.getAlumnos().stream().collect(Collectors.toList());
		List<Materia> materias = Bozzola.materiaController.getMaterias().stream().collect(Collectors.toList());
		Optional<Alumno> tempAlu = alumnos.stream().filter(alu -> alu.getID() == inscripcion.getNroLibreta()).findFirst();
		Optional<Materia> tempMat = materias.stream().filter(mat -> mat.getID() == inscripcion.getNroMateria()).findFirst();
		return tempMat.isPresent() && tempAlu.isPresent();
	}
	
	public void modificarInscripcion(Inscripcion inscripcion) {
		inscripciones.stream().forEach(ins -> {
			if(ins.getID() == inscripcion.getID()) {
				ins.setNotaParcial1(inscripcion.getNotaParcial1());
				ins.setNotaParcial2(inscripcion.getNotaParcial2());
				ins.setEstado(evaluarEstado(ins.getNotaParcial1(), ins.getNotaParcial2()));
			}
		});
	}
	
	public boolean quitarInscripcion(int index) {
		return inscripciones.removeIf(ins -> ins.getID() == index);
	}
	
	private String evaluarEstado(Integer nota1, Integer nota2) {
		if(nota1 >= 7.99 && nota2 >= 7.99) return "PROMOCIONADO";
		else {
			if(nota1 >= 5.99 && nota2 >= 5.99) return "REGULAR";
			else return "LIBRE";
		}
}
}
