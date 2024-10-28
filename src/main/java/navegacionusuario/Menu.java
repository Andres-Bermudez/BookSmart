package navegacionusuario;

import servicio.MetodosBusqueda;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public void menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        String menu = """
                      \n:::::::::::::: Bienvenido a BookSmart ::::::::::::::
                            1. Buscar un libro por su titulo.
                            2. Top 10 libros mas descargados.
                            3. Ver estadisticas generales de la app.
                            4. Ver todos los libros.
                            0. Salir.
                      """;
        int eleccionUsuario = - 1;
        int totalOpciones = 4;

        while (eleccionUsuario < 0 || eleccionUsuario > totalOpciones) {
            System.out.print(menu + "\nTu eleccion: ");

            // Para tratar la excepcion en caso de que el usuario ingrese
            // un valor diferente a un numero entero.
            try {
                eleccionUsuario = sc.nextInt();

                if (eleccionUsuario > totalOpciones) {
                    System.out.println("\nEsta opcion no esta disponible.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nEl caracter que ingresaste no es valido.");
                sc.nextLine(); // Para evitar un bucle infinito, se pasa al siguiente elemento del Scanner
            }
        }

        // Redireccion a la opcion que elija el usuario
        switch (eleccionUsuario) {
            case 0:
                System.exit(0);
                break;
            case 1:
                MetodosBusqueda.buscarLibroPorTitulo();
                break;
            case 2:
                MetodosBusqueda.top10LibrosMasDescargados();
                break;
            case 3:
                MetodosBusqueda.verEstadisticas();
                break;
            case 4:
                MetodosBusqueda.verTodosLosLibros();
                break;
            default:
                System.out.println("\nRecuerda elegir solo entre las opciones disponibles.");
                break;
        }
    }
}
