package maze;

/**
 * Vertex
 */
public class Vertex {
    private final Integer x;
    private final Integer y;

    Vertex(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    public Integer getX() {
        return x;
    }

    /**
     * @return the y
     */
    public Integer getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(this.getClass())) {
            Vertex other = (Vertex) obj;
            return this.x.equals(other.x) && this.y.equals(other.y);
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "x: " + x + ", y: " + y;
    }
}