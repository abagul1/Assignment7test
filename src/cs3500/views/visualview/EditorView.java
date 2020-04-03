package cs3500.views.visualview;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import cs3500.IAnimation;
import cs3500.IController;
import cs3500.IView;


public class EditorView extends JFrame implements IView {
  private IAnimation m;
  private int x;
  private int y;
  private boolean p;
  private JButton start;
  private JButton pause;
  private JButton restart;
  private JButton plus;
  private JButton minus;
  private JButton looping;

  public EditorView(IAnimation m) {
    super();

    if (m == null) {
      JOptionPane.showMessageDialog(null, "Model cannot be null",
              "Error", JOptionPane.ERROR_MESSAGE);
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.m = m;
    AnimationPanel animationPanel = new AnimationPanel(m); //Bottom Panel
    JPanel aniPane = new JPanel();
    JPanel buttonPane = new JPanel(); //Top panel

    JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buttonPane, aniPane); //Split pane
    this.setPreferredSize(getPreferredSize());
    getContentPane().setLayout(new GridLayout());
    getContentPane().add(splitPane);
    aniPane.add(animationPanel);
    splitPane.setDividerLocation(50);
    JScrollPane scrollPane = new JScrollPane(animationPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setPreferredSize(new Dimension(m.getWidth(), m.getHeight()));
    aniPane.add(scrollPane);
    this.add(splitPane);

    p = true;
    start = new JButton("Start");
    pause = new JButton("Pause");
    restart = new JButton("Restart");
    plus = new JButton("IncSpeed");
    minus = new JButton("DecSpeed");
    looping = new JButton("loop");
    buttonPane.add(start);
    buttonPane.add(pause);
    buttonPane.add(restart);
    buttonPane.add(looping);
    buttonPane.add(plus);
    buttonPane.add(minus);
    this.setTitle("Animation Station");
    this.setSize(m.getWidth(), m.getHeight());
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        if (e.getSource() == pause && !p) {
          p = true;
        }
        else if (e.getSource() == start) {
          p = false;
        }
        else if (e.getSource() == restart) {
          m.resetAnimation();
        }
        else if (e.getSource() == plus) {
          listener.changeSpeed("+");
        }
        else if (e.getSource() == minus) {
          listener.changeSpeed("-");
        }
        else if (e.getSource() == looping) {
          //Todo: implement looping
        }
        listener.handleButtonClick(x, y);
      }
    };
    pause.addMouseListener(ml);
    start.addMouseListener(ml);
    restart.addMouseListener(ml);
    plus.addMouseListener(ml);
    minus.addMouseListener(ml);
    looping.addMouseListener(ml);
  }

  @Override
  public void execute() {
    if (!p) {
      this.refresh();
      m.executeOneTick();
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(m.getWidth(), m.getHeight());
  }
}
