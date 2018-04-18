import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {


  public static void main(String[] args) {
    Random random = new Random();
    Qtree<Character> qtree = new Qtree<>(0, 0, Double.MAX_VALUE, 2);
    Set<Point> points = new HashSet<>();
    for (int i = 0; i <100000; i++) {
      Point<Character> point = new Point<>(random.nextDouble() * 1000, random.nextDouble() * 1000, (char) (random.nextInt('z' - 'a')+'a'));
      qtree.add(point);
    }
    long begin = System.currentTimeMillis();
    System.out.println("Number of values in range with Qtree: "+ qtree.getPoints(new Coord(32,42), 200,400).size());
    System.out.println("It tool: " + (System.currentTimeMillis()-begin) + "ms");
  }
}
