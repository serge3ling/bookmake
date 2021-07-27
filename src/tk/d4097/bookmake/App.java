package tk.d4097.bookmake;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class App {
  public static final String OPEN_NAME = "from.file";
  public static final String MESS_NAME = "volumes";

  private JPanel panel;
  private JButton msgBtn;

  public App() {
    msgBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          //FileMaker maker = new FileMaker("from.file", "volumes");
          FileMaker maker = new FileMaker("/home/sergio/temp/cfr.zip", "/home/sergio/temp/volumes");
          maker.unmake();
          JOptionPane.showMessageDialog(null, "Success.");
        } catch (IOException exception) {
          JOptionPane.showMessageDialog(null, "IOException.");
        }
      }
    });
  }

  public static void main(String[] args) {
    App app = new App();
    if ((args.length > 0) && args[0].equals("--gui")) {
      JFrame frame = new JFrame("BookMake");
      frame.setContentPane(app.panel);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.pack();
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          frame.setVisible(true);
        }
      });
    } else {
      String openName = OPEN_NAME;
      String messName = MESS_NAME;
      if (args.length > 0) {
        if (args.length > 1) {
          openName = args[1];
        }
        if (args.length > 2) {
          messName = args[2];
        }
        if (args[0].equals("--make")) {
          try {
            FileMaker maker = new FileMaker(openName, messName);
            maker.make();
          } catch (IOException e) {
            System.err.println("IOException.");
          }
        }
        if (args[0].equals("--unmake")) {
          try {
            FileMaker maker = new FileMaker(openName, messName);
            maker.unmake();
          } catch (IOException e) {
            System.err.println("IOException.");
          }
        }
      }
    }
  }
}
