package cs3500.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cs3500.IAnimation;
import cs3500.IElement;
import cs3500.IOperation;
import cs3500.animator.util.AnimationBuilder;
import cs3500.elements.Ellipse;
import cs3500.elements.Posn;
import cs3500.elements.Rectangle;
import cs3500.operations.Operation;


/**
 * Represents the Animation with all the elements and operations.
 */
public class AnimationModel implements IAnimation {

  private Map<String, IElement> elements;
  private List<IOperation> operations;
  private Map<String, List<String>> verboseOps;
  private Map<String, String> declaredShapes;

  private int currentTick;

  private final int windowWidth;
  private final int windowHeight;
  private final int leftX;
  private final int topY;

  /**
   * Constructor for animation model.
   * @param width width of animation panel
   * @param height height of animation panel
   */
  public AnimationModel(int x, int y, int width, int height) {
    elements = new LinkedHashMap<>();
    operations = new ArrayList<>();
    verboseOps = new LinkedHashMap<>();
    declaredShapes = new LinkedHashMap<>();
    currentTick = 0;
    windowWidth = width;
    windowHeight = height;
    leftX = x;
    topY = y;
  }

  @Override
  public void motion(String name,
                     int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                     int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {

    this.checkNotNull();

    double tickDiff = t2 - t1;
    double dx = (x2 - x1) / tickDiff;
    double dy = (y2 - y1) / tickDiff;
    double dw = (w2 - w1) / tickDiff;
    double dh = (h2 - h1) / tickDiff;
    double dr = (r2 - r1) / tickDiff;
    double dg = (g2 - g1) / tickDiff;
    double db = (b2 - b1) / tickDiff;

    if (!elements.containsKey(name)) {
      try {
        switch (declaredShapes.get(name)) {
          case "rectangle":
            elements.put(name, new Rectangle(name, new Color(r1, g1, b1, 0), new Posn(x1, y1),
                    h1, w1));
            break;
          case "ellipse":
            elements.put(name, new Ellipse(name, new Color(r1, g1, b1,0), new Posn(x1, y1), h1,
                    w1));
            break;
          default:
            throw new IllegalArgumentException("This type of shape doesn't exist: "
                    + declaredShapes.get(name));
        }
      } catch (NullPointerException npe) {
        throw new IllegalArgumentException("Element id doesn't exist");
      }
    }

    for (int i = t1; i < t2; i++) {
      operations.add(new Operation(dx, dy, dw, dh, dr, dg, db, i, elements.get(name)));
    }
    this.addVerboseMotion(name, t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2, h2, r2, g2, b2);
  }

  /**
   * Adds a verbose description of the motion.
   * @param id element id
   * @param t1 tick to start
   * @param x1 starting x pos
   * @param y1 starting y pos
   * @param w1 starting width
   * @param h1 starting height
   * @param r1 starting red value
   * @param g1 starting green value
   * @param b1 starting blue value
   * @param t2 ending tick
   * @param x2 ending x value
   * @param y2 ending y value
   * @param w2 ending width
   * @param h2 ending height
   * @param r2 ending red value
   * @param g2 ending green value
   * @param b2 ending blue value
   */
  private void addVerboseMotion(String id,
                                int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                                int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    if (verboseOps == null) {
      throw new IllegalStateException("Error: Verbose Ops is null");
    }

    StringBuilder str = new StringBuilder();
    str.append("motion").append(" ").append(id)
            .append(" ").append(t1)
            .append(" ").append(x1)
            .append(" ").append(y1)
            .append(" ").append(w1)
            .append(" ").append(h1)
            .append(" ").append(r1)
            .append(" ").append(g1)
            .append(" ").append(b1)
            .append(" ").append(t2)
            .append(" ").append(x2)
            .append(" ").append(y2)
            .append(" ").append(w2)
            .append(" ").append(h2)
            .append(" ").append(r2)
            .append(" ").append(g2)
            .append(" ").append(b2);

    if (!verboseOps.containsKey(id)) {
      throw new IllegalStateException("Element can't be moved before it exists");
    }
    verboseOps.get(id).add(str.toString());
  }

  /**
   * Checks that the elements and operations fields are not null.
   */
  private void checkNotNull() {
    if (elements == null) {
      throw new IllegalStateException("Error: Element map is null");
    }
    if (operations == null) {
      throw new IllegalStateException("Error: Operations is null");
    }
  }

  @Override
  public void insertElement(String id, String type) {
    if (id == null || type == null) {
      throw new IllegalArgumentException("Cannot add a null element to the animation");
    }
    if (elements == null) {
      throw new IllegalStateException("Error: Element map is null");
    }
    if (operations == null) {
      throw new IllegalStateException("Error: Operations is null");
    }
    if (declaredShapes.containsKey(id)) {
      throw new IllegalArgumentException("Cannot have duplicate elements");
    }

    declaredShapes.put(id, type);
    this.addVerboseInsert(id, type);
  }

  /**
   * Create a textual description of the insert operation.
   * @param id element id
   * @param type type of element
   */
  private void addVerboseInsert(String id, String type) {
    if (verboseOps == null) {
      throw new IllegalStateException("Error: Verbose Ops is null");
    }

    StringBuilder str = new StringBuilder();
    str.append("shape ").append(id).append(" ").append(type);

    if (verboseOps.containsKey(id)) {
      throw new IllegalArgumentException("Element ID already exists");
    }
    else {
      verboseOps.put(id, new ArrayList<>());
      verboseOps.get(id).add(str.toString());
    }
  }

  @Override
  public String getVerboseAnimation() {
    StringBuilder str = new StringBuilder();
    for (String id : verboseOps.keySet()) {
      for (String i : verboseOps.get(id)) {
        str.append(i).append("\n");
      }
      str.append("\n");
    }

    return str.toString();
  }

  @Override
  public void executeOperations() {
    List<IOperation> currentOps = new ArrayList<>();
    while (!operations.isEmpty()) {
      for (Iterator<IOperation> iterator = operations.iterator(); iterator.hasNext();) {
        IOperation op = iterator.next();
        if (op.getTickToFireAt() == currentTick) {
          for (IOperation co : currentOps) {
            if (co.getElementId().equals(op.getElementId()) && op.getClass() == co.getClass()) {
              throw new IllegalArgumentException("Cannot have two motions overlap");
            }
          }
          currentOps.add(op);
          op.fire();
          iterator.remove();
        }
      }
      currentTick++;
      currentOps.clear();
    }
  }

  @Override
  public void executeOperationsUntil(int tick) {
    List<IOperation> currentOps = new ArrayList<>();
    for (currentTick = 0; currentTick < tick; currentTick++) {
      if (operations.isEmpty()) {
        break;
      }
      for (Iterator<IOperation> iterator = operations.iterator(); iterator.hasNext();) {
        IOperation op = iterator.next();
        if (op.getTickToFireAt() == currentTick) {
          for (IOperation co : currentOps) {
            if (co.getElementId().equals(op.getElementId()) && op.getClass() == co.getClass()) {
              throw new IllegalArgumentException("Cannot have two motions overlap");
            }
          }
          currentOps.add(op);
          op.fire();
          iterator.remove();
        }
      }
      currentOps.clear();
    }
  }

  @Override
  public void sortOperations() {
    Collections.sort(this.operations, Comparator.comparingInt(IOperation::getTickToFireAt));
  }

  @Override
  public void executeOneTick() {
    List<IOperation> currentOps = new ArrayList<>();
    for (Iterator<IOperation> iterator = operations.iterator(); iterator.hasNext();) {
      IOperation op = iterator.next();
      if (op.getTickToFireAt() == currentTick) {
        for (IOperation co : currentOps) {
          if (co.getElementId().equals(op.getElementId()) && op.getClass() == co.getClass()) {
            throw new IllegalArgumentException("Cannot have two motions overlap");
          }
        }
        currentOps.add(op);
        op.fire();
        iterator.remove();
      }
      else if (op.getTickToFireAt() > currentTick) {
        break;
      }
    }
    currentTick++;
  }


  @Override
  public IElement getElement(String id) {
    try {
      return elements.get(id);
    } catch (NullPointerException npe) {
      throw new IllegalArgumentException("Element does not exist");
    }
  }

  @Override
  public Map<String,IElement> getElements() {
    return elements;
  }

  @Override
  public List<String> getMotionsForElement(String id) {
    return verboseOps.get(id);
  }

  @Override
  public int getHeight() {
    return windowHeight;
  }

  @Override
  public int getWidth() {
    return windowWidth;
  }

  @Override
  public int getX() {
    return leftX;
  }

  @Override
  public int getY() {
    return topY;
  }

  /**
   * Builder class to create an animation.
   */
  public static final class Builder implements AnimationBuilder<IAnimation> {

    private IAnimation modelToBuild;

    @Override
    public IAnimation build() {
      if (isModelToBuildNull()) {
        throw new IllegalStateException("Builder model has not been initialized");
      }
      return modelToBuild;
    }

    @Override
    public AnimationBuilder<IAnimation> setBounds(int x, int y, int width, int height) {
      this.modelToBuild = new AnimationModel(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<IAnimation> declareShape(String name, String type) {
      if (isModelToBuildNull()) {
        throw new IllegalStateException("Builder model has not been initialized");
      }
      this.modelToBuild.insertElement(name, type);
      return this;
    }

    @Override
    public AnimationBuilder<IAnimation> addMotion(String name, int t1, int x1, int y1, int w1,
                                                  int h1, int r1, int g1, int b1, int t2, int x2,
                                                  int y2, int w2, int h2, int r2, int g2, int b2) {
      if (isModelToBuildNull()) {
        throw new IllegalStateException("Builder model has not been initialized");
      }
      this.modelToBuild.motion(name, t1, x1, y1, w1, h1, r1, g1, b1,
                                     t2, x2, y2, w2, h2, r2, g2, b2);
      return this;
    }

    @Override
    public AnimationBuilder<IAnimation> addKeyframe(String name, int t, int x, int y, int w, int h,
                                                    int r, int g, int b) {
      return this;
    }

    private boolean isModelToBuildNull() {
      return modelToBuild == null;
    }
  }
}
