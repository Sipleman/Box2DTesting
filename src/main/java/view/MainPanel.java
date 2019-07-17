package view;

import model.MainModel;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import view.utils.DebugDrawJ2D;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
  public static final int WIDTH = 1200;
  public static final int HEIGHT = 600;
  
  private Graphics2D graphics2D;
  private DebugDrawJ2D draw;
  
  private int currentWidth;
  private int currentHeight;
  
  private final MainModel model;
  private Image dbImage;

  public MainPanel(MainModel model) {
    this.model = model;
    setBackground(Color.BLACK);
    draw = new DebugDrawJ2D(this);
    updateSize(WIDTH, HEIGHT);
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
//    addComponentListener();
  }

  @Override
  public void update(Graphics g) {
    draw.drawCircle(new Vec2(10, 10), 10f, Color3f.GREEN);
    super.update(g);
  }

  public Graphics2D getGraphics2D() {
    return graphics2D;
  }
  
  private void updateSize(int width, int height) {
    this.currentWidth = width;
    this.currentHeight = height;
    draw.getViewportTranform().setExtents(width /2, height /2);
  }

  public boolean render() {
    if (dbImage == null) {
//      log.debug("dbImage is null, creating a new one");
      if (currentWidth <= 0 || currentHeight <= 0) {
        return false;
      }
      dbImage = createImage(currentWidth, currentHeight);
      if (dbImage == null) {
//        log.error("dbImage is still null, ignoring render call");
        return false;
      }
      graphics2D = (Graphics2D) dbImage.getGraphics();
    }
    graphics2D.setColor(Color.black);
    graphics2D.fillRect(0, 0, currentWidth, currentHeight);
    return true;

  }

  public DebugDrawJ2D getDraw() {
    return draw;
  }

  public void paintScreen() {
    try {
      Graphics g = this.getGraphics();
      if ((g != null) && dbImage != null) {
        g.drawImage(dbImage, 0, 0, null);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        g.setColor(Color.YELLOW);
        g.drawLine(0,0, 10, 10);
        g.dispose();
      }
    } catch (AWTError e) {
      System.out.println(e.getMessage());
//      log.error("Graphics context error", e);
    }
  }
}
