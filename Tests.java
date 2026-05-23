import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tests {

    private Graph graph;

    @Before
    public void cargar() throws FileNotFoundException {

        graph = new Graph(30);

        File file = new File("guategrafo.txt");

        Scanner sc = new Scanner(file);

        while (sc.hasNext()) {

            String origin = sc.next();
            String destination = sc.next();
            int weight = sc.nextInt();

            graph.addEdge(origin, destination, weight);
        }

        sc.close();
    }

    
    //Verifica que las ciudades se cargaron

    @Test
    public void testCiudades() {

        assertTrue(graph.getCities().containsKey("Guatemala"));
        assertTrue(graph.getCities().containsKey("Mixco"));
        assertTrue(graph.getCities().containsKey("Peten"));
    }

    
    //Verifica una conexión existente
    
    @Test
    public void testArista() {

        int[][] matrix = graph.getMatrix();

        int g = graph.getCities().get("Guatemala");
        int m = graph.getCities().get("Mixco");

        assertEquals(15, matrix[g][m]);
    }

    
    //Verifica eliminación de arista
     
    @Test
    public void testEliminarArista() {

        graph.removeEdge("Guatemala", "Mixco");

        int[][] matrix = graph.getMatrix();

        int g = graph.getCities().get("Guatemala");
        int m = graph.getCities().get("Mixco");

        assertEquals(graph.getINF(), matrix[g][m]);
    }

    
    //Verifica ciudades no conectadas
    
    @Test
    public void testNoConectadas() {

        int[][] matrix = graph.getMatrix();

        int mixco = graph.getCities().get("Mixco");
        int coban = graph.getCities().get("Coban");

        assertEquals(graph.getINF(), matrix[mixco][coban]);
    }

    
    //Verifica diagonal en 0
     
    @Test
    public void testDiagonalCero() {

        int[][] matrix = graph.getMatrix();

        int g = graph.getCities().get("Guatemala");

        assertEquals(0, matrix[g][g]);
    }

    
    //Verifica cantidad de ciudades 
    @Test
    public void testTotalCiudades() {

        assertEquals(16, graph.getCities().size());
    }
}