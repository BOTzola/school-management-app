package com.application.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

public class FileManager {

	private List<String> lines;
	
	public FileManager(String path) {
		try {
			lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void actuar(Consumer<String> act) {
		lines.stream().forEach(line -> act.accept(line));
	}
}
