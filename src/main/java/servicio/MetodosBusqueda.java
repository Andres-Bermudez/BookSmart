package servicio;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import conexionapi.ConexionAPI;
import modelos.Libro;
import modelos.Resultado;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MetodosBusqueda {
    private static final ConexionAPI solicitud = new ConexionAPI();

    public static void buscarLibroPorTitulo() {
        /*
        Esta opcion permite la busqueda de un libro por su titulo.
        La API nos retorna la primera coincidencia con las palabras que ingresamos
        y no distingue entre mayusculas, minusculas ni el idioma.
        */
        Scanner sc = new Scanner(System.in);

        System.out.println("\nBuscando un libro por su titulo.....");
        System.out.print("Ingresa el titulo del libro: ");

        // Formateo del titulo para que sea valido en la solicitud a la API
        String tituloLibro = sc.nextLine().trim().replace(" ", "%20");

        // Endpoint al que hacemos la solicitud
        String url = "https://gutendex.com/books?search=" + tituloLibro;

        System.out.println("\nBuscando tu libro...");
        String json = solicitud.conexionAPI(url); // Solicitud a la API

        //System.out.println("Resultado: " + json);

        // Mostrar al usuario el libro encontrado , formateando la respuesta JSON
        // En un formato legible.
        Gson gson = new Gson();
        Resultado resultado = null;

        try {
            resultado = gson.fromJson(json, Resultado.class);

        // Tratamiento de errores de mapeo de datos
        } catch (JsonParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (resultado != null) {
            // Estadisticas de la cantidad de resultados encontrados
            System.out.println("\nSe han encontrado (" + resultado.resultado().size() + ") resultados " +
                    "que coinciden con tu busqueda:");

            // Imprimir los resultados encontrados
            resultado.resultado().forEach(libro -> {
                System.out.println("\n--------------------------------------------------------------------" +
                        "-------------------------------------------------");
                System.out.println("TITULO: " + libro.titulo());

                System.out.println("\nAUTORES: ");
                libro.autores().forEach(System.out::println);

                System.out.println("\nDOCUMENTACION:");
                libro.formatos().forEach((clave, valor) -> {
                    System.out.println("    Dato: " + clave);
                    System.out.println("    Informacion: " + valor + "\n");
                });
                System.out.println("Total de descargas: " + libro.totalDescargas());
            });

        } else {
            System.out.println("\nNo ha sido posible mostrar los resultados encontrados.");
        }
    }

    public static void top10LibrosMasDescargados() {
        /*
        Esta opcion realiza la busqueda de todos los libros disponibles en la API
        y posteriormente los filtra y retorna unicamente los 10 libros
        con mayores descargas.
        */
        System.out.println("\nBuscando top 10 de los libros mas descargados.....");

        // Solicitud para consultar los libros disponibles en la API
        ConexionAPI solicitud = new ConexionAPI();
        String url = "https://gutendex.com/books";
        String json = solicitud.conexionAPI(url);

        // Mostrar al usuario el libro encontrado , formateando la respuesta JSON
        // En un formato legible.
        Gson gson = new Gson();
        Resultado resultado = null;

        try {
            resultado = gson.fromJson(json, Resultado.class);

            // Tratamiento de errores de mapeo de datos
        } catch (JsonParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (resultado != null) {
            System.out.println("\nTOP 10 DE LOS LIBROS MAS DESCARGADOS:");

            // Imprimir los resultados encontrados
            resultado.resultado().stream()
                    .sorted(Comparator.comparing(Libro::totalDescargas).reversed())
                    .limit(10)
                    .forEach(libro -> {
                System.out.println("\n--------------------------------------------------------------------" +
                        "-------------------------------------------------");
                System.out.println("TITULO: " + libro.titulo());

                System.out.println("\nTOTAL DE DESCARGAS: " + libro.totalDescargas());

                System.out.println("\nAUTORES: ");
                libro.autores().forEach(System.out::println);

                System.out.println("\nDOCUMENTACION:");
                libro.formatos().forEach((clave, valor) -> {
                    System.out.println("    Dato: " + clave);
                    System.out.println("    Informacion: " + valor + "\n");
                });
            });

        } else {
            System.out.println("\nNo ha sido posible mostrar los resultados encontrados.");
        }
    }

    public static void verEstadisticas() {
        // Este metodo nos muestra las estadisticas generales de la app
        String url = "https://gutendex.com/books"; // Endpoint al que hacemos la solicitud

        System.out.println("\nCalculando estadisticas de la aplicacion...");
        String json = solicitud.conexionAPI(url); // Ejecutando solicitud a la API

        // Mostrar al usuario el libro encontrado , formateando la respuesta JSON
        // En un formato legible.
        Gson gson = new Gson();
        Resultado resultado = null;

        try {
            resultado = gson.fromJson(json, Resultado.class);

            // Tratamiento de errores de mapeo de datos
        } catch (JsonParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (resultado != null) {
            // Calcular los datos de las estadisticas de la app
            DoubleSummaryStatistics statistics = resultado.resultado().stream()
                    .filter(libro -> libro.totalDescargas() > 0)
                    .collect(Collectors.summarizingDouble(Libro::totalDescargas));

            System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
                                + "+++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("- Total de descargas realizadas por los usuarios: " + (int) statistics.getSum() +
                    "\n- El libro mas descargado tiene un total de: " + (int) statistics.getMax() + " descargas." +
                    "\n- Total de registros consultados: " + statistics.getCount());
        } else {
            System.out.println("\nNo ha sido posible mostrar los resultados encontrados.");
        }
    }

    public static void verTodosLosLibros() {
        //Esta opcion nos muestra todos los libros disponibles.
        String url = "https://gutendex.com/books"; // Endpoint al que hacemos la solicitud

        System.out.println("\nBuscando libros...");
        String json = solicitud.conexionAPI(url); // Ejecutando solicitud a la API

        // Mostrar al usuario el libro encontrado , formateando la respuesta JSON
        // En un formato legible.
        Gson gson = new Gson();
        Resultado resultado = null;

        try {
            resultado = gson.fromJson(json, Resultado.class);

            // Tratamiento de errores de mapeo de datos
        } catch (JsonParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        if (resultado != null) {
            // Estadisticas de la cantidad de resultados encontrados
            System.out.println("\nSe han encontrado (" + resultado.resultado().size() + ") resultados.");

            // Imprimir los resultados encontrados
            resultado.resultado().forEach(libro -> {
                System.out.println("\n--------------------------------------------------------------------" +
                        "-------------------------------------------------");
                System.out.println("TITULO: " + libro.titulo());

                System.out.println("\nAUTORES: ");
                libro.autores().forEach(System.out::println);

                System.out.println("\nDOCUMENTACION:");
                libro.formatos().forEach((clave, valor) -> {
                    System.out.println("    Dato: " + clave);
                    System.out.println("    Informacion: " + valor + "\n");
                });
                System.out.println("TOTAL DE DESCARGAS: " + libro.totalDescargas());
            });
        } else {
            System.out.println("\nNo ha sido posible mostrar los resultados encontrados.");
        }
    }
}
