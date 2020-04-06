package cs3500.views.visualview;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;


import javax.swing.*;


import cs3500.ReadOnlyAnimation;
import cs3500.motions.Motion;

public class EditorPanel extends JPanel {
  private ReadOnlyAnimation rom;
  private WindowType wt;


  public EditorPanel(ReadOnlyAnimation m) {
    super();
    this.rom = m;
    wt = WindowType.ANIMATION;
  }

  public void setWindow(WindowType wt) {
    this.wt = wt;
    switch (wt) {
      case ANIMATION:
        this.removeAll();
        this.setAnimationWindow();
        break;
      case SHAPEMENU:
        this.setShapeWindow();
        break;
      default:
        throw new IllegalArgumentException("Window type doesn't exist");
    }
  }

  private void setShapeWindow() {
    this.setBackground(Color.lightGray);
    JButton edit = new JButton("edit");
    String[] shapes = rom.getShapes();
    JList<String> shapeList = new JList(shapes);
    shapeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    shapeList.setVisibleRowCount(8);
    JScrollPane shapeScroll = new JScrollPane(shapeList,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    MouseListener ml = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        String data = "";
        if (shapeList.getSelectedIndex() != -1 && e.getSource() == edit) {
          data = shapeList.getSelectedValue();
          List<Motion> keyFrames = rom.getKeyFrame(data);
          setKeyFramesWindow();
        }
      }
    };
    edit.addMouseListener(ml);
    this.add(shapeScroll);
    this.add(edit);
  }

  private void setAnimationWindow() {
    this.setBackground(Color.WHITE);
  }

  private void setKeyFramesWindow() {

  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    g2d.translate(-rom.getX(), -rom.getY());

    switch (wt) {
      case ANIMATION:
        this.animationWindow(g2d);
        break;
      case SHAPEMENU:
        this.shapeWindow(g2d);
        break;
      case MOTIONMENU:
        this.motionMenu(g2d);
        break;
      case EDITORMENU:
        this.editorMenu(g2d);
        break;
      default:
        throw new IllegalArgumentException("Window type doesn't exist");
    }
  }

  private void animationWindow(Graphics2D g2d) {
    this.setBackground(Color.WHITE);
    for (String key : rom.getElements().keySet()) {
      rom.getElement(key).paint(g2d);
    }
  }

  private void shapeWindow(Graphics2D g2d) {

  }

  private void motionMenu(Graphics2D g2d) {

  }

  private void editorMenu(Graphics2D g2d) {

  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(rom.getWidth(), rom.getHeight());
  }
}
