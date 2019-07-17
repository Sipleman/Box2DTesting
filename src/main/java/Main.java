import model.CurrentTest;
import model.MainModel;
import view.MainFrame;
import view.MainPanel;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {

    MainModel mainModel = new MainModel();
    CurrentTest currentTest = new CurrentTest();
//    currentTest.initTest();
    mainModel.setTest(currentTest);
    MainPanel mainPanel = new MainPanel(mainModel);

    MainFrame mainFrame = new MainFrame(mainModel, mainPanel);
    mainFrame.setVisible(true);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
  }
}
