package com.application.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.application.model.Alumno;
import com.application.model.Inscripcion;
import com.application.model.Materia;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Consultas extends JFrame {

	private Materia materiaSelec;
	JList<String> JListMaterias = new JList<String>();
	private JPanel contentPane;
	private int actualX;
	private int actualY;
	private JTable table_1;
	DefaultListModel<String> materiasModel = new DefaultListModel<String>();
	private List<Alumno> alumnos = Bozzola.alumnoController.getAlumnos().stream().collect(Collectors.toList());
	private List<Materia> materias = Bozzola.materiaController.getMaterias().stream().collect(Collectors.toList());
	private List<Inscripcion> inscripciones = Bozzola.inscripcionController.getInscripciones().stream()
			.collect(Collectors.toList());

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Consultas frame = new Consultas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Consultas() {
		setUndecorated(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Bozzola menu = new Bozzola();
				menu.setVisible(true);
			}

			@Override
			public void windowOpened(WindowEvent e) {
				if (materias.size() > 0) {
					materias.stream().sorted(new Comparator<Materia>() {
						@Override
						public int compare(Materia o1, Materia o2) {
							return o1.getID().compareTo(o2.getID());
						}
					}).forEach(mat -> materiasModel.addElement(mat.getNombre()));
					JListMaterias.setModel(materiasModel);
					JListMaterias.setSelectedIndex(0);
					materiaSelec = (Materia) materias.stream()
							.filter(mat -> mat.getNombre() == JListMaterias.getSelectedValue()).toArray()[0];
					cargoTabla();
				} else {
					DefaultListModel<String> dl = new DefaultListModel<String>();
					dl.addElement("No hay materias inscriptas.");
					JListMaterias.setModel(dl);
					JListMaterias.setSelectedIndex(0);
				}
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 735, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		table_1 = new JTable(new DefaultTableModel(new Object[][] {},
				new String[] { "Legajo", "Apellido", "Nombre", "Nota Parcial 1", "Nota Parcial 2" }));
		
		JPanel panelMaterias = new JPanel();
		JPanel panelVistas = new JPanel();
		JPanel btnMenu_Listado = new JPanel();
		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(new Color(100, 149, 237));
		menuPanel.setBounds(0, 0, 143, 410);
		contentPane.add(menuPanel);
		menuPanel.setLayout(null);

		btnMenu_Listado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!btnMenu_Listado.getBackground().equals(new Color(70, 130, 181)))
					btnMenu_Listado.setBackground(new Color(70, 130, 180));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!btnMenu_Listado.getBackground().equals(new Color(70, 130, 181)))
					btnMenu_Listado.setBackground(new Color(30, 144, 255));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				panelVistas.removeAll();
				panelVistas.repaint();
				panelVistas.revalidate();
				panelVistas.add(panelMaterias);
				btnMenu_Listado.setBackground(new Color(70, 130, 181));
				panelVistas.repaint();
				panelVistas.revalidate();
			}
		});
		btnMenu_Listado.setLayout(null);
		btnMenu_Listado.setBackground(new Color(30, 144, 255));
		btnMenu_Listado.setBounds(0, 86, 143, 46);
		menuPanel.add(btnMenu_Listado);

		JLabel lblMenu_Listado = new JLabel("Listado");
		lblMenu_Listado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblMenu_Listado.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu_Listado.setForeground(Color.WHITE);
		lblMenu_Listado.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMenu_Listado.setBounds(0, 0, 143, 46);
		btnMenu_Listado.add(lblMenu_Listado);

		JLabel lblMenu_Vistas = new JLabel("Vistas");
		lblMenu_Vistas.setForeground(Color.WHITE);
		lblMenu_Vistas.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu_Vistas.setBounds(44, 63, 55, 16);
		menuPanel.add(lblMenu_Vistas);

		Panel logoPanel = new Panel();
		logoPanel.setLayout(null);
		logoPanel.setBounds(0, 0, 143, 57);
		menuPanel.add(logoPanel);

		JLabel utnLogo = new JLabel("");
		utnLogo.setHorizontalAlignment(SwingConstants.CENTER);
		utnLogo.setBackground(Color.BLACK);
		utnLogo.setBounds(0, 0, 143, 57);
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\bin\\com\\application\\img\\logo.png");
		Image imageIcon = icon.getImage();
		utnLogo.setIcon(new ImageIcon(imageIcon.getScaledInstance(143, 57, java.awt.Image.SCALE_SMOOTH)));
		logoPanel.add(utnLogo);

		JPanel btnMenu_Volver = new JPanel();
		btnMenu_Volver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnMenu_Volver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Bozzola menu = new Bozzola();
				setVisible(false);
				menu.setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnMenu_Volver.setBackground(new Color(70, 130, 180));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnMenu_Volver.setBackground(new Color(30, 144, 255));
			}
		});
		btnMenu_Volver.setLayout(null);
		btnMenu_Volver.setBackground(new Color(30, 144, 255));
		btnMenu_Volver.setBounds(0, 350, 143, 46);
		menuPanel.add(btnMenu_Volver);

		JLabel lblMenu_Volver = new JLabel("Volver");
		lblMenu_Volver.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu_Volver.setForeground(Color.WHITE);
		lblMenu_Volver.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMenu_Volver.setBounds(0, 0, 143, 46);
		btnMenu_Volver.add(lblMenu_Volver);

		JPanel panel = new JPanel();
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - actualX, y - actualY);
			}
		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				actualX = e.getX();
				actualY = e.getY();
			}
		});
		panel.setBounds(0, 0, 735, 33);
		contentPane.add(panel);
		panel.setLayout(null);

		panelVistas.setBounds(141, 0, 594, 410);
		contentPane.add(panelVistas);
		panelVistas.setLayout(null);

		panelMaterias.setBounds(2, 0, 594, 410);
		panelVistas.add(panelMaterias);
		panelMaterias.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table_1);
		scrollPane.setBounds(10, 64, 570, 319);
		panelMaterias.add(scrollPane);

		scrollPane.setViewportView(table_1);

		JLabel lblNewLabel = new JLabel("Listado de promocionados de la materia:");
		lblNewLabel.setBounds(35, 39, 273, 14);
		panelMaterias.add(lblNewLabel);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(318, 37, 205, 20);
		panelMaterias.add(scrollPane_1);
		JListMaterias.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(JListMaterias.getSelectedValue() != "No hay materias inscriptas.")
				{
					materiaSelec = (Materia) materias.stream()
							.filter(mat -> mat.getNombre() == JListMaterias.getSelectedValue()).toArray()[0];
					cargoTabla();
				}
			}
		});


		scrollPane_1.setViewportView(JListMaterias);

	}

	public void cargoTabla() {
		AtomicInteger i = new AtomicInteger(0);
		List<Inscripcion> insMat = getInscripciones();

		String[][] tablaset = new String[insMat.size()][5];
		insMat.forEach(ins -> {
			tablaset[i.get()][0] = ins.getNroLibreta().toString();
			tablaset[i.get()][1] = ((Alumno) alumnos.stream().filter(alu -> alu.getID() == ins.getNroLibreta())
					.toArray()[0]).getApellido();
			tablaset[i.get()][2] = ((Alumno) alumnos.stream().filter(alu -> alu.getID() == ins.getNroLibreta())
					.toArray()[0]).getNombre();
			tablaset[i.get()][3] = ins.getNotaParcial1().toString();
			tablaset[i.getAndIncrement()][4] = ins.getNotaParcial2().toString();
		});
		
		table_1.setModel(new DefaultTableModel(tablaset, new String[] { "Legajo", "Apellido", "Nombre", "Nota Parcial 1", "Nota Parcial 2" }));
		table_1.repaint();
		table_1.revalidate();
	}

	public List<Inscripcion> getInscripciones() {
		return inscripciones.stream()
				.filter(ins -> ins.getNroMateria() == materiaSelec.getID() && ins.getEstado() == "PROMOCIONADO")
				.collect(Collectors.toList());
	}
}
