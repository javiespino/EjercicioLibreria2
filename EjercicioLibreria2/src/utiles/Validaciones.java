package utiles;

import java.util.List;
import java.util.regex.Pattern;

import modelo.Libreria;
import modelo.Libro;

public class Validaciones {
	public static boolean validarLetras(String nombre) {
		if (nombre == null || nombre.isEmpty())
			return false;
		return Pattern.matches("[\\p{L}\\s.,\\-()´'\"¿?!]+", nombre.trim());
	}

	public static boolean validarISBN(String isbn, Libreria libreria, int indiceEditar) {
		if (isbn == null || isbn.isEmpty())
			return false;

		if (!isbn.matches("\\d{13}"))
			return false;

		List<Libro> lista = libreria.getListaLibros();
		for (int i = 0; i < lista.size(); i++) {
			if (i == indiceEditar)
				continue;
			if (lista.get(i).getISBN().equals(isbn)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isNumber(String texto) {
		if (texto == null || texto.isEmpty())
			return false;

		if (!Pattern.matches("\\d+", texto))
			return false;

		try {
			int numero = Integer.parseInt(texto);
			return numero >= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isNumberFloat(String valor) {
		if (valor == null || valor.isEmpty())
			return false;
		return valor.matches("^\\d+(\\.\\d{1,2})?$");
	}

	public static String validateFields(String isbn, String autor, String titulo, String editorial, String precio,
			String formato, String estado, String cantidad, Libreria libreria, int indiceEditar) {

		if (!validarISBN(isbn, libreria, indiceEditar))
			return "El ISBN no es válido o ya existe.";
		if (!validarLetras(autor))
			return "El campo Autor solo puede contener letras.";
		if (titulo == null || titulo.isEmpty())
			return "El campo Título no puede estar vacío.";
		if (editorial == null || editorial.isEmpty())
			return "El campo Editorial no puede estar vacío.";
		if (!isNumberFloat(precio))
			return "El campo Precio debe ser un número válido (por ejemplo 12.50).";
		if (formato == null || formato.isEmpty())
			return "Debe seleccionar un formato (Cartoné, Rústica, Grapada o Espiral).";
		if (estado == null || estado.isEmpty())
			return "Debe seleccionar un estado (Reedición o Novedad).";
		if (!isNumber(cantidad))
			return "La cantidad debe ser un número entero válido.";

		return null;
	}
}
