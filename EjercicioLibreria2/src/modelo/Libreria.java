package modelo;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Libreria {
	private ArrayList<Libro> listaLibros;

	public Libreria() {
        listaLibros = new ArrayList<>();
	}

	public void guardarLibro(Libro libro) {
		listaLibros.add(libro);
	}

	public ArrayList<Libro> getListaLibros() {
		return listaLibros;
	}

	public void mostrarLibros() {
		for (Libro libro : listaLibros) {
			System.out.println(libro.toString());
		}
	}

	public void rellenarTabla(JTable tablaLibros) {
	    String nombresColumnas[] = { "ISBN", "TITULO", "AUTOR", "EDITORIAL", "PRECIO", "FORMATO", "ESTADO", "CANTIDAD" };
	    String[][] filasTabla = new String[listaLibros.size()][nombresColumnas.length];

	    for (int i = 0; i < listaLibros.size(); i++) {
	        Libro libro = listaLibros.get(i);
	        filasTabla[i][0] = libro.getISBN();
	        filasTabla[i][1] = libro.getTitulo();
	        filasTabla[i][2] = libro.getAutor();
	        filasTabla[i][3] = libro.getEditorial();
	        filasTabla[i][4] = String.valueOf(libro.getPrecio());
	        filasTabla[i][5] = libro.getFormato();
	        filasTabla[i][6] = libro.getEstado();
	        filasTabla[i][7] = String.valueOf(libro.getCantidad());
	    }

	    DefaultTableModel tablaCompleta = new DefaultTableModel(filasTabla, nombresColumnas) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    tablaLibros.setModel(tablaCompleta);
	}

	public int obtenerId(JTable tablaLibros) {
		for (int i = 0; i < this.listaLibros.size(); i++) {
			if (tablaLibros.getSelectedRow() == i) {
				return i;
			}
		}
		return -1;
	}

	public void borrarLibros(int indice) {
		listaLibros.remove(indice);
	}
	
	public Libro obtenerLibroDos(String ISBN) {
		for(int i = 0; i < this.listaLibros.size(); i++) {
			if (this.listaLibros.get(i).getISBN().equals(ISBN)) {
				return listaLibros.get(i);
			}
		}
		return null;
	}
	
	public void actualizarLibro(Libro libroActualizado) {
	    for (int i = 0; i < listaLibros.size(); i++) {
	        Libro l = listaLibros.get(i);
	        if (l.getISBN().equals(libroActualizado.getISBN())) {
	            listaLibros.set(i, libroActualizado);
	            return;
	        }
	    }
	}
}
