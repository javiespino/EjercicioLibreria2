package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

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

					libreria.agregarLibro(libro);
					libreria.mostrarLibros();
					libreria.rellenarTabla(tablaLibros);
					limpiar();
					JOptionPane.showMessageDialog(null, "Libro guardado con éxito");
				} else {
					JOptionPane.showMessageDialog(null, "Campos erroneos");
				}
			}
		});

		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
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
				JOptionPane.showMessageDialog(null, "Compra realizada con éxito.\nSe añadieron " + compras
						+ " unidades.\nStock actual: " + libro.getCantidad());
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
				JOptionPane.showMessageDialog(null, "Venta realizada con éxito.\nSe vendieron " + ventas
						+ " unidades.\nStock actual: " + libro.getCantidad());
				libreria.rellenarTabla(tablaLibros);
			}
		});

	}

	private void generarLibreria() {
		libreria.agregarLibro(new Libro("1111111111111", "Cien años de soledad", "Sudamericana",
				"Gabriel García Márquez", 19.99, "Cartone", "Reedicion", 10));
		libreria.agregarLibro(new Libro("2222222222222", "Don Quijote de la Mancha", "Espasa", "Miguel de Cervantes",
				24.50, "Grapada", "Novedad", 5));
		libreria.agregarLibro(new Libro("3333333333333", "La sombra del viento", "Planeta", "Carlos Ruiz Zafón", 17.95,
				"Rustica", "Reedicion", 8));
		libreria.agregarLibro(
				new Libro("4444444444444", "1984", "Debolsillo", "George Orwell", 12.99, "Espiral", "Novedad", 12));
		libreria.agregarLibro(new Libro("5555555555555", "El Principito", "Salamandra", "Antoine de Saint-Exupéry",
				9.50, "Cartone", "Reedicion", 20));

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
