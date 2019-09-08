package maze;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * GraphWriter
 */
public class GraphWriter {

    static void writePath(GraphPath<Vertex, DefaultWeightedEdge> path,
            DefaultUndirectedWeightedGraph<Vertex, DefaultWeightedEdge> G, File outfile, File infile)
            throws IOException {
        BufferedImage image = ImageIO.read(infile);

        int n = 0;

        for (var e : path.getEdgeList()) {
            Vertex v1 = G.getEdgeSource(e);
            Vertex v2 = G.getEdgeTarget(e);

            if (v1.getX().equals(v2.getX())) {
                int x = v1.getX();
                int y = v1.getY();

                do {
                    if (image.getRGB(x, y) == Color.white.getRGB()) {
                        image.setRGB(x, y, Color.red.getRGB());
                        n++;
                    }
                } while (y++ != v2.getY());
            } else if (v1.getY().equals(v2.getY())) {
                int x = v1.getX();
                int y = v1.getY();

                do {
                    if (image.getRGB(x, y) == Color.white.getRGB()) {
                        image.setRGB(x, y, Color.red.getRGB());
                        n++;
                    }
                } while (x++ != v2.getX());
            }
            ImageIO.write(image, "bmp", outfile);
        }

        System.out.println("Length of path: " + n);

    }
}