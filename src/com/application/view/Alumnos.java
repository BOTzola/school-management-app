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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;

import com.application.controller.AlumnoController;
import com.application.model.Alumno;
import com.application.model.AlumnosTableModel;
import com.application.model.FileManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Alumnos extends JFrame {

	private Alumno alumnoModif;
	private JPanel contentPane;
	private JTextField txtAgregar_Nombre;
	private JTextField txtAgregar_Apellido;
	private JTextField txtModif_Nombre;
	private JTextField txtModif_Apellido;
	private int actualX;
	private int actualY;
	private JTable table_1;
	final AlumnosTableModel tableModel = new AlumnosTableModel();
	public static AlumnoController controller = Bozzola.alumnoController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Alumnos frame = new Alumnos();
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
	public Alumnos() {
		setUndecorated(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Bozzola menu = new Bozzola();
				menu.setVisible(true);
			}

			@Override
			public void windowOpened(WindowEvent e) {
				if (controller.getAlumnos().size() > 0) {
					controller.getAlumnos().forEach(tableModel::addRow);
				}
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 735, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JPanel panelAgregar = new JPanel();
		JPanel panelAlumnos = new JPanel();
		JPanel panelVistas = new JPanel();
		JPanel panelModificar = new JPanel();
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
				panelVistas.add(panelAlumnos);
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
				getAlumno(alumnoModif.getID());
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
						"Esta seguro de que desea quitar este alumno?") == JOptionPane.YES_OPTION) {
					if (controller.quitarAlumno(alumnoModif.getID())) {
						tableModel.removeRow(alumnoModif);
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

		panelAlumnos.setBounds(2, 0, 594, 410);
		panelVistas.add(panelAlumnos);
		panelAlumnos.setLayout(null);
		JPanel btnModificar = new JPanel();
		btnModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				alumnoModif.setNombre(txtModif_Nombre.getText());
				alumnoModif.setApellido(txtModif_Apellido.getText());
				controller.modificarAlumno(alumnoModif);
				tableModel.removeAll();
				controller.getAlumnos().forEach(tableModel::addRow);
				tableModel.fireTableDataChanged();
				panelVistas.removeAll();
				panelVistas.repaint();
				panelVistas.revalidate();
				panelVistas.add(panelAlumnos);
				btnMenu_Listado.setBackground(new Color(70, 130, 181));
				btnMenu_Modificar.setBackground(new Color(30, 144, 255));
				panelVistas.repaint();
				panelVistas.revalidate();
			}
		});
		btnModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		table_1 = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(table_1);
		scrollPane.setBounds(12, 43, 570, 319);
		panelAlumnos.add(scrollPane);

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
						controller.clearAlumnos();
					}
					FileManager fm = new FileManager(fc.getSelectedFile().getAbsolutePath());
					try {
						fm.actuar(Alumnos::convertirAlumno);
						controller.getAlumnos().forEach(tableModel::addRow);
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
		panelAlumnos.add(btnCargarTxt);
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
				List<String> alumnos = controller.getAlumnos().stream().map(alu -> alu.toString())
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
						for (String alumno : alumnos) {
							bw.write(alumno);
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
		panelAlumnos.add(btnGuardarTxt);

		JLabel lblGuardarDatos = new JLabel("Guardar datos");
		lblGuardarDatos.setForeground(new Color(255, 255, 255));
		lblGuardarDatos.setHorizontalAlignment(SwingConstants.CENTER);
		lblGuardarDatos.setBounds(0, 0, 93, 24);
		btnGuardarTxt.add(lblGuardarDatos);

		panelAgregar.setBounds(2, 0, 594, 410);
		panelAgregar.setLayout(null);
		panelVistas.add(panelAgregar);

		txtAgregar_Nombre = new JTextField();
		txtAgregar_Nombre.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtAgregar_Nombre.getText().equals("Ej: Alexis"))
					txtAgregar_Nombre.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtAgregar_Nombre.getText().isEmpty() || txtAgregar_Nombre.getText().isBlank())
					txtAgregar_Nombre.setText("Ej: Alexis");
			}
		});
		txtAgregar_Nombre.setText("Ej: Alexis");
		txtAgregar_Nombre.setForeground(Color.GRAY);
		txtAgregar_Nombre.setColumns(10);
		txtAgregar_Nombre.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		txtAgregar_Nombre.setBackground(Color.WHITE);
		txtAgregar_Nombre.setBounds(245, 75, 221, 31);
		panelAgregar.add(txtAgregar_Nombre);

		txtAgregar_Apellido = new JTextField();
		txtAgregar_Apellido.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtAgregar_Apellido.getText().equals("Ej: Perez"))
					txtAgregar_Apellido.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtAgregar_Apellido.getText().isEmpty() || txtAgregar_Apellido.getText().isBlank())
					txtAgregar_Apellido.setText("Ej: Perez");
			}
		});
		txtAgregar_Apellido.setText("Ej: Perez");
		txtAgregar_Apellido.setForeground(Color.GRAY);
		txtAgregar_Apellido.setColumns(10);
		txtAgregar_Apellido.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		txtAgregar_Apellido.setBackground(Color.WHITE);
		txtAgregar_Apellido.setBounds(245, 132, 221, 31);
		panelAgregar.add(txtAgregar_Apellido);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(95, 82, 55, 16);
		panelAgregar.add(lblNombre);

		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(95, 139, 55, 16);
		panelAgregar.add(lblApellido);

		JLabel lblRequerido = new JLabel("*");
		lblRequerido.setToolTipText("Este campo es requerido");
		lblRequerido.setForeground(Color.RED);
		lblRequerido.setBounds(147, 82, 55, 16);
		panelAgregar.add(lblRequerido);

		JLabel lblRequerido_1 = new JLabel("*");
		lblRequerido_1.setToolTipText("Este campo es requerido");
		lblRequerido_1.setForeground(Color.RED);
		lblRequerido_1.setBounds(147, 139, 55, 16);
		panelAgregar.add(lblRequerido_1);

		JPanel btnAgregar_1 = new JPanel();
		btnAgregar_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAgregar_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!(txtAgregar_Apellido.getText().isBlank() || txtAgregar_Apellido.getText().isEmpty()
						|| txtAgregar_Nombre.getText().isBlank() || txtAgregar_Nombre.getText().isEmpty())) {
					char[] charsApellido = txtAgregar_Apellido.getText().toCharArray();
					char[] charsNombre = txtAgregar_Nombre.getText().toCharArray();
					boolean tieneNums = false;
					for (char c : charsApellido) {
						if (Character.isDigit(c)) {
							JOptionPane.showMessageDialog(null, "El apellido no puede poseer números.");
							tieneNums = true;
							break;
						}
					}
					for (char c1 : charsNombre) {
						if (Character.isDigit(c1)) {
							JOptionPane.showMessageDialog(null, "El nombre no puede poseer números.");
							tieneNums = true;
							break;
						}
					}
					if (!tieneNums) {
						Alumno alu = new Alumno(txtAgregar_Apellido.getText(), txtAgregar_Nombre.getText());
						if(controller.agregarAlumno(alu)) tableModel.addRow(alu);
						panelVistas.removeAll();
						panelVistas.repaint();
						panelVistas.validate();
						panelVistas.add(panelAlumnos);
						panelVistas.repaint();
						panelVistas.validate();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Estos campos son obligatorios, no pueden estar vacíos!");
				}
			}
		});
		btnAgregar_1.setLayout(null);
		btnAgregar_1.setBackground(new Color(30, 144, 255));
		btnAgregar_1.setBounds(231, 236, 143, 46);
		panelAgregar.add(btnAgregar_1);

		JLabel lblAgregar_1 = new JLabel("Agregar");
		lblAgregar_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblAgregar_1.setForeground(Color.WHITE);
		lblAgregar_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAgregar_1.setBounds(0, 0, 143, 46);
		btnAgregar_1.add(lblAgregar_1);

		panelModificar.setBounds(2, 0, 594, 410);
		panelVistas.add(panelModificar);
		panelModificar.setLayout(null);

		txtModif_Nombre = new JTextField();

		txtModif_Nombre.setText("Ej: Alexis");
		txtModif_Nombre.setForeground(Color.GRAY);
		txtModif_Nombre.setColumns(10);
		txtModif_Nombre.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		txtModif_Nombre.setBackground(Color.WHITE);
		txtModif_Nombre.setBounds(245, 75, 221, 31);
		panelModificar.add(txtModif_Nombre);

		txtModif_Apellido = new JTextField();
		txtModif_Apellido.setText("Ej: Perez");
		txtModif_Apellido.setForeground(Color.GRAY);
		txtModif_Apellido.setColumns(10);
		txtModif_Apellido.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		txtModif_Apellido.setBackground(Color.WHITE);
		txtModif_Apellido.setBounds(245, 132, 221, 31);
		panelModificar.add(txtModif_Apellido);

		JLabel lblNombre_1 = new JLabel("Nombre");
		lblNombre_1.setBounds(95, 82, 55, 16);
		panelModificar.add(lblNombre_1);

		JLabel lblApellido_1 = new JLabel("Apellido");
		lblApellido_1.setBounds(95, 139, 55, 16);
		panelModificar.add(lblApellido_1);

		JLabel lblRequerido_2 = new JLabel("*");
		lblRequerido_2.setToolTipText("Este campo es requerido");
		lblRequerido_2.setForeground(Color.RED);
		lblRequerido_2.setBounds(159, 81, 55, 16);
		panelModificar.add(lblRequerido_2);

		JLabel lblRequerido_1_1 = new JLabel("*");
		lblRequerido_1_1.setToolTipText("Este campo es requerido");
		lblRequerido_1_1.setForeground(Color.RED);
		lblRequerido_1_1.setBounds(161, 136, 55, 16);
		panelModificar.add(lblRequerido_1_1);

		btnModificar.setLayout(null);
		btnModificar.setBackground(new Color(30, 144, 255));
		btnModificar.setBounds(231, 236, 143, 46);
		panelModificar.add(btnModificar);

		JLabel lblBtnModificar = new JLabel("Modificar");
		lblBtnModificar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBtnModificar.setForeground(Color.WHITE);
		lblBtnModificar.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBtnModificar.setBounds(0, 0, 143, 46);
		btnModificar.add(lblBtnModificar);

		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int alumnoSelec = table_1.getSelectedRow();
				int id = (Integer) tableModel.getValueAt(alumnoSelec, 0);
				if (alumnoSelec != -1) {
					alumnoModif = controller.getAlumnoById(id);
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
	}

	public void getAlumno(int index) {
		try {
			Optional<Alumno> alumno = Optional.of(controller.getAlumnoById(index));
			alumno.ifPresent(alu -> {
				alumnoModif = alumno.get();
				txtModif_Nombre.setText(alumnoModif.getNombre());
				txtModif_Apellido.setText(alumnoModif.getApellido());
			});
		} catch (NoSuchElementException e) {
			JOptionPane.showMessageDialog(contentPane, e.getMessage());
		}
	}

	public static void convertirAlumno(String linea) throws PatternSyntaxException {
		String[] datos = {};
		boolean tieneNums = false;
		datos = linea.split(":");
		if(datos.length == 3) {
			for (char c : datos[1].toCharArray()) {
				if (Character.isDigit(c)) {
					tieneNums = true;
					break;
				}
			}
			for (char c : datos[2].toCharArray()) {
				if (Character.isDigit(c)) {
					tieneNums = true;
					break;
				}
			}
			if (!tieneNums) {
				Alumno alumno = new Alumno(Integer.parseInt(datos[0]), datos[1], datos[2]);
				controller.agregarAlumno(alumno);
			}
		}
	}
}
