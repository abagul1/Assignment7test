package cs3500.views.visualview;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;


import javax.swing.*;


import cs3500.IAnimation;
import cs3500.motions.Motion;

public class EditorPanel extends JPanel {
  private IAnimation rom;
  private WindowType wt;

  public EditorPanel(IAnimation m) {
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
    this.removeAll();
    JPanel shapePanel = new JPanel();
    //TODO: When the animation is started, and then edit is clicked, doesnt fully overlay.
    this.setBackground(Color.black);
    JButton edit = new JButton("Edit KeyFrame");
    JButton create = new JButton("Create Shape");
    JButton delete = new JButton("Delete Shape");

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
        String data;
        if (shapeList.getSelectedIndex() != -1 && e.getSource() == edit) {
          data = shapeList.getSelectedValue();
          List<Motion> keyFrames = rom.getKeyFrame(data);
          setKeyFramesWindow(keyFrames);
        }
        else if (e.getSource() == create) {
          createShape();
        }
        else if (shapeList.getSelectedIndex() != -1 && e.getSource() == delete) {
          data = shapeList.getSelectedValue();
          rom.deleteElement(data);
          finalScreen("Shape is deleted, click start to go back to the animation,"
                  + " or edit to continue editing.");
        }
      }
    };
    create.addMouseListener(ml);
    delete.addMouseListener(ml);
    edit.addMouseListener(ml);
    shapePanel.add(edit);
    shapePanel.add(create);
    shapePanel.add(delete);
    shapePanel.add(shapeScroll);
    this.add(shapePanel);
  }

  private void createShape() {
    this.removeAll();
    this.setBackground(Color.WHITE);
    JButton create = new JButton("Create Element");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(create);
    JPanel fieldPanel = new JPanel();

    JPanel idPanel =new JPanel(new BorderLayout());
    JTextField id = new JTextField();
    id.setColumns(10);
    JLabel idLabel = new JLabel("Name: ");
    idPanel.add(id, BorderLayout.CENTER);
    idPanel.add(idLabel, BorderLayout.WEST);
    fieldPanel.add(idPanel);

    JPanel typePanel =new JPanel(new BorderLayout());
    JTextField type = new JTextField();
    type.setColumns(10);
    JLabel typeLabel = new JLabel("Type: ");
    typePanel.add(type, BorderLayout.CENTER);
    typePanel.add(typeLabel, BorderLayout.WEST);
    fieldPanel.add(typePanel);

    MouseListener ml = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getSource() == create) {
          String name = id.getText();
          String t = type.getText();
          rom.insertElement(name, t);
          finalScreen("Shape is inserted, click start to go back to the animation,"
                  + " or edit to continue editing.");
        }
      }
    };
    create.addMouseListener(ml);
    this.add(fieldPanel, Component.CENTER_ALIGNMENT);
    this.add(buttonPanel, Component.CENTER_ALIGNMENT);
  }

  private void setAnimationWindow() {
    removeAll();
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
        if (e.getSource() == insert) {
          insertKeyFrame();
        }
        else if (keyFramesList.getSelectedIndex() != -1 && e.getSource() == edit) {
          data = keyFramesList.getSelectedValue();
          editKeyFrame(data);
        }
        else if (keyFramesList.getSelectedIndex() != -1 && e.getSource() == delete) {
          data = keyFramesList.getSelectedValue();
          deleteKeyFrame(data);
        }
      }
    };
    insert.addMouseListener(ml);
    edit.addMouseListener(ml);
    delete.addMouseListener(ml);
    this.add(insert);
    this.add(edit);
    this.add(delete);
    this.add(keyFrameScroll);
  }

  private void insertKeyFrame() {
    this.removeAll();
    this.setBackground(Color.WHITE);
    JPanel fieldPanel = this.drawTextFields();
    JButton insert = new JButton("Insert KeyFrame");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(insert);
    MouseListener ml = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getSource() == insert) {
          StringBuilder str = new StringBuilder();
          for (Component c : fieldPanel.getComponents()) {
            if (c instanceof JTextField) {
              System.out.println(((JTextField) c).getText());
              str.append(((JTextField) c).getText());
            }
          }
        }
      }
    };
    insert.addMouseListener(ml);
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    this.add(fieldPanel, Component.CENTER_ALIGNMENT);
    this.add(buttonPanel, Component.CENTER_ALIGNMENT);
  }

  private void finalScreen(String message) {
    this.removeAll();
    this.setBackground(Color.WHITE);
    JLabel m = new JLabel(message);
    this.add(m);
  }

  private void editKeyFrame(String data) {

  }

  private void deleteKeyFrame(String data) {

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

  private JPanel drawTextFields() {
    JPanel fieldPanel = new JPanel();

    JPanel tickPanel =new JPanel(new BorderLayout());
    JTextField tick = new JTextField();
    tick.setColumns(4);
    JLabel tickLabel = new JLabel("T: ");
    tickPanel.add(tick, BorderLayout.CENTER);
    tickPanel.add(tickLabel, BorderLayout.WEST);
    fieldPanel.add(tickPanel);

    JPanel xPanel =new JPanel(new BorderLayout());
    JTextField xPos = new JTextField();
    xPos.setColumns(4);
    JLabel xPosLabel = new JLabel("X:");
    xPanel.add(xPos, BorderLayout.CENTER);
    xPanel.add(xPosLabel, BorderLayout.WEST);
    fieldPanel.add(xPanel);

    JPanel yPanel = new JPanel(new BorderLayout());
    JTextField yPos = new JTextField();
    yPos.setColumns(4);
    JLabel yPosLabel = new JLabel("Y:");
    yPanel.add(yPos, BorderLayout.CENTER);
    yPanel.add(yPosLabel, BorderLayout.WEST);
    fieldPanel.add(yPanel);

    JPanel wPanel = new JPanel(new BorderLayout());
    JTextField width = new JTextField();
    width.setColumns(4);
    JLabel widthLabel = new JLabel("W:");
    wPanel.add(width, BorderLayout.CENTER);
    wPanel.add(widthLabel, BorderLayout.WEST);
    fieldPanel.add(wPanel);

    JPanel hPanel = new JPanel(new BorderLayout());
    JTextField height = new JTextField();
    height.setColumns(4);
    JLabel heightLabel = new JLabel("H:");
    hPanel.add(height, BorderLayout.CENTER);
    hPanel.add(heightLabel, BorderLayout.WEST);
    fieldPanel.add(hPanel);

    JPanel rPanel = new JPanel(new BorderLayout());
    JTextField red = new JTextField();
    red.setColumns(4);
    JLabel redLabel = new JLabel("R:");
    rPanel.add(red, BorderLayout.CENTER);
    rPanel.add(redLabel, BorderLayout.WEST);
    fieldPanel.add(rPanel);

    JPanel gPanel = new JPanel(new BorderLayout());
    JTextField green = new JTextField();
    green.setColumns(4);
    JLabel greenLabel = new JLabel("G:");
    gPanel.add(green, BorderLayout.CENTER);
    gPanel.add(greenLabel, BorderLayout.WEST);
    fieldPanel.add(gPanel);

    JPanel bPanel = new JPanel(new BorderLayout());
    JTextField blue = new JTextField();
    blue.setColumns(4);
    JLabel blueLabel = new JLabel("B:");
    bPanel.add(blue, BorderLayout.CENTER);
    bPanel.add(blueLabel, BorderLayout.WEST);
    fieldPanel.add(bPanel);

    return fieldPanel;

  }
}
