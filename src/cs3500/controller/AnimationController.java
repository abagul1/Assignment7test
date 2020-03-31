package cs3500.controller;

import javax.swing.*;

import cs3500.IAnimation;
import cs3500.IController;
import cs3500.IView;

public class AnimationController implements IController {
  IView view;
  IAnimation a;

  public AnimationController(IView v) {
    this.view = v;
  }

  @Override
  public void playAnimation(IAnimation a, String viewType, int tempo) {
    this.a = a;

    if (viewType.equals("visual")) {
      Timer t = new Timer(1000 / tempo, e -> view.execute());
      t.start();
      t.setRepeats(true);
    }
    else {
      view.execute();
    }
  }

  @Override
  public void handleButtonClick(int x, int y) {

  }
}
