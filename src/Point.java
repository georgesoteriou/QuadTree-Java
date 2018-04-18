public class Point<T> {
  private final T value;
  final Coord coord;

  Point(double x, double y, T value){
    this.coord = new Coord(x,y);
    this.value = value;
  }

  @Override
  public String toString() {
    return "Point "+value+": (" +coord.x+","+coord.y+")";
  }
}
