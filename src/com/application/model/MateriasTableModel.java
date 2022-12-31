package com.application.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MateriasTableModel extends AbstractTableModel {

	private List<Materia> materias;
	private String[] titulos = { "ID", "Nombre" };
	
	public MateriasTableModel() {
		materias = new ArrayList<Materia>();
	}

	public void addRow(Materia materia) {
		materias.add(materia);
		Collections.sort(materias, new Comparator<Materia>() {
			@Override
			public int compare(Materia o1, Materia o2) {
				return o1.getID().compareTo(o2.getID());
			}
		});
		fireTableRowsInserted(materias.size() - 1, materias.size() - 1);
	}
	
	public void removeAll() {
		int size = materias.size() -1;
		materias.clear();
		fireTableRowsDeleted(0, size);
	}
	
	public void removeRow(Materia materia) {
		if(materias.contains(materia)) {
			int index = materias.indexOf(materia);
			materias.remove(materia);
			fireTableRowsDeleted(index, index);
		}
	}
	
	@Override
	public int getRowCount() {
		return materias.size();
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
		Materia materia = materias.get(rowIndex);
		Object valor = null;
		switch (columnIndex) {
			case 0: {
				valor = materia.getID();
				break;
				}
			case 1:{
				valor = materia.getNombre();
				break;
				}
		}
		return valor;
	}

}
