import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    private Map<String, Integer> cities;
    private int[][] adjacencyMatrix;
    private final int INF = 999999;
    private int size;

    public Graph(int maxCities) {
        cities = new HashMap<>();
        adjacencyMatrix = new int[maxCities][maxCities];
        size = 0;

        // Inicializar matriz
        for (int i = 0; i < maxCities; i++) {
            for (int j = 0; j < maxCities; j++) {

                if (i == j) {
                    adjacencyMatrix[i][j] = 0;
                } else {
                    adjacencyMatrix[i][j] = INF;
                }

            }
        }
    }

    /**
     * Agrega una ciudad al grafo
     */
    public void addCity(String city) {

        if (!cities.containsKey(city)) {
            cities.put(city, size);
            size++;
        }

    }

    /**
     * Agrega una conexión dirigida entre ciudades
     */
    public void addEdge(String origin, String destination, int weight) {

        addCity(origin);
        addCity(destination);

        int i = cities.get(origin);
        int j = cities.get(destination);

        adjacencyMatrix[i][j] = weight;

    }

    /**
     * Elimina una conexión
     */
    public void removeEdge(String origin, String destination) {

        if (cities.containsKey(origin) && cities.containsKey(destination)) {

            int i = cities.get(origin);
            int j = cities.get(destination);

            adjacencyMatrix[i][j] = INF;
        }
    }

    /**
     * Obtiene la matriz de adyacencia
     */
    public int[][] getMatrix() {
        return adjacencyMatrix;
    }

    /**
     * Obtiene el mapa de ciudades
     */
    public Map<String, Integer> getCities() {
        return cities;
    }

    /**
     * Obtiene INF
     */
    public int getINF() {
        return INF;
    }

    /**
     * Muestra la matriz
     */
    public void printMatrix() {

        System.out.println("\nMATRIZ DE ADYACENCIA:");

        // Encabezados
        for (String city : cities.keySet()) {
            System.out.printf("%15s", city);
        }

        System.out.println();

        for (String rowCity : cities.keySet()) {

            int i = cities.get(rowCity);

            System.out.printf("%15s", rowCity);

            for (String colCity : cities.keySet()) {

                int j = cities.get(colCity);

                if (adjacencyMatrix[i][j] == INF) {
                    System.out.printf("%15s", "INF");
                } else {
                    System.out.printf("%15d", adjacencyMatrix[i][j]);
                }

            }

            System.out.println();
        }
    }
}