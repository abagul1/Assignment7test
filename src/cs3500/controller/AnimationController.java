package cs3500.controller;

import javax.swing.*;

import cs3500.IAnimation;
import cs3500.IController;
import cs3500.IView;

public class AnimationController implements IController {
  IView view;
  IAnimation a;
  Timer t;
  int speed;
  int currentTick;
  boolean paused;
  boolean loop;

  public AnimationController(IView v) {
    this.view = v;
    this.paused = true;
    this.currentTick = 0;
    this.loop = false;
  }

  @Override
  public void playAnimation(IAnimation a, String viewType, int tempo) {
    this.a = a;
    this.speed = tempo;
    if (viewType.equals("edit")) {
      view.addClickListener(this);
    }
    view.makeVisible();
    if (viewType.equals("visual") || viewType.equals("edit")) {
      t = new Timer(1000 / speed, e -> execute());
      t.start();
      t.setRepeats(true);
    }
    else {
      execute();
    }
  }

  private void execute() {
    if (!paused) {
      if (a.isDone(currentTick) && loop) {
        currentTick = 0;
        a.resetAnimation();
      }
      view.execute();
      a.executeOneTick();
      currentTick++;
    }
  }

  @Override
  public void handleButtonClick(int x, int y) {

  }

  @Override
  public void changeSpeed(String type) {
    if (type.equals("+")) {
      t.setDelay(1000 / speed++);
    }
    else {
      if (speed > 1) {
        t.setDelay(1000 / speed--);
      }
    }
  }

  @Override
  public boolean getPaused() {
    return paused;
  }

  @Override
  public void setPaused() {
    paused = !paused;
  }

  @Override
  public boolean getLoop() {
    return loop;
  }

  @Override
  public void setLoop() {
    loop = !loop;
  }
}
