package com.application.controller;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.application.model.Materia;

public class MateriaController {

private Set<Materia> materias = new HashSet<Materia>();
	
	public Set<Materia> getMaterias() {
		return materias;
	}
	
	public void clearMaterias() {
		materias.clear();
	}
	
	public Materia getMateriaById(Integer id) {
		return (Materia)materias.stream().filter(mat -> mat.getID() == id).toArray()[0];
	}
	
	public boolean agregarMateria(Materia materia) {
		try {
			return materias.add(materia);
		}catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "La materia es nula.");
			return false;
		}
	}
	
	public void modificarMateria(Materia materia) {
		materias.stream().forEach(mat -> {
			if(mat.getID() == materia.getID()) {
				mat.setNombre(materia.getNombre());
			}
		});
	}
	
	public boolean quitarMateria(int index) {
		return materias.removeIf(mat -> mat.getID() == index);
	}
}
