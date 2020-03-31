package cs3500.operations;

import java.awt.Color;
import cs3500.IElement;
import cs3500.IOperation;
import cs3500.elements.Posn;

/**
 * Extends {@code AbstractOp} to handle the incremental motion of an element.
 */
public class Operation implements IOperation {
  private int tick;
  private IElement element;
  private double dx;
  private double dy;
  private double dw;
  private double dh;
  private double dr;
  private double dg;
  private double db;

  /**
   * Constructor for an Operation.
   * @param dx change in x
   * @param dy change in y
   * @param dw change in width
   * @param dh change in height
   * @param dr change in red color
   * @param dg change in green color
   * @param db change in blue color
   * @param tick tick to fire at
   * @param element reference element
   */
  public Operation(double dx, double dy, double dw, double dh, double dr, double dg, double db,
                   int tick, IElement element) {
    this.tick = tick;
    this.element = element;
    this.dx = dx;
    this.dy = dy;
    this.dw = dw;
    this.dh = dh;
    this.dr = dr;
    this.dg = dg;
    this.db = db;
  }

  @Override
  public int getTickToFireAt() {
    return tick;
  }

  @Override
  public void fire() {

    this.element.setPosn(new Posn(this.element.getPosn().getX() + dx,
            this.element.getPosn().getY() + dy));
    this.element.setHeight(element.getDimensions()[0] + dh);
    this.element.setWidth(element.getDimensions()[1] + dw);
    int newRed = (int) (this.element.getColor().getRed() + dr);
    int newGreen = (int) (this.element.getColor().getGreen() + dg);
    int newBlue = (int) (this.element.getColor().getBlue() + db);

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

  @Override
  public String getElementId() {
    return element.getID();
  }

}
