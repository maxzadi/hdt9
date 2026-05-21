import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FloydWarshall {

    private int[][] dist;
    private int[][] next;
    private Graph graph;
    private final int INF;

    public FloydWarshall(Graph graph) {

        this.graph = graph;
        this.INF = graph.getINF();

        int[][] matrix = graph.getMatrix();
        int n = graph.getCities().size();

        dist = new int[n][n];
        next = new int[n][n];

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                dist[i][j] = matrix[i][j];

                if (matrix[i][j] != INF && i != j) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }

            }

        }

    }

    /**
     * Algoritmo de Floyd-Warshall
     */
    public void floyd() {

        int n = dist.length;

        for (int k = 0; k < n; k++) {

            for (int i = 0; i < n; i++) {

                for (int j = 0; j < n; j++) {

                    if (dist[i][k] == INF || dist[k][j] == INF) {
                        continue;
                    }

                    if (dist[i][k] + dist[k][j] < dist[i][j]) {

                        dist[i][j] = dist[i][k] + dist[k][j];

                        next[i][j] = next[i][k];
                    }

                }

            }

        }

    }

    /**
     * Obtiene distancia mínima
     */
    public int getDistance(String origin, String destination) {

        int i = graph.getCities().get(origin);
        int j = graph.getCities().get(destination);

        return dist[i][j];
    }

    /**
     * Reconstruye la ruta más corta
     */
    public String shortestPath(String origin, String destination) {

        int i = graph.getCities().get(origin);
        int j = graph.getCities().get(destination);

        if (next[i][j] == -1) {
            return "No existe ruta.";
        }

        List<String> path = new ArrayList<>();

        path.add(origin);

        while (i != j) {

            i = next[i][j];

            path.add(getCityByIndex(i));
        }

        return String.join(" -> ", path);
    }

    /**
     * Calcula centro del grafo
     */
    public String graphCenter() {

        int n = dist.length;

        int minEccentricity = INF;
        int center = -1;

        for (int i = 0; i < n; i++) {

            int eccentricity = 0;

            for (int j = 0; j < n; j++) {

                if (dist[i][j] != INF &&
                    dist[i][j] > eccentricity) {

                    eccentricity = dist[i][j];
                }

            }

            if (eccentricity < minEccentricity) {

                minEccentricity = eccentricity;
                center = i;
            }

        }

        return getCityByIndex(center);
    }

    /**
     * Imprime matriz de distancias
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

                if (dist[i][j] == INF) {
                    System.out.printf("%15s", "INF");
                } else {
                    System.out.printf("%15d", dist[i][j]);
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