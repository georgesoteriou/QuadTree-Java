public class Coord {
  final double x;
  final double y;

  Coord(double x, double y){
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return "("+x+","+y+")";
  }
}
