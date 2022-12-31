package com.application.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AlumnosTableModel extends AbstractTableModel {

	private List<Alumno> alumnos;
	private String[] titulos = { "ID", "Apellido", "Nombre" };
	
	public AlumnosTableModel() {
		alumnos = new ArrayList<Alumno>();
	}

	public void addRow(Alumno alumno) {
		alumnos.add(alumno);
		Collections.sort(alumnos, new Comparator<Alumno>() {
			@Override
			public int compare(Alumno o1, Alumno o2) {
				return o1.getID().compareTo(o2.getID());
			}
		});
		fireTableRowsInserted(alumnos.size() - 1, alumnos.size() - 1);
	}
	
	public void removeAll() {
		int size = alumnos.size() -1;
		alumnos.clear();
		fireTableRowsDeleted(0, size);
	}
	
	public void removeRow(Alumno alumno) {
		if(alumnos.contains(alumno)) {
			int index = alumnos.indexOf(alumno);
			alumnos.remove(alumno);
			fireTableRowsDeleted(index, index);
		}
	}
	
	@Override
	public int getRowCount() {
		return alumnos.size();
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
		Alumno alumno = alumnos.get(rowIndex);
		Object valor = null;
		switch (columnIndex) {
			case 0: {
				valor = alumno.getID();
				break;
				}
			case 1:{
				valor = alumno.getApellido();
				break;
				}
			case 2:{
				valor = alumno.getNombre();
				break;
			}
		}
		return valor;
	}

}
