package com.application.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.application.controller.AlumnoController;
import com.application.controller.InscripcionController;
import com.application.controller.MateriaController;

public class Bozzola extends JFrame {

	private JPanel contentPane;
	private Integer actualY;
	private Integer actualX;
	public static MateriaController materiaController = new MateriaController();
	public static InscripcionController inscripcionController = new InscripcionController();
	public static AlumnoController alumnoController = new AlumnoController();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bozzola frame = new Bozzola();
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
	public Bozzola() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 260, 332);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		setUndecorated(true);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel btnAlumnos = new JPanel();
		btnAlumnos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btnAlumnos.setBackground(new Color(100, 149, 237));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAlumnos.setBackground(new Color(30, 144, 255));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Alumnos alumnosFrame = new Alumnos();
				alumnosFrame.setVisible(true);
				setVisible(false);
			}
		});
		btnAlumnos.setBackground(new Color(100, 149, 237));
		btnAlumnos.setBounds(10, 50, 240, 63);
		contentPane.add(btnAlumnos);
		btnAlumnos.setLayout(null);
		
		JLabel lblAlumnos = new JLabel("Alumnos");
		lblAlumnos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAlumnos.setForeground(new Color(255, 255, 255));
		lblAlumnos.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlumnos.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblAlumnos.setBounds(0, 0, 240, 63);
		btnAlumnos.add(lblAlumnos);
		
		JPanel btnSalir = new JPanel();
		btnSalir.setForeground(new Color(255, 255, 255));
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSalir.setBackground(new Color(139, 0, 0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSalir.setBackground(new Color(165, 42, 42));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null,"Salida del programa de Bózzola");
				System.exit(0);
			}
		});
		btnSalir.setBackground(new Color(165, 42, 42));
		btnSalir.setBounds(221, 0, 39, 23);
		btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		contentPane.add(btnSalir);
		btnSalir.setLayout(null);
		
		JLabel lblX = new JLabel("X");
		lblX.setForeground(new Color(255, 255, 255));
		lblX.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblX.setBounds(0, 0, 39, 23);
		btnSalir.add(lblX);
		
		JPanel btnMaterias = new JPanel();
		btnMaterias.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnMaterias.setBackground(new Color(30, 144, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnMaterias.setBackground(new Color(100, 149, 237));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Materias materias = new Materias();
				setVisible(false);
				materias.setVisible(true);
			}
		});
		btnMaterias.setLayout(null);
		btnMaterias.setBackground(new Color(100, 149, 237));
		btnMaterias.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnMaterias.setBounds(10, 116, 240, 63);
		contentPane.add(btnMaterias);
		
		JLabel lblMaterias = new JLabel("Materias");
		lblMaterias.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaterias.setForeground(Color.WHITE);
		lblMaterias.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMaterias.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblMaterias.setBounds(0, 0, 240, 63);
		btnMaterias.add(lblMaterias);
		
		JPanel btnInscripciones = new JPanel();
		btnInscripciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnInscripciones.setBackground(new Color(30, 144, 255));
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				btnInscripciones.setBackground(new Color(100, 149, 237));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Inscripciones inscripciones = new Inscripciones();
				setVisible(false);
				inscripciones.setVisible(true);
			}
		});
		btnInscripciones.setLayout(null);
		btnInscripciones.setBackground(new Color(100, 149, 237));
		btnInscripciones.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnInscripciones.setBounds(10, 183, 240, 63);
		contentPane.add(btnInscripciones);
		
		JLabel lblInscripciones = new JLabel("Inscripciones");
		lblInscripciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblInscripciones.setForeground(Color.WHITE);
		lblInscripciones.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInscripciones.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblInscripciones.setBounds(0, 0, 240, 63);
		btnInscripciones.add(lblInscripciones);
		
		JPanel btnConsultas = new JPanel();
		btnConsultas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnConsultas.setBackground(new Color(30, 144, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnConsultas.setBackground(new Color(100, 149, 237));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Consultas con = new Consultas();
				setVisible(false);
				con.setVisible(true);
			}
		});
		btnConsultas.setLayout(null);
		btnConsultas.setBackground(new Color(100, 149, 237));
		btnConsultas.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnConsultas.setBounds(10, 250, 240, 63);
		contentPane.add(btnConsultas);
		
		JLabel lblConsultas = new JLabel("Consultas");
		lblConsultas.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsultas.setForeground(Color.WHITE);
		lblConsultas.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblConsultas.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblConsultas.setBounds(0, 0, 240, 63);
		btnConsultas.add(lblConsultas);
		
		JPanel panel = new JPanel();
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int y = e.getYOnScreen();
				int x = e.getXOnScreen();
				setLocation(x - actualX, y - actualY);
			}
		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				actualY = e.getY();
				actualX = e.getX();
			}
		});
		panel.setBackground(new Color(135, 206, 250));
		panel.setBounds(0, 0, 260, 23);
		contentPane.add(panel);
	}
}
