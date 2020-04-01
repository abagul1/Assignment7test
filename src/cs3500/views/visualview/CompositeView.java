package cs3500.views.visualview;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import cs3500.IAnimation;
import cs3500.IController;
import cs3500.IView;


public class CompositeView extends JFrame implements IView {
  private IAnimation m;
  private int x;
  private int y;
  private boolean p;
  private JButton start;
  private JButton pause;
  private JButton restart;

  public CompositeView(IAnimation m) {
    super();

    if (m == null) {
      JOptionPane.showMessageDialog(null, "Model cannot be null",
              "Error", JOptionPane.ERROR_MESSAGE);
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.m = m;
    AnimationPanel animationPanel = new AnimationPanel(m);
    p = true;
    start = new JButton("Start");
    pause = new JButton("Pause");
    restart = new JButton("Restart");
    animationPanel.add(start);
    animationPanel.add(pause);
    animationPanel.add(restart);
    this.setTitle("Animation Station");
    this.setSize(m.getWidth(), m.getHeight());
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JScrollPane scrollPane = new JScrollPane(animationPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setPreferredSize(new Dimension(600, 600));
    this.add(scrollPane);
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
    MouseListener ml = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (e.getSource() == pause) {
          p = true;
        }
        if (e.getSource() == start) {
          p = false;
        }
        if (e.getSource() == restart) {
          m.resetAnimation();

        }
        listener.handleButtonClick(x, y);
      }
    };
    pause.addMouseListener(ml);
    start.addMouseListener(ml);
    restart.addMouseListener(ml);
  }

  @Override
  public void execute() {
    if (p == false) {
      this.refresh();
      m.executeOneTick();
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(m.getWidth(), m.getHeight());
  }
}
