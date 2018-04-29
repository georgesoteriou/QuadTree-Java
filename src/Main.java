import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
  private static final int NUMOFPOINTS = 1000000;
  private static final Coord MAXPOINTPOSITION = new Coord(10000,10000);
  static class range{
    static Coord topLeft = new Coord(32,42);
    static Double width = 16.0;
    static Double height = 14.0;
  }

  public static void main(String[] args) {
    Random random = new Random();
    Qtree<Character> qtree = new Qtree<>(0, 0, MAXPOINTPOSITION.x, 2);
    Set<Point> points = new HashSet<>();
    for (int i = 0; i < NUMOFPOINTS; i++) {
      Point<Character> point = new Point<>(random.nextDouble() * MAXPOINTPOSITION.x,
              random.nextDouble() * MAXPOINTPOSITION.y, (char) (random.nextInt('z' - 'a') + 'a'));
      qtree.add(point);
      points.add(point);
    }
    long begin;
    //System.out.println(qtree.toString());

    System.out.println("Number of points added: "+ NUMOFPOINTS +", Between (0,0) and "+MAXPOINTPOSITION);
    System.out.println("Range to Query: Top Left: " + range.topLeft +", Width: "+range.width+", Height: "+range.height);
    begin = System.currentTimeMillis();
    System.out.println("Number of values in range (using Qtree): "+
            qtree.getPoints(range.topLeft, range.width,range.height).size());
    System.out.println("It tool: " + (System.currentTimeMillis()-begin) + "ms");

    begin = System.currentTimeMillis();
    System.out.println("Number of values in range (using Stream): " +
            points.stream().parallel().filter(p -> qtree.RangeIntersectsPoint(range.topLeft, range.width,range.height,p)).count());
    System.out.println("It tool: " + (System.currentTimeMillis()-begin) + "ms");
  }
}
