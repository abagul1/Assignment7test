package cs3500.views.visualview;

import java.awt.*;

import javax.swing.*;

import cs3500.IController;
import cs3500.IView;
import cs3500.ReadOnlyAnimation;

public class CompositeView extends JFrame implements IView {
  private ReadOnlyAnimation m;

  public CompositeView(ReadOnlyAnimation m) {
    super();

    if (m == null) {
      JOptionPane.showMessageDialog(null, "Model cannot be null",
              "Error", JOptionPane.ERROR_MESSAGE);
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.m = m;
    m.sortOperations();

    AnimationPanel animationPanel = new AnimationPanel(m);
    this.setTitle("Animation Station");
    this.setSize(m.getWidth(), m.getHeight());
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JScrollPane scrollPane = new JScrollPane(animationPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setPreferredSize(new Dimension(600, 600));
    this.add(scrollPane);
    this.makeVisible();
  }


  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void addClickListener(IController listener) {

  }

  @Override
  public void execute() {

  }
}
