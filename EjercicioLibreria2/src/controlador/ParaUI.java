package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import modelo.Libreria;
import modelo.Libro;
import utiles.Validaciones;
import vista.UI;

public class ParaUI extends UI {
	Libreria libreria = new Libreria();
	Libro libro;
	private int indiceLibroEditando = -1;

	public ParaUI() {
		// Llamada a la función para generar librerias automaticamente
		generarLibreria();

		// BOTONES
		btnGuardar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String autor = textAutor.getText().trim();
		        String titulo = textTitulo.getText().trim();
		        String editorial = textEditorial.getText().trim();
		        String precioStr = textPrecio.getText().trim();
		        String cantidadStr = textCantidad.getText().trim();
		        String formato = getSelectedRadio(grupoFormato);
		        String estado = getSelectedRadio(grupoEstado);

		        if (!Validaciones.validarLetras(autor)) {
		            JOptionPane.showMessageDialog(null, "El campo Autor solo puede contener letras y signos, sin números.");
		            return;
		        }
		        if (titulo.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "El campo Título no puede estar vacío.");
		            return;
		        }
		        if (editorial.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "El campo Editorial no puede estar vacío.");
		            return;
		        }
		        if (!precioStr.matches("^\\d+(\\.\\d{1,2})?$")) {
		            JOptionPane.showMessageDialog(null, "El precio debe ser un número válido con máximo dos decimales. Ejemplo: 12.50");
		            return;
		        }
		        if (!cantidadStr.matches("\\d+")) {
		            JOptionPane.showMessageDialog(null, "La cantidad debe ser un número entero válido.");
		            return;
		        }
		        if (formato == null || formato.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Debe seleccionar un formato (Cartone, Rustica, Grapada, Espiral).");
		            return;
		        }
		        if (estado == null || estado.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Debe seleccionar un estado (Reedicion o Novedad).");
		            return;
		        }

		        float precio = Float.parseFloat(precioStr);
		        int cantidad = Integer.parseInt(cantidadStr);

		        if (indiceLibroEditando == -1) {
		            String isbn = textISBN.getText().trim();
		            if (!Validaciones.validarISBN(isbn, libreria, -1)) {
		                JOptionPane.showMessageDialog(null, "El ISBN no es válido o ya existe.");
		                return;
		            }
		            Libro libro = new Libro(isbn, autor, titulo, editorial, precio, formato, estado, cantidad);
		            JOptionPane.showMessageDialog(null, "Libro guardado con éxito");
		            libreria.guardarLibro(libro);
		        } else {
		            Libro libroExistente = libreria.getListaLibros().get(indiceLibroEditando);
		            libroExistente.setAutor(autor);
		            libroExistente.setTitulo(titulo);
		            libroExistente.setEditorial(editorial);
		            libroExistente.setPrecio(precio);
		            libroExistente.setFormato(formato);
		            libroExistente.setEstado(estado);
		            libroExistente.setCantidad(cantidad);
		            
		            JOptionPane.showMessageDialog(null, "Libro editado con éxito");

		            indiceLibroEditando = -1;
		        }

		        libreria.mostrarLibros();
		        libreria.rellenarTabla(tablaLibros);
		        limpiarCampos(textISBN, textAutor, textTitulo, textEditorial, textPrecio, textCantidad);

		        textISBN.setEditable(true);
		        textISBN.setBackground(Color.WHITE);
		        
		        panel.setVisible(false);
		        panel_1.setVisible(true);
		    }
		});

		btnEditar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int indice = libreria.obtenerId(tablaLibros);
		        if (indice == -1) {
		            JOptionPane.showMessageDialog(null, "No has seleccionado ningún libro");
		            return;
		        }

		        Libro libroSeleccionado = libreria.getListaLibros().get(indice);

		        panel_1.setVisible(false);
		        panel.setVisible(true);

		        textISBN.setText(libroSeleccionado.getISBN());
		        textISBN.setEditable(false);
		        textISBN.setBackground(Color.LIGHT_GRAY);
		        textISBN.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));

		        textAutor.setText(libroSeleccionado.getAutor());
		        textTitulo.setText(libroSeleccionado.getTitulo());
		        textEditorial.setText(libroSeleccionado.getEditorial());
		        textPrecio.setText(String.valueOf(libroSeleccionado.getPrecio()));
		        textCantidad.setText(String.valueOf(libroSeleccionado.getCantidad()));

		        grupoFormato.clearSelection();
		        switch (libroSeleccionado.getFormato()) {
		            case "Cartone": rdbtnCartone.setSelected(true); break;
		            case "Rustica": rdbtnRustica.setSelected(true); break;
		            case "Grapada": rdbtnGrapada.setSelected(true); break;
		            case "Espiral": rdbtnEspiral.setSelected(true); break;
		        }

		        grupoEstado.clearSelection();
		        switch (libroSeleccionado.getEstado()) {
		            case "Reedicion": rdbtnReedicion.setSelected(true); break;
		            case "Novedad": rdbtnNovedad.setSelected(true); break;
		        }

		        indiceLibroEditando = indice;
		    }
		});

		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indice = libreria.obtenerId(tablaLibros);
				if (indice == -1) {
					JOptionPane.showMessageDialog(null, "No has seleccionado ningún libro");
				} else {
					Libro libroSeleccionado = libreria.getListaLibros().get(indice);

					int respuesta = JOptionPane.showConfirmDialog(null,
							"¿Estás seguro de que deseas borrar el libro:\n\"" + libroSeleccionado.getTitulo() + "\"?",
							"Confirmar borrado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if (respuesta == JOptionPane.YES_OPTION) {
						libreria.borrarLibros(indice);
						libreria.rellenarTabla(tablaLibros);
						JOptionPane.showMessageDialog(null,
								"Libro \"" + libroSeleccionado.getTitulo() + "\" borrado con éxito");
					}
				}
			}
		});

		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String ISBNsel = JOptionPane.showInputDialog("Introduce el ISBN del libro a comprar:");
				if (ISBNsel == null || ISBNsel.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debes introducir un ISBN válido.");
					return;
				}

				Libro libro = libreria.obtenerLibroDos(ISBNsel.trim());
				if (libro == null) {
					JOptionPane.showMessageDialog(null, "No se encontró ningún libro con ese ISBN.");
					return;
				}

				mostrarLibro(libreria.obtenerLibroDos(ISBNsel));
			}
		});

		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String ISBNsel = JOptionPane.showInputDialog("Introduce el ISBN del libro a comprar:");
				if (ISBNsel == null || ISBNsel.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debes introducir un ISBN válido.");
					return;
				}

				Libro libro = libreria.obtenerLibroDos(ISBNsel.trim());
				if (libro == null) {
					JOptionPane.showMessageDialog(null, "No se encontró ningún libro con ese ISBN.");
					return;
				}

				String cantidadStr = JOptionPane.showInputDialog("Introduce la cantidad a comprar:");
				if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debes introducir una cantidad.");
					return;
				}

				int compras = Integer.parseInt(cantidadStr);
				if (compras <= 0) {
					JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que cero.");
					return;
				}

				libro.setCantidad(libro.getCantidad() + compras);
				double total = compras * libro.getPrecio();

				JOptionPane.showMessageDialog(null,
						"Compra realizada con éxito.\n" + "Se añadieron " + compras + " unidades.\n" + "Total: €"
								+ String.format("%.2f", total) + "\n" + "Stock actual: " + libro.getCantidad());

				libreria.rellenarTabla(tablaLibros);
			}
		});

		btnVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String ISBNsel = JOptionPane.showInputDialog("Introduce el ISBN del libro a vender:");
				if (ISBNsel == null || ISBNsel.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debes introducir un ISBN válido.");
					return;
				}

				Libro libro = libreria.obtenerLibroDos(ISBNsel.trim());
				if (libro == null) {
					JOptionPane.showMessageDialog(null, "No se encontró ningún libro con ese ISBN.");
					return;
				}

				String cantidadStr = JOptionPane.showInputDialog("Introduce la cantidad a vender:");
				if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debes introducir una cantidad.");
					return;
				}

				int ventas = Integer.parseInt(cantidadStr);
				if (ventas <= 0) {
					JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que cero.");
					return;
				}

				if (libro.getCantidad() < ventas) {
					JOptionPane.showMessageDialog(null,
							"Stock insuficiente.\nSolo hay " + libro.getCantidad() + " unidades disponibles.");
					return;
				}

				libro.setCantidad(libro.getCantidad() - ventas);
				double totalSinIVA = ventas * libro.getPrecio();
				double totalConIVA = totalSinIVA * 1.21;

				JOptionPane.showMessageDialog(null,
						"Venta realizada con éxito.\n" + "Se vendieron " + ventas + " unidades.\n" + "Total sin IVA: €"
								+ String.format("%.2f", totalSinIVA) + "\n" + "Total con IVA (21%): €"
								+ String.format("%.2f", totalConIVA) + "\n" + "Stock actual: " + libro.getCantidad());

				libreria.rellenarTabla(tablaLibros);

			}
		});

		btnSalida.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int respuesta = JOptionPane.showConfirmDialog(
		                null,
		                "¿Estás seguro de que deseas salir?",
		                "Confirmar salida",
		                JOptionPane.YES_NO_OPTION,
		                JOptionPane.WARNING_MESSAGE
		        );

		        if (respuesta == JOptionPane.YES_OPTION) {
		            System.exit(0);
		        }
		    }
		});

		// Funciones para poder editar los cuadros de texto
		((AbstractDocument) textISBN.getDocument()).setDocumentFilter(new javax.swing.text.DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
					throws BadLocationException {
				if ((fb.getDocument().getLength() + string.length()) <= 13) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				if ((fb.getDocument().getLength() - length + (text == null ? 0 : text.length())) <= 13) {
					super.replace(fb, offset, length, text, attrs);
				}
			}
		});

		textISBN.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
		    public void insertUpdate(javax.swing.event.DocumentEvent e) {
		        validar();
		    }

		    public void removeUpdate(javax.swing.event.DocumentEvent e) {
		        validar();
		    }

		    public void changedUpdate(javax.swing.event.DocumentEvent e) {
		        validar();
		    }

		    private void validar() {
		        if (indiceLibroEditando != -1) {
		            return;
		        }

		        String isbnActual = textISBN.getText().trim();

		        if (Validaciones.validarISBN(isbnActual, libreria, indiceLibroEditando)) {
		            textISBN.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		        } else {
		            textISBN.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		        }
		    }
		});

		textAutor.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			private void validar() {
				if (Validaciones.validarLetras(textAutor.getText())) {
					textAutor.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
				} else {
					textAutor.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				}
			}
		});

		textTitulo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			private void validar() {
				if (textTitulo.getText() != null && !textTitulo.getText().isEmpty()) {
					textTitulo.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
				} else {
					textTitulo.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				}
			}
		});

		textEditorial.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			private void validar() {
				if (textEditorial.getText() != null && !textEditorial.getText().isEmpty()) {
					textEditorial.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
				} else {
					textEditorial.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				}
			}
		});

		textPrecio.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			private void validar() {
				if (Validaciones.isNumberFloat(textPrecio.getText())) {
					textPrecio.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
				} else {
					textPrecio.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				}
			}
		});

		textCantidad.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				validar();
			}

			private void validar() {
				if (Validaciones.isNumber(textCantidad.getText())) {
					textCantidad.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
				} else {
					textCantidad.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				}
			}
		});

	}

	// FUNCIONES AUXILIARES
	// Genera una libreria con 10 libros para probar
	private void generarLibreria() {
		libreria.guardarLibro(new Libro("1111111111111", "Cien años de soledad", "Gabriel García Márquez",
				"Sudamericana", 19.99, "Cartone", "Reedicion", 10));
		libreria.guardarLibro(new Libro("2222222222222", "Don Quijote de la Mancha", "Miguel de Cervantes", "Espasa",
				24.50, "Grapada", "Novedad", 5));
		libreria.guardarLibro(new Libro("3333333333333", "La sombra del viento", "Carlos Ruiz Zafón", "Planeta", 17.95,
				"Rustica", "Reedicion", 8));
		libreria.guardarLibro(
				new Libro("4444444444444", "1984", "George Orwell", "Debolsillo", 12.99, "Espiral", "Novedad", 12));
		libreria.guardarLibro(new Libro("5555555555555", "El Principito", "Antoine de Saint Exupéry", "Salamandra",
				9.50, "Cartone", "Reedicion", 20));
		libreria.guardarLibro(new Libro("6666666666666", "El Hobbit", "J.R.R. Tolkien", "Minotauro", 15.99, "Rustica",
				"Reedicion", 7));
		libreria.guardarLibro(new Libro("7777777777777", "Harry Potter y la piedra filosofal", "J.K. Rowling",
				"Salamandra", 18.50, "Cartone", "Novedad", 12));
		libreria.guardarLibro(new Libro("8888888888888", "Crimen y castigo", "Fiódor Dostoievski", "Alianza", 14.25,
				"Espiral", "Reedicion", 9));
		libreria.guardarLibro(new Libro("9999999999999", "El nombre del viento", "Patrick Rothfuss", "Plaza and Janés",
				20.00, "Cartone", "Novedad", 6));
		libreria.guardarLibro(new Libro("1010101010101", "Los pilares de la Tierra", "Ken Follett", "DeBolsillo", 22.75,
				"Rustica", "Reedicion", 8));
		libreria.rellenarTabla(tablaLibros);
	}

	// Muestra el libro en un cuadro de dialogo
	private void mostrarLibro(Libro libro) {
		String mensaje = String.format(
				"Información del libro:\n\n" + "ISBN: %s\n" + "Título: %s\n" + "Editorial: %s\n" + "Autor: %s\n"
						+ "Precio: %.2f €\n" + "Formato: %s\n" + "Estado: %s\n" + "Cantidad: %d",
				libro.getISBN(), libro.getTitulo(), libro.getEditorial(), libro.getAutor(), libro.getPrecio(),
				libro.getFormato(), libro.getEstado(), libro.getCantidad());

		JOptionPane.showMessageDialog(null, mensaje, "Detalles del Libro", JOptionPane.INFORMATION_MESSAGE);
	}

	// Devuelve el boton seleccionado
	private String getSelectedRadio(ButtonGroup group) {
		Enumeration<AbstractButton> elements = group.getElements();
		for (int i = 0; i < group.getButtonCount(); i++) {
			AbstractButton actual = elements.nextElement();
			if (actual.isSelected()) {
				return actual.getText();
			}
		}
		return "";
	}

	// Limpia los cuadros de texto y sus bordes
	private void limpiarCampos(JTextField... campos) {
		textISBN.setText("");
		textAutor.setText("");
		textTitulo.setText("");
		textEditorial.setText("");
		textPrecio.setText("");
		textCantidad.setText("");
		grupoFormato.clearSelection();
		grupoEstado.clearSelection();
		
		Border bordeOriginal = UIManager.getBorder("TextField.border");

	    for (JTextField campo : campos) {
	        campo.setBorder(bordeOriginal);
	    }
	}
}
