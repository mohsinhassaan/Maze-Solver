package maze;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * GraphLoader
 */
public class GraphLoader {

    private static final int UP = 0;
    private static final int LEFT = 1;
    private static final int DOWN = 2;
    private static final int RIGHT = 3;

    public static DefaultUndirectedWeightedGraph<Vertex, DefaultWeightedEdge> load(BufferedImage image)
            throws IOException {
        var G = new DefaultUndirectedWeightedGraph<Vertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        boolean[] neighbors;

        for (int y = 0; y < image.getWidth(); y++) {
            for (int x = 0; x < image.getHeight(); x++) {
                if (isPath(image, x, y)) {
                    Vertex v = new Vertex(x, y);
                    if (y == 0) {
                        G.addVertex(v);
                    } else {
                        neighbors = getNeighbors(image, x, y);

                        if (neighbors[UP] && (neighbors[LEFT] || neighbors[RIGHT])) {
                            G.addVertex(v);

                            int weight = 0, x2 = x, y2 = y;

                            while (!G.containsVertex(new Vertex(x2, --y2))) {
                                weight++;
                            }

                            var e = G.addEdge(new Vertex(x2, y2), v);
                            G.setEdgeWeight(e, weight);

                            if (neighbors[LEFT]) {
                                weight = 0;
                                x2 = x;
                                y2 = y;

                                while (!G.containsVertex(new Vertex(--x2, y2))) {
                                    weight++;
                                }

                                e = G.addEdge(new Vertex(x2, y2), v);
                                G.setEdgeWeight(e, weight);
                            }
                        } else if (neighbors[DOWN] && (neighbors[LEFT] || neighbors[RIGHT])) {
                            G.addVertex(v);
                            if (neighbors[LEFT]) {
                                int weight = 0, x2 = x, y2 = y;

                                while (!G.containsVertex(new Vertex(--x2, y2))) {
                                    weight++;
                                }

                                var e = G.addEdge(new Vertex(x2, y2), v);
                                G.setEdgeWeight(e, weight);
                            }
                        } else if (neighbors[LEFT] && !neighbors[RIGHT] && !neighbors[UP] && !neighbors[DOWN]) {
                            G.addVertex(v);

                            int weight = 0, x2 = x, y2 = y;

                            while (!G.containsVertex(new Vertex(--x2, y2))) {
                                weight++;
                            }

                            var e = G.addEdge(new Vertex(x2, y2), v);
                            G.setEdgeWeight(e, weight);
                        } else if (neighbors[UP] && !neighbors[RIGHT] && !neighbors[LEFT] && !neighbors[DOWN]) {
                            G.addVertex(v);

                            int weight = 0, x2 = x, y2 = y;

                            while (!G.containsVertex(new Vertex(x2, --y2))) {
                                weight++;
                            }

                            var e = G.addEdge(new Vertex(x2, y2), v);
                            G.setEdgeWeight(e, weight);
                        } else if (neighbors[RIGHT] && !neighbors[LEFT] && !neighbors[UP] && !neighbors[DOWN]) {
                            G.addVertex(v);
                        } else if (neighbors[DOWN] && !neighbors[RIGHT] && !neighbors[UP] && !neighbors[LEFT]) {
                            G.addVertex(v);
                        }
                    }
                }
            }
        }
        return G;
    }

    private static boolean isPath(BufferedImage image, int x, int y) {
        return image.getRGB(x, y) == Color.white.getRGB();
    }

    private static boolean[] getNeighbors(BufferedImage image, int x, int y) {
        boolean[] neighbors = new boolean[4];
        neighbors[UP] = y == 0 ? false : isPath(image, x, y - 1);
        neighbors[LEFT] = isPath(image, x - 1, y);
        neighbors[DOWN] = y == image.getHeight() - 1 ? false : isPath(image, x, y + 1);
        neighbors[RIGHT] = isPath(image, x + 1, y);

        return neighbors;
    }
}
