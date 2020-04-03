package cs3500.motions;

import java.awt.Color;
import cs3500.IElement;
import cs3500.elements.Posn;

/**
 * Class to represent a motion, and calculate the changes of an element over time.
 */
public class Motion {
  private IElement element;
  private int t1;
  private int x1;
  private int y1;
  private int w1;
  private int h1;
  private int r1;
  private int g1;
  private int b1;
  private int t2;
  private int x2;
  private int y2;
  private int w2;
  private int h2;
  private int r2;
  private int g2;
  private int b2;

  /**
   * Constructor for a motion.
   * @param e element to apply the motion
   * @param t1 starting tick
   * @param x1 starting x position
   * @param y1 starting y position
   * @param w1 starting width
   * @param h1 starting height
   * @param r1 starting red value
   * @param g1 starting green value
   * @param b1 starting blue value
   * @param t2 ending tick
   * @param x2 ending x position
   * @param y2 ending y position
   * @param w2 ending width
   * @param h2 ending height
   * @param r2 ending red value
   * @param g2 ending green value
   * @param b2 ending blue value
   */
  public Motion(IElement e,
                int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    this.element = e;
    this.t1 = t1;
    this.x1 = x1;
    this.y1 = y1;
    this.w1 = w1;
    this.h1 = h1;
    this.r1 = r1;
    this.g1 = g1;
    this.b1 = b1;
    this.t2 = t2;
    this.x2 = x2;
    this.y2 = y2;
    this.w2 = w2;
    this.h2 = h2;
    this.r2 = r2;
    this.g2 = g2;
    this.b2 = b2;
  }

  /**
   * Method to change elements when the motion is supposed to be executed.
   * @param currentTick current tick in the animation
   */
  public void fire(int currentTick) {
    if (currentTick >= t1 && currentTick <= t2) {
      double x = (double) (x1 * (t2 - currentTick) / (t2 - t1))
              + (double) (x2 * (currentTick - t1) / (t2 - t1));
      double y = (double)(y1 * (t2 - currentTick) / (t2 - t1))
              + (double) (y2 * (currentTick - t1) / (t2 - t1));
      double w = (double)(w1 * (t2 - currentTick) / (t2 - t1))
              + (double) (w2 * (currentTick - t1) / (t2 - t1));
      double h = (double)(h1 * (t2 - currentTick) / (t2 - t1))
              + (double) (h2 * (currentTick - t1) / (t2 - t1));
      double r = (double)(r1 * (t2 - currentTick) / (t2 - t1))
              + (double) (r2 * (currentTick - t1) / (t2 - t1));
      double g = (double)(g1 * (t2 - currentTick) / (t2 - t1))
              + (double) (g2 * (currentTick - t1) / (t2 - t1));
      double b = (double)(b1 * (t2 - currentTick) / (t2 - t1))
              + (double) (b2 * (currentTick - t1) / (t2 - t1));

      this.element.setPosn(new Posn(x, y));
      this.element.setHeight(h);
      this.element.setWidth(w);
      int newRed = (int) r;
      int newGreen = (int) g;
      int newBlue = (int) b;

      if (newRed > 255) {
        newRed = 255;
      }
      if (newGreen > 255) {
        newGreen = 255;
      }
      if (newBlue > 255) {
        newBlue = 255;
      }
      if (newRed < 0) {
        newRed = 0;
      }
      if (newGreen < 0) {
        newGreen = 0;
      }
      if (newBlue < 0) {
        newBlue = 0;
      }

      Color c = new Color(newRed, newGreen, newBlue, 255);
      this.element.setColor(c);
    }
  }

  /**
   * Gets the id of the motion's element.
   * @return id
   */
  public String getElementId() {
    return this.element.getID();
  }

  /**
   * Gets the tick at which the motion begins.
   * @return starting tick
   */
  public int getStartTick() {
    return t1;
  }

  /**
   * Gets the tick that is the ending of the motion.
   * @return ending tick
   */
  public int getEndTick() {
    return t2;
  }

  /**
   * Gets the type of motion being performed on the element.
   * @return type of motion
   */
  public String motionType() {
    Color c1 = new Color(r1, g1, b1);
    Color c2 = new Color(r2, g2, b2);
    if (x1 != x1 || y1 != y2) {
      return "move";
    }
    else if (h1 != h2 || w1 != w2) {
      return "scale";
    }
    else if (c1 != c2) {
      return "color";
    }
    else {
      return "none";
    }
  }

}
