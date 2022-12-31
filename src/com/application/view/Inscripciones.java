package com.application.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;

import com.application.controller.InscripcionController;
import com.application.model.Alumno;
import com.application.model.FileManager;
import com.application.model.Inscripcion;
import com.application.model.InscripcionesTableModel;
import com.application.model.Materia;

public class Inscripciones extends JFrame {

	private Inscripcion inscripModif;
	private JPanel contentPane;
	private JTextField txtAgregar_Nota2;
	private JTextField txtModif_Legajo;
	private int actualX;
	private int actualY;
	private JTable table_1;
	private JList<String> JListLegajos = new JList<String>();
	private JList<String> JListMaterias = new JList<>();
	DefaultListModel<String> materiasModel = new DefaultListModel<String>();
	DefaultListModel<String> alumnosModel = new DefaultListModel<String>();
	final InscripcionesTableModel tableModel = new InscripcionesTableModel();
	public List<Alumno> alumnos = Bozzola.alumnoController.getAlumnos().stream().collect(Collectors.toList());
	public List<Materia> materias = Bozzola.materiaController.getMaterias().stream().collect(Collectors.toList());
	public static InscripcionController controller = Bozzola.inscripcionController;
	private JTextField txtAgregar_Nota1;
	JPanel panelInscripciones = new JPanel();
	JPanel panelModificar = new JPanel();
	JPanel panelAgregar = new JPanel();
	private JTextField txtModif_Materia;
	private JTextField txtModif_Nota1;
	private JTextField txtModif_Nota2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inscripciones frame = new Inscripciones();
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
	public Inscripciones() {
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
				} else {
					DefaultListModel<String> dl = new DefaultListModel<String>();
					dl.addElement("No hay materias inscriptas.");
					JListMaterias.setModel(dl);
					JListMaterias.setSelectedIndex(0);
				}
				if (alumnos.size() > 0) {
					alumnos.stream().sorted(new Comparator<Alumno>() {
						@Override
						public int compare(Alumno o1, Alumno o2) {
							return o1.getID().compareTo(o2.getID());
						}
					}).forEach(alu -> alumnosModel.addElement(alu.getID().toString()));
					JListLegajos.setModel(alumnosModel);
					JListLegajos.setSelectedIndex(0);
				} else {
					DefaultListModel<String> dl = new DefaultListModel<String>();
					dl.addElement("No hay alumnos inscriptos.");
					JListLegajos.setModel(dl);
					JListLegajos.setSelectedIndex(0);
				}
				if (controller.getInscripciones().size() > 0) {
					controller.getInscripciones().forEach(tableModel::addRow);
				}
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 735, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panelVistas = new JPanel();
		panelVistas.add(panelInscripciones);
		JPanel btnMenu_Listado = new JPanel();
		JPanel menuPanel = new JPanel();
		JPanel btnMenu_Modificar = new JPanel();
		JPanel btnMenu_Quitar = new JPanel();
		btnMenu_Modificar.setVisible(false);
		menuPanel.setBackground(new Color(100, 149, 237));
		menuPanel.setBounds(0, 0, 143, 410);
		contentPane.add(menuPanel);
		menuPanel.setLayout(null);

		JPanel btnMenu_Agregar = new JPanel();
		btnMenu_Agregar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!btnMenu_Agregar.getBackground().equals(new Color(70, 130, 181)))
					btnMenu_Agregar.setBackground(new Color(70, 130, 180));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!btnMenu_Agregar.getBackground().equals(new Color(70, 130, 181)))
					btnMenu_Agregar.setBackground(new Color(30, 144, 255));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				btnMenu_Modificar.setVisible(false);
				btnMenu_Quitar.setVisible(false);
				btnMenu_Listado.setBackground(new Color(30, 144, 255));
				btnMenu_Modificar.setBackground(new Color(30, 144, 255));
				panelVistas.removeAll();
				panelVistas.repaint();
				panelVistas.revalidate();
				panelVistas.add(panelAgregar);
				btnMenu_Agregar.setBackground(new Color(70, 130, 181));
				panelVistas.repaint();
				panelVistas.revalidate();
			}
		});

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
				btnMenu_Agregar.setBackground(new Color(30, 144, 255));
				btnMenu_Modificar.setBackground(new Color(30, 144, 255));
				panelVistas.removeAll();
				panelVistas.repaint();
				panelVistas.revalidate();
				panelVistas.add(panelInscripciones);
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
		btnMenu_Agregar.setBackground(new Color(30, 144, 255));
		btnMenu_Agregar.setBounds(0, 133, 143, 46);
		menuPanel.add(btnMenu_Agregar);
		btnMenu_Agregar.setLayout(null);

		JLabel lblMenu_Agregar = new JLabel("Agregar");
		lblMenu_Agregar.setForeground(new Color(255, 255, 255));
		lblMenu_Agregar.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMenu_Agregar.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu_Agregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblMenu_Agregar.setBounds(0, 0, 143, 46);
		btnMenu_Agregar.add(lblMenu_Agregar);

		btnMenu_Modificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnMenu_Modificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!btnMenu_Modificar.getBackground().equals(new Color(70, 130, 181)))
					btnMenu_Modificar.setBackground(new Color(70, 130, 180));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!btnMenu_Modificar.getBackground().equals(new Color(70, 130, 181)))
					btnMenu_Modificar.setBackground(new Color(30, 144, 255));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				btnMenu_Quitar.setVisible(false);
				btnMenu_Agregar.setBackground(new Color(30, 144, 255));
				btnMenu_Listado.setBackground(new Color(30, 144, 255));
				panelVistas.removeAll();
				panelVistas.repaint();
				panelVistas.revalidate();
				btnMenu_Modificar.setBackground(new Color(70, 130, 181));
				getInscripcion(inscripModif.getID());
				panelVistas.add(panelModificar);
				panelVistas.repaint();
				panelVistas.revalidate();
			}
		});
		btnMenu_Modificar.setBackground(new Color(30, 144, 255));
		btnMenu_Modificar.setBounds(0, 180, 143, 46);
		menuPanel.add(btnMenu_Modificar);
		btnMenu_Modificar.setLayout(null);

		JLabel lblMenu_Modificar = new JLabel("Modificar");
		lblMenu_Modificar.setVisible(false);
		lblMenu_Modificar.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu_Modificar.setForeground(Color.WHITE);
		lblMenu_Modificar.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMenu_Modificar.setBounds(0, 0, 143, 46);
		btnMenu_Modificar.add(lblMenu_Modificar);

		btnMenu_Quitar.setVisible(false);
		btnMenu_Quitar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnMenu_Quitar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnMenu_Quitar.setBackground(new Color(70, 130, 180));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnMenu_Quitar.setBackground(new Color(30, 144, 255));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				btnMenu_Quitar.setBackground(new Color(30, 144, 255));
				if (JOptionPane.showConfirmDialog(null,
						"Esta seguro de que desea quitar esta inscripcion?") == JOptionPane.YES_OPTION) {
					if (controller.quitarInscripcion(inscripModif.getID())) {
						tableModel.removeRow(inscripModif);
						btnMenu_Quitar.setVisible(false);
						btnMenu_Modificar.setVisible(false);
					} else {

					}
				}
			}
		});
		btnMenu_Quitar.setBackground(new Color(30, 144, 255));
		btnMenu_Quitar.setBounds(0, 227, 143, 46);
		menuPanel.add(btnMenu_Quitar);
		btnMenu_Quitar.setLayout(null);

		JLabel lblMenu_Quitar = new JLabel("Quitar");
		lblMenu_Quitar.setVisible(false);
		lblMenu_Quitar.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu_Quitar.setForeground(Color.WHITE);
		lblMenu_Quitar.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMenu_Quitar.setBounds(0, 0, 143, 46);
		btnMenu_Quitar.add(lblMenu_Quitar);

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

		JPanel btnModificar = new JPanel();
		btnModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Integer.parseInt(txtAgregar_Nota1.getText()) > 10
						|| Integer.parseInt(txtAgregar_Nota2.getText()) > 10
						|| Integer.parseInt(txtAgregar_Nota1.getText()) < 0
						|| Integer.parseInt(txtAgregar_Nota2.getText()) < 0) {
					JOptionPane.showMessageDialog(null, "Las notas tienen que ser valores entre 0 o 10 inclusives");
				}else {
					inscripModif.setNotaParcial1(Integer.parseInt(txtModif_Nota1.getText()));
					inscripModif.setNotaParcial2(Integer.parseInt(txtModif_Nota2.getText()));
					controller.modificarInscripcion(inscripModif);
					tableModel.removeAll();
					controller.getInscripciones().forEach(tableModel::addRow);
					tableModel.fireTableDataChanged();
					panelVistas.removeAll();
					panelVistas.repaint();
					panelVistas.revalidate();
					panelVistas.add(panelInscripciones);
					btnMenu_Listado.setBackground(new Color(70, 130, 181));
					btnMenu_Modificar.setBackground(new Color(30, 144, 255));
					panelVistas.repaint();
					panelVistas.revalidate();
				}
			}
		});
		table_1 = new JTable(tableModel);

		panelInscripciones.setBounds(2, 0, 594, 410);

		panelInscripciones.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table_1);
		scrollPane.setBounds(12, 43, 570, 319);
		panelInscripciones.add(scrollPane);

		scrollPane.setViewportView(table_1);

		JPanel btnCargarTxt = new JPanel();
		btnCargarTxt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCargarTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCargarTxt.setBackground(new Color(70, 130, 180));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCargarTxt.setBackground(new Color(100, 149, 237));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						if (f.isDirectory())
							return true;
						else {
							String filename = f.getName().toLowerCase();
							return filename.endsWith(".txt");
						}
					}

					@Override
					public String getDescription() {
						return "Archivo TXT (*.txt)";
					}

				});
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					btnMenu_Modificar.setVisible(false);
					btnMenu_Quitar.setVisible(false);
					if (tableModel.getRowCount() > 0) {
						tableModel.removeAll();
						controller.clearInscripciones();
					}
					FileManager fm = new FileManager(fc.getSelectedFile().getAbsolutePath());
					try {
						fm.actuar(Inscripciones::convertirInscripcion);
						controller.getInscripciones().forEach(tableModel::addRow);
						tableModel.fireTableDataChanged();
					} catch (NumberFormatException exe2) {
						JOptionPane.showMessageDialog(null,
								"Este archivo contiene datos invalidos, por favor verifique e intente nuevamente.");
					} catch (NullPointerException exe) {
						JOptionPane.showMessageDialog(null, "Error al intentar crear alumno.");
					} catch (PatternSyntaxException exe1) {
						JOptionPane.showMessageDialog(null,
								"Este archivo contiene datos invalidos, por favor verifique e intente nuevamente.");
					}
				}
			}
		});
		btnCargarTxt.setBackground(new Color(100, 149, 237));
		btnCargarTxt.setBounds(22, 374, 93, 24);
		panelInscripciones.add(btnCargarTxt);
		btnCargarTxt.setLayout(null);

		JLabel lblNewLabel = new JLabel("Cargar datos");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 93, 24);
		btnCargarTxt.add(lblNewLabel);

		JPanel btnGuardarTxt = new JPanel();
		btnGuardarTxt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGuardarTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnGuardarTxt.setBackground(new Color(70, 130, 180));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnGuardarTxt.setBackground(new Color(100, 149, 237));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				List<String> inscripciones = controller.getInscripciones().stream().map(ins -> ins.toString())
						.collect(Collectors.toList());
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				fc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						if (f.isDirectory())
							return true;
						else {
							String filename = f.getName().toLowerCase();
							return filename.endsWith(".txt");
						}
					}

					@Override
					public String getDescription() {
						return "Archivo TXT (*.txt)";
					}

				});
				int returnVal = fc.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						FileWriter fw = new FileWriter(fc.getSelectedFile());
						BufferedWriter bw = Files.newBufferedWriter(Path.of(fc.getSelectedFile().getAbsolutePath()));
						for (String inscripcion : inscripciones) {
							bw.write(inscripcion);
							bw.newLine();
							bw.flush();
						}
						bw.close();
						fw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnGuardarTxt.setBackground(new Color(100, 149, 237));
		btnGuardarTxt.setLayout(null);
		btnGuardarTxt.setBounds(127, 374, 93, 24);
		panelInscripciones.add(btnGuardarTxt);

		JLabel lblGuardarDatos = new JLabel("Guardar datos");
		lblGuardarDatos.setForeground(new Color(255, 255, 255));
		lblGuardarDatos.setHorizontalAlignment(SwingConstants.CENTER);
		lblGuardarDatos.setBounds(0, 0, 93, 24);
		btnGuardarTxt.add(lblGuardarDatos);

		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int inscripSelec = table_1.getSelectedRow();
				int id = (Integer) tableModel.getValueAt(inscripSelec, 0);
				if (inscripSelec != -1) {
					inscripModif = controller.getInscripcionById(id);
					btnMenu_Modificar.setVisible(true);
					lblMenu_Modificar.setVisible(true);
					btnMenu_Quitar.setVisible(true);
					lblMenu_Quitar.setVisible(true);
				} else {
					btnMenu_Modificar.setVisible(false);
					lblMenu_Modificar.setVisible(false);
					btnMenu_Quitar.setVisible(false);
					lblMenu_Quitar.setVisible(false);
				}
			}
		});
		btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		panelModificar.setBounds(2, 0, 594, 410);
		panelVistas.add(panelModificar);
		panelModificar.setLayout(null);

		txtModif_Legajo = new JTextField();
		txtModif_Legajo.setEditable(false);
		txtModif_Legajo.setForeground(Color.GRAY);
		txtModif_Legajo.setColumns(10);
		txtModif_Legajo.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		txtModif_Legajo.setBackground(Color.WHITE);
		txtModif_Legajo.setBounds(245, 75, 221, 31);
		panelModificar.add(txtModif_Legajo);

		JLabel lblNombre_1 = new JLabel("Legajo Alumno:");
		lblNombre_1.setBounds(55, 82, 95, 16);
		panelModificar.add(lblNombre_1);

		JLabel lblRequerido_2 = new JLabel("*");
		lblRequerido_2.setToolTipText("Este campo es requerido");
		lblRequerido_2.setForeground(Color.RED);
		lblRequerido_2.setBounds(159, 81, 55, 16);
		panelModificar.add(lblRequerido_2);

		btnModificar.setLayout(null);
		btnModificar.setBackground(new Color(30, 144, 255));
		btnModificar.setBounds(232, 323, 143, 46);
		panelModificar.add(btnModificar);

		JLabel lblBtnModificar = new JLabel("Modificar");
		lblBtnModificar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBtnModificar.setForeground(Color.WHITE);
		lblBtnModificar.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBtnModificar.setBounds(0, 0, 143, 46);
		btnModificar.add(lblBtnModificar);

		txtModif_Materia = new JTextField();
		txtModif_Materia.setEditable(false);
		txtModif_Materia.setForeground(Color.GRAY);
		txtModif_Materia.setColumns(10);
		txtModif_Materia.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		txtModif_Materia.setBackground(Color.WHITE);
		txtModif_Materia.setBounds(245, 116, 221, 31);
		panelModificar.add(txtModif_Materia);

		txtModif_Nota1 = new JTextField();
		txtModif_Nota1.setForeground(Color.GRAY);
		txtModif_Nota1.setColumns(10);
		txtModif_Nota1.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		txtModif_Nota1.setBackground(Color.WHITE);
		txtModif_Nota1.setBounds(245, 158, 221, 31);
		panelModificar.add(txtModif_Nota1);

		txtModif_Nota2 = new JTextField();
		txtModif_Nota2.setForeground(Color.GRAY);
		txtModif_Nota2.setColumns(10);
		txtModif_Nota2.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		txtModif_Nota2.setBackground(Color.WHITE);
		txtModif_Nota2.setBounds(245, 194, 221, 31);
		panelModificar.add(txtModif_Nota2);

		JLabel lblNombre_1_1 = new JLabel("Materia:");
		lblNombre_1_1.setBounds(55, 118, 95, 16);
		panelModificar.add(lblNombre_1_1);

		JLabel lblRequerido_2_1 = new JLabel("*");
		lblRequerido_2_1.setToolTipText("Este campo es requerido");
		lblRequerido_2_1.setForeground(Color.RED);
		lblRequerido_2_1.setBounds(159, 117, 55, 16);
		panelModificar.add(lblRequerido_2_1);

		JLabel lblNombre_1_1_1 = new JLabel("Nota Parcial 1:");
		lblNombre_1_1_1.setBounds(55, 166, 95, 16);
		panelModificar.add(lblNombre_1_1_1);

		JLabel lblNombre_1_1_1_1 = new JLabel("Nota Parcial 2:");
		lblNombre_1_1_1_1.setBounds(55, 202, 95, 16);
		panelModificar.add(lblNombre_1_1_1_1);

		panelAgregar.setBounds(2, 0, 594, 410);
		panelAgregar.setLayout(null);
		panelVistas.add(panelAgregar);

		txtAgregar_Nota2 = new JTextField();
		txtAgregar_Nota2.setText("0");
		txtAgregar_Nota2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtAgregar_Nota2.getText().isEmpty() || txtAgregar_Nota2.getText().isBlank())
					txtAgregar_Nota2.setText("0");
			}
		});
		txtAgregar_Nota2.setForeground(Color.GRAY);
		txtAgregar_Nota2.setColumns(10);
		txtAgregar_Nota2.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		txtAgregar_Nota2.setBackground(Color.WHITE);
		txtAgregar_Nota2.setBounds(245, 202, 221, 31);
		panelAgregar.add(txtAgregar_Nota2);

		JLabel lblNombre = new JLabel("Legajo Alumno:");
		lblNombre.setBounds(48, 82, 102, 16);
		panelAgregar.add(lblNombre);

		JLabel lblRequerido = new JLabel("*");
		lblRequerido.setToolTipText("Este campo es requerido");
		lblRequerido.setForeground(Color.RED);
		lblRequerido.setBounds(147, 82, 55, 16);
		panelAgregar.add(lblRequerido);

		JPanel btnAgregar_1 = new JPanel();
		btnAgregar_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAgregar_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (JListLegajos.getSelectedValue() == "No hay alumnos inscriptos.") {
					JOptionPane.showMessageDialog(null, "Asegurese de que haya alumnos cargados.");
				} else if (JListMaterias.getSelectedValue() == "No hay materias inscriptas.") {
					JOptionPane.showMessageDialog(null, "Asegurese de que haya materias cargadas.");
				} else {
					try {
					if (Integer.parseInt(txtAgregar_Nota1.getText()) > 10
							|| Integer.parseInt(txtAgregar_Nota2.getText()) > 10
							|| Integer.parseInt(txtAgregar_Nota1.getText()) < 0
							|| Integer.parseInt(txtAgregar_Nota2.getText()) < 0) {
						JOptionPane.showMessageDialog(null, "Las notas tienen que ser valores entre 0 o 10 inclusives");
					} else {
							Integer nroMateria = ((Materia) materias.stream()
									.filter(mat -> mat.getNombre() == JListMaterias.getSelectedValue()).toArray()[0])
											.getID();
							Inscripcion inscripcion = new Inscripcion(Integer.parseInt(JListLegajos.getSelectedValue()),
									nroMateria, Integer.parseInt(txtAgregar_Nota1.getText()),
									Integer.parseInt(txtAgregar_Nota2.getText()));
							if (controller.agregarInscripcion(inscripcion))
								tableModel.addRow(inscripcion);
							panelVistas.removeAll();
							panelVistas.repaint();
							panelVistas.validate();
							panelVistas.add(panelInscripciones);
							panelVistas.repaint();
							panelVistas.validate();
						
					}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Se insertaron valores incorrectos, por favor verifique.");
					}
				}
			}
		});
		btnAgregar_1.setLayout(null);
		btnAgregar_1.setBackground(new Color(30, 144, 255));
		btnAgregar_1.setBounds(231, 322, 143, 46);
		panelAgregar.add(btnAgregar_1);

		JLabel lblAgregar_1 = new JLabel("Agregar");
		lblAgregar_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblAgregar_1.setForeground(Color.WHITE);
		lblAgregar_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAgregar_1.setBounds(0, 0, 143, 46);
		btnAgregar_1.add(lblAgregar_1);

		txtAgregar_Nota1 = new JTextField();
		txtAgregar_Nota1.setText("0");
		txtAgregar_Nota1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtAgregar_Nota1.getText().isEmpty() || txtAgregar_Nota1.getText().isBlank())
					txtAgregar_Nota1.setText("0");
			}
		});
		txtAgregar_Nota1.setForeground(Color.GRAY);
		txtAgregar_Nota1.setColumns(10);
		txtAgregar_Nota1.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		txtAgregar_Nota1.setBackground(Color.WHITE);
		txtAgregar_Nota1.setBounds(245, 155, 221, 31);
		panelAgregar.add(txtAgregar_Nota1);

		JLabel lblNombre_2 = new JLabel("Materia:");
		lblNombre_2.setBounds(87, 120, 63, 16);
		panelAgregar.add(lblNombre_2);

		JLabel lblRequerido_1 = new JLabel("*");
		lblRequerido_1.setToolTipText("Este campo es requerido");
		lblRequerido_1.setForeground(Color.RED);
		lblRequerido_1.setBounds(147, 120, 55, 16);
		panelAgregar.add(lblRequerido_1);

		JLabel lblNombre_2_1 = new JLabel("Nota Parcial 1:");
		lblNombre_2_1.setBounds(48, 155, 102, 16);
		panelAgregar.add(lblNombre_2_1);

		JLabel lblNombre_2_1_1 = new JLabel("Nota Parcial 2:");
		lblNombre_2_1_1.setBounds(48, 202, 102, 16);
		panelAgregar.add(lblNombre_2_1_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(245, 112, 221, 24);
		panelAgregar.add(scrollPane_1);
		scrollPane_1.setViewportView(JListMaterias);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(245, 82, 219, 22);
		panelAgregar.add(scrollPane_2);

		scrollPane_2.setViewportView(JListLegajos);
	}

	public void getInscripcion(int index) {
		try {
			Optional<Inscripcion> inscripcion = Optional.of(controller.getInscripcionById(index));
			inscripcion.ifPresent(ins -> {
				inscripModif = inscripcion.get();
				txtModif_Legajo.setText(inscripModif.getNroLibreta().toString());
				txtModif_Materia.setText(inscripModif.getNroMateria().toString());
				txtModif_Nota1.setText(inscripModif.getNotaParcial1().toString());
				txtModif_Nota2.setText(inscripModif.getNotaParcial2().toString());
			});
		} catch (NoSuchElementException e) {
			JOptionPane.showMessageDialog(contentPane, e.getMessage());
		}
	}

	public static void convertirInscripcion(String linea) throws PatternSyntaxException, NumberFormatException {
		String[] datos = {};
		datos = linea.split(":");

		Inscripcion inscripcion = new Inscripcion(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]),
				Integer.parseInt(datos[2]), Integer.parseInt(datos[3]), Integer.parseInt(datos[4]));
		controller.agregarInscripcion(inscripcion);
	}
}
