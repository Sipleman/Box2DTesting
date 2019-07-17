package view;

import controller.MainController;
import model.MainModel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
  
  MainPanel panel;
  MainModel model;
  MainController controller;
  
  public MainFrame(MainModel model, MainPanel panel) throws HeadlessException {
    this.panel = panel;
    this.model = model;
    model.setDraw(panel.getDraw());
    this.controller = new MainController(model, panel);
    
    add(panel);
    pack();
    controller.start();
    
    
  }
  
}
