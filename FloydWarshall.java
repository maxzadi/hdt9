import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FloydWarshall {

    private int[][] distances;
    private String[][] paths;
    private Graph graph;
    private final int INF;

    public FloydWarshall(Graph graph) {

        this.graph = graph;
        this.INF = graph.getINF();

        int[][] matrix = graph.getMatrix();
        int n = graph.getCities().size();

        distances = new int[n][n];
        paths = new String[n][n];

        // Copiar matriz original
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                distances[i][j] = matrix[i][j];

                if (i != j && matrix[i][j] != INF) {
                    paths[i][j] = getCityByIndex(j);
                } else {
                    paths[i][j] = null;
                }

            }

        }
    }

    /**
     * Algoritmo de Floyd-Warshall
     */
    public void floyd() {

        int n = distances.length;

        for (int k = 0; k < n; k++) {

            for (int i = 0; i < n; i++) {

                for (int j = 0; j < n; j++) {

                    if (distances[i][k] != INF &&
                        distances[k][j] != INF &&
                        distances[i][k] + distances[k][j] < distances[i][j]) {

                        distances[i][j] =
                                distances[i][k] + distances[k][j];

                        paths[i][j] = getCityByIndex(k);
                    }

                }

            }

        }

    }

    /**
     * Obtiene la distancia mínima entre dos ciudades
     */
    public int getDistance(String origin, String destination) {

        Map<String, Integer> cities = graph.getCities();

        int i = cities.get(origin);
        int j = cities.get(destination);

        return distances[i][j];
    }

    /**
     * Obtiene la ruta más corta
     */
    public String shortestPath(String origin, String destination) {

        Map<String, Integer> cities = graph.getCities();

        int i = cities.get(origin);
        int j = cities.get(destination);

        if (distances[i][j] == INF) {
            return "No existe ruta.";
        }

        List<String> route = new ArrayList<>();

        route.add(origin);

        buildPath(i, j, route);

        return String.join(" -> ", route);
    }

    /**
     * Construye recursivamente la ruta
     */
    private void buildPath(int i, int j, List<String> route) {

        String intermediate = paths[i][j];

        if (intermediate == null) {

            route.add(getCityByIndex(j));
            return;
        }

        int k = graph.getCities().get(intermediate);

        buildPath(i, k, route);

        route.remove(route.size() - 1);

        buildPath(k, j, route);

    }

    /**
     * Calcula el centro del grafo
     */
    public String graphCenter() {

        int n = distances.length;

        int minEccentricity = INF;
        int centerIndex = -1;

        for (int i = 0; i < n; i++) {

            int eccentricity = 0;

            for (int j = 0; j < n; j++) {

                if (distances[i][j] > eccentricity &&
                    distances[i][j] != INF) {

                    eccentricity = distances[i][j];
                }

            }

            if (eccentricity < minEccentricity) {

                minEccentricity = eccentricity;
                centerIndex = i;
            }

        }

        return getCityByIndex(centerIndex);
    }

    /**
     * Muestra matriz de distancias
     */
    public void printDistances() {

        System.out.println("\nMATRIZ DE DISTANCIAS:");

        for (String city : graph.getCities().keySet()) {
            System.out.printf("%15s", city);
        }

        System.out.println();

        for (String rowCity : graph.getCities().keySet()) {

            int i = graph.getCities().get(rowCity);

            System.out.printf("%15s", rowCity);

            for (String colCity : graph.getCities().keySet()) {

                int j = graph.getCities().get(colCity);

                if (distances[i][j] == INF) {
                    System.out.printf("%15s", "INF");
                } else {
                    System.out.printf("%15d", distances[i][j]);
                }

            }

            System.out.println();
        }
    }

    /**
     * Obtiene ciudad por índice
     */
    private String getCityByIndex(int index) {

        for (Map.Entry<String, Integer> entry :
                graph.getCities().entrySet()) {

            if (entry.getValue() == index) {
                return entry.getKey();
            }

        }

        return null;
    }

}