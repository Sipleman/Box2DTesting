package model;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.util.Vector;

public class MainModel {

//  private final DefaultComboBoxModel tests = new DefaultComboBoxModel();
//  private final TestbedSettings settings = new TestbedSettings();
  private DebugDraw draw;
  private CurrentTest test;
  private final Vec2 mouse = new Vec2();
//  private final Vector<TestChangedListener> listeners = new Vector<TestChangedListener>();
  private final boolean[] keys = new boolean[512];
  private final boolean[] codedKeys = new boolean[512]; 
  private float calculatedFps;
  private float panelWidth;
  private int currTestIndex; //TODO: remove
  
  public MainModel() {
//    tests.addElement(new model.CurrentTest());
  }

  //TODO: remove
  public DefaultComboBoxModel getTests() {
    return null;
  }

  

  public DebugDraw getDraw() {
    return draw;
  }

  public void setDraw(DebugDraw draw) {
    this.draw = draw;
  }

  public CurrentTest getTest() {
    return test;
  }

  public void setTest(CurrentTest test) {
    this.test = test;
  }

  public Vec2 getMouse() {
    return mouse;
  }

  public boolean[] getKeys() {
    return keys;
  }

  public boolean[] getCodedKeys() {
    return codedKeys;
  }

  public float getCalculatedFps() {
    return calculatedFps;
  }

  public void setCalculatedFps(float calculatedFps) {
    this.calculatedFps = calculatedFps;
  }

  public float getPanelWidth() {
    return panelWidth;
  }

  public void setPanelWidth(float panelWidth) {
    this.panelWidth = panelWidth;
  }

  public int getCurrTestIndex() {
    return currTestIndex;
  }

  public void setCurrTestIndex(int currTestIndex) {
    this.currTestIndex = currTestIndex;
  }
}
