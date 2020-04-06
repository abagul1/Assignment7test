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
          setKeyFramesWindow(keyFrames);
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

  private void setKeyFramesWindow(List<Motion> lm) {
    this.removeAll();
    this.setBackground(Color.WHITE);
    String[] m = new String[lm.size()];
    int i = 0;
    for (Motion motion : lm) {
      StringBuilder str = new StringBuilder();
      str.append("T: ").append(motion.getParams()[0]).append(" ")
              .append("X: ").append(motion.getParams()[1]).append(" ")
              .append("Y: ").append(motion.getParams()[2]).append(" ")
              .append("W: ").append(motion.getParams()[3]).append(" ")
              .append("H: ").append(motion.getParams()[4]).append(" ")
              .append("R: ").append(motion.getParams()[5]).append(" ")
              .append("G: ").append(motion.getParams()[6]).append(" ")
              .append("B: ").append(motion.getParams()[7]).append(" ");
      m[i] = str.toString();
      i++;
    }
    JList<String> keyFramesList = new JList(m);
    keyFramesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    keyFramesList.setVisibleRowCount(8);
    JScrollPane keyFrameScroll = new JScrollPane(keyFramesList,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JButton insert = new JButton("Insert");
    JButton edit = new JButton("Edit");
    JButton delete = new JButton("Delete");
    MouseListener ml = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        String data;
        if (keyFramesList.getSelectedIndex() != -1 && e.getSource() == insert) {
          insertKeyFrame();
        }
        else if (keyFramesList.getSelectedIndex() != -1 && e.getSource() == edit) {
          data = keyFramesList.getSelectedValue();
          editKeyFrame();
        }
      }
    };
    this.add(insert);
    this.add(edit);
    this.add(delete);
    this.add(keyFrameScroll);
  }

  private void insertKeyFrame() {

  }

  private void editKeyFrame() {

  }

  private void deleteKeyFrame() {

  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    g2d.translate(-rom.getX(), -rom.getY());
    for (String key : rom.getElements().keySet()) {
      rom.getElement(key).paint(g2d);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(rom.getWidth(), rom.getHeight());
  }
}
