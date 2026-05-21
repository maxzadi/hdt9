import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    private static Graph graph;
    private static FloydWarshall floyd;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Crear grafo
        graph = new Graph(50);

        // Leer archivo
        readFile("guategrafo.txt");

        // Mostrar matriz original
        graph.printMatrix();

        // Ejecutar Floyd
        floyd = new FloydWarshall(graph);
        floyd.floyd();

        // Mostrar matriz de distancias
        floyd.printDistances();

        // Menú
        menu();
    }

    /**
     * Lee archivo txt y construye el grafo
     */
    public static void readFile(String fileName) {

        try {

            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(" ");

                String city1 = data[0];
                String city2 = data[1];
                int distance = Integer.parseInt(data[2]);

                graph.addEdge(city1, city2, distance);
            }

            fileScanner.close();

            System.out.println("Archivo cargado correctamente.");

        } catch (FileNotFoundException e) {

            System.out.println("Error: archivo no encontrado.");
        }

    }

    /**
     * Menú principal
     */
    public static void menu() {

        int option = 0;

        while (option != 4) {

            System.out.println("\n===== MENÚ =====");
            System.out.println("1. Ruta más corta");
            System.out.println("2. Centro del grafo");
            System.out.println("3. Modificar grafo");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:
                    shortestRoute();
                    break;

                case 2:
                    showCenter();
                    break;

                case 3:
                    modifyGraph();
                    break;

                case 4:
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        }

    }

    /**
     * Consulta ruta más corta
     */
    public static void shortestRoute() {

        System.out.print("Ciudad origen: ");
        String origin = scanner.nextLine();

        System.out.print("Ciudad destino: ");
        String destination = scanner.nextLine();

        int distance = floyd.getDistance(origin, destination);

        if (distance == graph.getINF()) {

            System.out.println("No existe ruta.");

        } else {

            System.out.println("\nDistancia mínima: " + distance + " KM");

            String route =
                    floyd.shortestPath(origin, destination);

            System.out.println("Ruta: " + route);
        }

    }

    /**
     * Muestra centro del grafo
     */
    public static void showCenter() {

        String center = floyd.graphCenter();

        System.out.println("\nCentro del grafo: " + center);

    }

    /**
     * Modifica conexiones
     */
    public static void modifyGraph() {

        System.out.println("\n1. Eliminar conexión");
        System.out.println("2. Agregar conexión");
        System.out.print("Seleccione una opción: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {

            case 1:

                System.out.print("Ciudad origen: ");
                String origin = scanner.nextLine();

                System.out.print("Ciudad destino: ");
                String destination = scanner.nextLine();

                graph.removeEdge(origin, destination);

                System.out.println("Conexión eliminada.");

                break;

            case 2:

                System.out.print("Ciudad origen: ");
                String city1 = scanner.nextLine();

                System.out.print("Ciudad destino: ");
                String city2 = scanner.nextLine();

                System.out.print("Distancia KM: ");
                int distance = scanner.nextInt();

                graph.addEdge(city1, city2, distance);

                System.out.println("Conexión agregada.");

                break;

            default:
                System.out.println("Opción inválida.");
        }

        // Recalcular Floyd
        floyd = new FloydWarshall(graph);
        floyd.floyd();

        System.out.println("\nRutas recalculadas correctamente.");

        // Mostrar nueva matriz
        floyd.printDistances();

    }

}