package controller;

import model.CurrentTestBase;
import model.MainModel;
import view.MainPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainController implements Runnable {
  
  public static final int DEFAULT_FPS = 60;
  
  private CurrentTestBase currentTest;
  
  private long startTime;
  private long frameCount;
  private int targetFrameRate;
  private float frameRate = 0;
  private boolean animating = false;
  private Thread animator;
  
  private final MainModel model;
  private final MainPanel panel;

  public MainController(MainModel model, MainPanel panel) {
    this.model = model;
    currentTest = model.getTest();
    setFrameRate(DEFAULT_FPS);
    this.panel = panel;
    animator = new Thread(this, "Testing");
    addListeners();
  }

  private void addListeners() {
    
    panel.addKeyListener(new KeyListener() {
      public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()) {
          //TODO: refactor to array of cases and state machine
          case KeyEvent.VK_LEFT:
            break;
          case KeyEvent.VK_RIGHT:
            break;
        }
      }

      public void keyPressed(KeyEvent e) {

      }

      public void keyReleased(KeyEvent e) {

      }
    });
  }

  public void run() {
    long beforeTime, afterTime, updateTime, timeDiff, sleepTime, timeSpent;
    float timeInSecs;
    beforeTime = startTime = updateTime = System.nanoTime();
    sleepTime = 0;

    animating = true;
    loopInit();
    while (animating) {

      timeSpent = beforeTime - updateTime;
      if (timeSpent > 0) {
        timeInSecs = timeSpent * 1.0f / 1000000000.0f;
        updateTime = System.nanoTime();
        frameRate = (frameRate * 0.9f) + (1.0f / timeInSecs) * 0.1f;
        model.setCalculatedFps(frameRate);
      } else {
        updateTime = System.nanoTime();
      }

      if (panel.render()) {
        update();
        panel.paintScreen();
      }
      frameCount++;

      afterTime = System.nanoTime();

      timeDiff = afterTime - beforeTime;
      sleepTime = (1000000000 / targetFrameRate - timeDiff) / 1000000;
      if (sleepTime > 0) {
        try {
          Thread.sleep(sleepTime);
        } catch (InterruptedException ex) {
        }
      }

      beforeTime = System.nanoTime();
    }
  }

  private void update() {
    if(currentTest != null) {
      currentTest.update();
    }
  }
  
  protected void loopInit() {
    panel.grabFocus();

    if (currentTest != null) {
      currentTest.init(model);
    }
  }

  public void setFrameRate(int fps) {
    if (fps <= 0) {
      throw new IllegalArgumentException("Fps cannot be less than or equal to zero");
    }
    targetFrameRate = fps;
    frameRate = fps;
  }
  
  public synchronized void start() {
    if (animating != true) {
      frameCount = 0;
      animator.start();
    } else {
      System.out.println("WARNING");
//      log.warn("Animation is already animating.");
    }
  }
}
