import java.util.HashSet;
import java.util.Set;

public class Qtree<T> {
  private Set<Point<T>> points;
  private Coord topLeft;
  private double width;
  private Qtree<T> NW;
  private Qtree<T> NE;
  private Qtree<T> SW;
  private Qtree<T> SE;
  private int capacity;

  Qtree(double x, double y, double width, int capacity){
    this.topLeft = new Coord(x,y);
    this.width = width;
    this.points = new HashSet<>();
    this.capacity = capacity;
  }

  public boolean add(Point<T> point){
    if(NW == null && this.points.size()<capacity){
      this.points.add(point);
      return true;
    }else{
        boolean bool = true;
        if (NW == null) {
          NW = new Qtree<>(topLeft.x, topLeft.y, width / 2, capacity);
          NE = new Qtree<>(topLeft.x + width / 2, topLeft.y, width / 2, capacity);
          SW = new Qtree<>(topLeft.x, topLeft.y + width / 2, width / 2, capacity);
          SE = new Qtree<>(topLeft.x + width / 2, topLeft.y + width / 2, width / 2, capacity);
          for (Point<T> p:points) {
            bool &= addsubpoint(p);
          }
          this.points = null;
        }
        return addsubpoint(point) && bool;
    }
  }

  private boolean addsubpoint(Point<T> point){
    boolean bool = true;
    if(NW.QtreeIntersectsPoint(point)){
      bool &= NW.add(point);
    }else if(NE.QtreeIntersectsPoint(point)){
      bool &= NE.add(point);
    }else if(SW.QtreeIntersectsPoint(point)){
      bool &= SW.add(point);
    }else if(SE.QtreeIntersectsPoint(point)){
      bool &= SE.add(point);
    }else{
      return false;
    }
    return bool;
  }

  private static boolean intersects(Coord aTopLeft, double aWidth, double aHeight,
      Coord bTopLeft, double bWidth, double bHeight){
    return !(aTopLeft.x+aWidth  <= bTopLeft.x        ||
             aTopLeft.x         >  bTopLeft.x+bWidth ||
             aTopLeft.y+aHeight <= bTopLeft.y        ||
             aTopLeft.y         > bTopLeft.y+bHeight);
  }


  private boolean QtreeTntersectsRange(Coord topLeft, double width, double height){
    return intersects(this.topLeft, this.width, this.width, topLeft, width, height);
  }

  private boolean QtreeIntersectsPoint(Point<T> point){
    return intersects(this.topLeft, this.width, this.width, point.coord, 0, 0);
  }

  public boolean RangeIntersectsPoint(Coord topLeft, double width, double height, Point<T> point){
    return intersects(topLeft, width, height, point.coord, 0, 0);
  }

  public Set<Point<T>> getPoints(Coord topLeft, double width, double height) {
    Set<Point<T>> pointsInRange = new HashSet<>();
    if(!this.QtreeTntersectsRange(topLeft,width,height)){
      return pointsInRange;
    }

    if(NW == null){
      for (Point<T> p:points) {
        if (RangeIntersectsPoint(topLeft, width, height, p)) {
          pointsInRange.add(p);
        }
      }
    }else{
      pointsInRange.addAll(NW.getPoints(topLeft,width,height));
      pointsInRange.addAll(NE.getPoints(topLeft,width,height));
      pointsInRange.addAll(SW.getPoints(topLeft,width,height));
      pointsInRange.addAll(SE.getPoints(topLeft,width,height));
    }
    return pointsInRange;
  }

  public int size(){
    int count = 0;
    if(NW == null){
      return points.size();
    }else{
      count += NW.size();
      count += NE.size();
      count += SW.size();
      count += SE.size();
      return count;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if(NW != null){
      sb.append("NW:  ").append(NW.toString().replaceAll("\n", "\n     ")).append('\n');
      sb.append("NE:  ").append(NE.toString().replaceAll("\n", "\n     ")).append('\n');
      sb.append("SW:  ").append(SW.toString().replaceAll("\n", "\n     ")).append('\n');
      sb.append("SE:  ").append(SE.toString().replaceAll("\n", "\n     ")).append('\n');
    }else{
      sb.append(points.toString());
    }
    return sb.toString();
  }
}
