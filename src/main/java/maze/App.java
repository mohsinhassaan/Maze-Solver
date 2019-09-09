package maze;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.io.ExportException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws IOException, ExportException {
        if (args.length < 1) {
            System.out.println("Insufficient arguments");
            return;
        } else if (args.length > 1) {
            System.out.println("Too many argmants");
            return;
        }

        File infile = new File(args[0]);
        var image = ImageIO.read(infile);
        long t1, t2;

        t1 = System.nanoTime();

        var G = GraphLoader.load(image);

        t2 = System.nanoTime();

        System.out.println(String.format("Graph loaded in %dms", (t2 - t1) / 1000000));

        var V = G.vertexSet();

        Vertex source = null, sink = null;

        for (var v : V) {
            if (v.getY() == 0) {
                source = v;
            } else if (v.getY() == image.getHeight() - 1) {
                sink = v;
            }
        }

        t1 = System.nanoTime();

        var sp = DijkstraShortestPath.findPathBetween(G, source, sink);

        t2 = System.nanoTime();

        System.out.println(String.format("Shortest path calculated in %dms", (t2 - t1) / 1000000));

        t1 = System.nanoTime();

        int n = GraphWriter.writePath(sp, G, new File("solved.bmp"), infile);

        t2 = System.nanoTime();

        System.out.println(String.format("Solution written in %dms", (t2 - t1) / 1000000));

        System.out.println("Length of path: " + n);
    }
}
