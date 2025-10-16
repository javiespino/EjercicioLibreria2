package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
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

	public ParaUI() {
		generarLibreria();

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Validaciones.validateFields(textISBN.getText(), textAutor.getText(), textTitulo.getText(),
						textEditorial.getText(), textPrecio.getText(), getSelectedRadio(grupoFormato),
						getSelectedRadio(grupoEstado), textCantidad.getText(), libreria)) {

					libro = new Libro(textISBN.getText(), textAutor.getText(), textTitulo.getText(),
							textEditorial.getText(), Float.parseFloat(textPrecio.getText()),
							getSelectedRadio(grupoFormato), getSelectedRadio(grupoEstado),
							Integer.parseInt(textCantidad.getText()));

					libreria.guardarLibro(libro);
					libreria.mostrarLibros();
					libreria.rellenarTabla(tablaLibros);
					limpiar();
					JOptionPane.showMessageDialog(null, "Libro guardado con éxito");
				} else {
					JOptionPane.showMessageDialog(null, "Campos erroneos");
				}
			}
		});

		btnEditar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (Validaciones.validateFieldsEdit(textISBN.getText(), textTitulo.getText(), textAutor.getText(),
		                textEditorial.getText(), textPrecio.getText(), getSelectedRadio(grupoFormato),
		                getSelectedRadio(grupoEstado), textCantidad.getText(), libreria)) {

		            Libro libroExistente = libreria.obtenerLibroDos(textISBN.getText());

		            if (libroExistente != null) {
		                libroExistente.setAutor(textAutor.getText());
		                libroExistente.setTitulo(textTitulo.getText());
		                libroExistente.setEditorial(textEditorial.getText());
		                libroExistente.setPrecio(Float.parseFloat(textPrecio.getText()));
		                libroExistente.setFormato(getSelectedRadio(grupoFormato));
		                libroExistente.setEstado(getSelectedRadio(grupoEstado));
		                libroExistente.setCantidad(Integer.parseInt(textCantidad.getText()));

		                libreria.mostrarLibros();
		                libreria.rellenarTabla(tablaLibros);
		                limpiar();
		                JOptionPane.showMessageDialog(null, "Libro editado con éxito");
		            } else {
		                JOptionPane.showMessageDialog(null, "No se encontró el libro con el ISBN ingresado");
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Campos erroneos");
		        }
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
				String ISBNsel = JOptionPane.showInputDialog("Introduce ISBN");
				mostrarCampos(libreria.obtenerLibroDos(ISBNsel));
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
		                "Compra realizada con éxito.\n" +
		                        "Se añadieron " + compras + " unidades.\n" +
		                        "Total: €" + String.format("%.2f", total) + "\n" +
		                        "Stock actual: " + libro.getCantidad());

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
		                "Venta realizada con éxito.\n" +
		                        "Se vendieron " + ventas + " unidades.\n" +
		                        "Total sin IVA: €" + String.format("%.2f", totalSinIVA) + "\n" +
		                        "Total con IVA (21%): €" + String.format("%.2f", totalConIVA) + "\n" +
		                        "Stock actual: " + libro.getCantidad());

		        libreria.rellenarTabla(tablaLibros);

			}
		});

		// Limitar que no se puedan poner mas de 13 caracteres
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
		
		// ISBN
		textISBN.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
		    public void insertUpdate(javax.swing.event.DocumentEvent e) { validar(); }
		    public void removeUpdate(javax.swing.event.DocumentEvent e) { validar(); }
		    public void changedUpdate(javax.swing.event.DocumentEvent e) { validar(); }

		    private void validar() {
		        if (Validaciones.validarISBN(textISBN.getText(), libreria)) {
		            textISBN.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		        } else {
		            textISBN.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		        }
		    }
		});

		// Autor
		textAutor.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
		    public void insertUpdate(javax.swing.event.DocumentEvent e) { validar(); }
		    public void removeUpdate(javax.swing.event.DocumentEvent e) { validar(); }
		    public void changedUpdate(javax.swing.event.DocumentEvent e) { validar(); }

		    private void validar() {
		        if (Validaciones.validarLetras(textAutor.getText())) {
		            textAutor.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		        } else {
		            textAutor.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		        }
		    }
		});

		// Título
		textTitulo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
		    public void insertUpdate(javax.swing.event.DocumentEvent e) { validar(); }
		    public void removeUpdate(javax.swing.event.DocumentEvent e) { validar(); }
		    public void changedUpdate(javax.swing.event.DocumentEvent e) { validar(); }

		    private void validar() {
		        if (textTitulo.getText() != null && !textTitulo.getText().isEmpty()) {
		            textTitulo.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		        } else {
		            textTitulo.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		        }
		    }
		});

		// Editorial
		textEditorial.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
		    public void insertUpdate(javax.swing.event.DocumentEvent e) { validar(); }
		    public void removeUpdate(javax.swing.event.DocumentEvent e) { validar(); }
		    public void changedUpdate(javax.swing.event.DocumentEvent e) { validar(); }

		    private void validar() {
		        if (textEditorial.getText() != null && !textEditorial.getText().isEmpty()) {
		            textEditorial.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		        } else {
		            textEditorial.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		        }
		    }
		});

		// Precio
		textPrecio.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
		    public void insertUpdate(javax.swing.event.DocumentEvent e) { validar(); }
		    public void removeUpdate(javax.swing.event.DocumentEvent e) { validar(); }
		    public void changedUpdate(javax.swing.event.DocumentEvent e) { validar(); }

		    private void validar() {
		        if (Validaciones.isNumberFloat(textPrecio.getText())) {
		            textPrecio.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		        } else {
		            textPrecio.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		        }
		    }
		});

		// Cantidad
		textCantidad.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
		    public void insertUpdate(javax.swing.event.DocumentEvent e) { validar(); }
		    public void removeUpdate(javax.swing.event.DocumentEvent e) { validar(); }
		    public void changedUpdate(javax.swing.event.DocumentEvent e) { validar(); }

		    private void validar() {
		        if (Validaciones.isNumber(textCantidad.getText())) {
		            textCantidad.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		        } else {
		            textCantidad.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		        }
		    }
		});

		
	}

	private void generarLibreria() {
		libreria.guardarLibro(new Libro("1111111111111", "Cien años de soledad", "Gabriel García Márquez",
				"Sudamericana", 19.99, "Cartone", "Reedicion", 10));
		libreria.guardarLibro(new Libro("2222222222222", "Don Quijote de la Mancha", "Miguel de Cervantes", "Espasa",
				24.50, "Grapada", "Novedad", 5));
		libreria.guardarLibro(new Libro("3333333333333", "La sombra del viento", "Carlos Ruiz Zafón", "Planeta", 17.95,
				"Rustica", "Reedicion", 8));
		libreria.guardarLibro(
				new Libro("4444444444444", "1984", "George Orwell", "Debolsillo", 12.99, "Espiral", "Novedad", 12));
		libreria.guardarLibro(new Libro("5555555555555", "El Principito", "Antoine de Saint-Exupéry", "Salamandra",
				9.50, "Cartone", "Reedicion", 20));
		libreria.guardarLibro(new Libro("6666666666666", "El Hobbit", "J.R.R. Tolkien", "Minotauro", 15.99, "Rustica",
				"Reedicion", 7));
		libreria.guardarLibro(new Libro("7777777777777", "Harry Potter y la piedra filosofal", "J.K. Rowling",
				"Salamandra", 18.50, "Cartone", "Novedad", 12));
		libreria.guardarLibro(new Libro("8888888888888", "Crimen y castigo", "Fiódor Dostoievski", "Alianza", 14.25,
				"Espiral", "Reedicion", 9));
		libreria.guardarLibro(new Libro("9999999999999", "El nombre del viento", "Patrick Rothfuss", "Plaza & Janés",
				20.00, "Cartone", "Novedad", 6));
		libreria.guardarLibro(new Libro("1010101010101", "Los pilares de la Tierra", "Ken Follett", "DeBolsillo", 22.75,
				"Rustica", "Reedicion", 8));
		libreria.rellenarTabla(tablaLibros);
	}

	private void mostrarCampos(Libro libro) {
		textISBN.setText(libro.getISBN());
		textTitulo.setText(libro.getTitulo());
		textEditorial.setText(libro.getEditorial());
		textAutor.setText(libro.getAutor());
		textPrecio.setText(String.valueOf(libro.getPrecio()));

		switch (libro.getFormato()) {
		case "Cartone":
			rdbtnCartone.setSelected(true);
			break;
		case "Espiral":
			rdbtnEspiral.setSelected(true);
			break;
		case "Grapada":
			rdbtnGrapada.setSelected(true);
			break;
		case "Rustica":
			rdbtnRustica.setSelected(true);
			break;
		}

		switch (libro.getEstado()) {
		case "Novedad":
			rdbtnNovedad.setSelected(true);
			break;
		case "Reedicion":
			rdbtnReedicion.setSelected(true);
			break;
		}

		textCantidad.setText(String.valueOf(libro.getCantidad()));
	}

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

	private void limpiar() {
		textISBN.setText("");
		textAutor.setText("");
		textTitulo.setText("");
		textEditorial.setText("");
		textPrecio.setText("");
		textCantidad.setText("");
		grupoFormato.clearSelection();
		grupoEstado.clearSelection();
	}
}
