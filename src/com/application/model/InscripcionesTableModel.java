package com.application.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class InscripcionesTableModel extends AbstractTableModel {

	private List<Inscripcion> inscripciones;
	private String[] titulos = { "ID", "Legajo Alumno", "Nro Materia", "Nota Parcial 1", "Nota Parcial 2", "Estado" };
	
	public InscripcionesTableModel() {
		inscripciones = new ArrayList<Inscripcion>();
	}

	public void addRow(Inscripcion inscripcion) {
		inscripciones.add(inscripcion);
		Collections.sort(inscripciones, new Comparator<Inscripcion>() {
			@Override
			public int compare(Inscripcion o1, Inscripcion o2) {
				return o1.getID().compareTo(o2.getID());
			}
		});
		fireTableRowsInserted(inscripciones.size() - 1, inscripciones.size() - 1);
	}
	
	public void removeAll() {
		int size = inscripciones.size() -1;
		inscripciones.clear();
		fireTableRowsDeleted(0, size);
	}
	
	public void removeRow(Inscripcion inscripcion) {
		if(inscripciones.contains(inscripcion)) {
			int index = inscripciones.indexOf(inscripcion);
			inscripciones.remove(inscripcion);
			fireTableRowsDeleted(index, index);
		}
	}
	
	@Override
	public int getRowCount() {
		return inscripciones.size();
	}

	@Override
	public String getColumnName(int index) {
		return titulos[index];
	}

	@Override
	public int getColumnCount() {
		return titulos.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Inscripcion inscripcion = inscripciones.get(rowIndex);
		Object valor = null;
		switch (columnIndex) {
			case 0: {
				valor = inscripcion.getID();
				break;
				}
			case 1:{
				valor = inscripcion.getNroLibreta();
				break;
				}
			case 2:{
				valor = inscripcion.getNroMateria();
				break;
			}
			case 3:{
				valor = inscripcion.getNotaParcial1();
				break;
			}
			case 4:{
				valor = inscripcion.getNotaParcial2();
				break;
			}
			case 5: {
				valor = inscripcion.getEstado();
				break;
			}
		}
		return valor;
	}

}
