package utiles;

import java.util.regex.Pattern;

import modelo.Libreria;
import modelo.Libro;

public class Validaciones {
	public static boolean validarLetras(String nombre) {
		if (nombre == null || nombre.isEmpty())
			return false;
		return Pattern.matches("[\\p{L}\\s]+", nombre);
	}

	public static boolean validarISBN(String isbn, Libreria libreria) {
	    if (isbn == null || isbn.isEmpty())
	        return false;

	    if (!isbn.matches("\\d{13}"))
	        return false;

	    for (Libro libro : libreria.getListaLibros()) {
	        if (libro.getISBN().equals(isbn)) {
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

	public static boolean isNumberFloat(String texto) {
	    if (texto == null || texto.isEmpty())
	        return false;

	    try {
	        Float.parseFloat(texto.trim());
	        char charAt = texto.charAt(texto.length() - 1);
	        if (charAt == 'f' || charAt == 'd') {
	        	return false;
	        }
	    } catch (NumberFormatException e) {
	        return false;
	    }
	    return true;
	}


	public static boolean validateFields(String isbn, String autor, String titulo, String editorial, String precio,
			String formato, String estado, String cantidad, Libreria libreria) {

		if (!validarISBN(isbn, libreria))
			return false;
		if (!validarLetras(autor))
			return false;
		if (titulo == null || titulo.isEmpty())
			return false;
		if (editorial == null || editorial.isEmpty())
			return false;
		if (!isNumberFloat(precio))
			return false;
		if (formato == null || formato.isEmpty())
			return false;
		if (estado == null || estado.isEmpty())
			return false;
		if (!isNumber(cantidad))
			return false;

		return true;
	}
	
	public static boolean validateFieldsEdit(String isbn, String autor, String titulo, String editorial, String precio,
			String formato, String estado, String cantidad, Libreria libreria) {

		if (!validarISBNEdit(isbn, libreria))
			return false;
		if (!validarLetras(autor))
			return false;
		if (titulo == null || titulo.isEmpty())
			return false;
		if (editorial == null || editorial.isEmpty())
			return false;
		if (!isNumberFloat(precio))
			return false;
		if (formato == null || formato.isEmpty())
			return false;
		if (estado == null || estado.isEmpty())
			return false;
		if (!isNumber(cantidad))
			return false;

		return true;
	}
	
	public static boolean validarISBNEdit(String isbn, Libreria libreria) {
		if (isbn == null || isbn.isEmpty())
			return false;

		if (!(isbn.length() == 13 && !isNumber(isbn)))
			return false;

		return true;
	}
}
