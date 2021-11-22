package code;

import java.awt.Color;
import java.util.*;

import given.Image.PixelCoordinate;
import given.Image;

public class ImageSegmenter {

  // Colors to use while coloring
  private static Color[] colors = { Color.BLACK, Color.BLUE, Color.GREEN, Color.GRAY, Color.MAGENTA, Color.RED,
      Color.CYAN, Color.LIGHT_GRAY, Color.ORANGE, Color.PINK, Color.YELLOW, Color.DARK_GRAY };

  /*
   * 
   * YOU CAN ADD MORE FIELDS HERE
   * 
   */

  /**
   * Segment image by finding connected components. Pixels in same component must
   * have the same color. Coloring should be done on a new image which should be
   * returned. Note that you need to use getValidNeighbors.
   * 
   * You can use any graph traversal method you like.
   * 
   * @param epsilon
   *          - threshold value to decide connectedness of two neighboring pixels.
   */
  public Image segmentImage(Image input, double epsilon) {
    Image output = new Image(input.getHeight(), input.getWidth());
    HashSet<PixelCoordinate> segmented = new HashSet<>();
    int colorIndx = 0;
    int numColors = colors.length;

    for (int r = 0; r < input.getHeight(); ++r) {
      for (int c = 0; c < input.getWidth(); ++c) {
        // Get all possible neighbors of pixel at row r and column c for fun
        PixelCoordinate pc = new PixelCoordinate(r, c);
        if(!segmented.contains(pc)){
          List<PixelCoordinate> segment = iterativeDFS(input,pc,segmented,epsilon);
          Color nextColorToUse = colors[colorIndx % numColors];
          for(PixelCoordinate coordinate: segment){
            output.setColor(coordinate, nextColorToUse);
          }
          ++colorIndx;
        }
      }
    }
    return output;
  }


  private List<PixelCoordinate> iterativeDFS(Image input, PixelCoordinate startVertex, HashSet<PixelCoordinate> visited,double epsilon){
    Stack<PixelCoordinate> s = new Stack<>();
    LinkedList<PixelCoordinate> traversal = new LinkedList<>();

    s.push(startVertex);
    while(!s.isEmpty()){
      PixelCoordinate u = s.pop();
      if (!visited.contains(u)){
        visited.add(u);
        traversal.addLast(u);

        for(PixelCoordinate w: getValidNeighbors(input,u,epsilon)){
          if(!visited.contains(w)){
            s.push(w);
          }
        }
      }
    }
    return traversal;
  }

  private List<PixelCoordinate> getValidNeighbors(Image input, PixelCoordinate pc, double epsilon){
    ArrayList<PixelCoordinate> neighbors = new ArrayList<>();
    PixelCoordinate n0 = new PixelCoordinate(pc.r+1,pc.c);
    PixelCoordinate n1 = new PixelCoordinate(pc.r,pc.c-1);
    PixelCoordinate n2 = new PixelCoordinate(pc.r-1,pc.c);
    PixelCoordinate n3 = new PixelCoordinate(pc.r,pc.c+1);

    neighbors.add(n0);
    neighbors.add(n1);
    neighbors.add(n2);
    neighbors.add(n3);

    neighbors.removeIf(n -> !(n.c < input.getWidth() && n.r < input.getHeight() && n.c >= 0 && n.r >= 0)
            || Math.abs(input.getPixelVal(n) - input.getPixelVal(pc)) > epsilon);

    return neighbors;
  }

  /**
   * This function iterates over the image and colors output image using "color"
   * array in circular fashion. i.e. if all colors have been used then pick the
   * first color.
   * 
   * This is given to you as an example of how to use some of the available
   * classes,
   *
   *          - image graph.
   *          - output segmented image.
   */
  public Image dummyIteration(Image input) {
    int colorIndx = 0;
    int numColors = colors.length;
    Image output = new Image(input.getHeight(), input.getWidth());
    for (int r = 0; r < input.getHeight(); ++r) {
      for (int c = 0; c < input.getWidth(); ++c) {
        // Get all possible neighbors of pixel at row r and column c for fun
        PixelCoordinate pc = new PixelCoordinate(r, c);
        
        //HINT: You need to iterate over all the some of neighbors of the current pixel here!
        
        // Get the next color in circular fashion
        Color nextColorToUse = colors[colorIndx % numColors];
        // Set the color of pixel at PixelCoordinate pc of segmentedImage to
        // nextColorToUse
        output.setColor(pc, nextColorToUse);
        // Increment color index
        ++colorIndx;
      }
    }
    return output;
  }

}
