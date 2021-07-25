package tk.d4097.bookmake;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class App {
  private JPanel panel;
  private JButton msgBtn;

  public App() {
    msgBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          FileMaker maker = new FileMaker("from.file", "volumes");
          maker.make();
          JOptionPane.showMessageDialog(null, "Success.");
        } catch (IOException exception) {
          JOptionPane.showMessageDialog(null, "IOException.");
        }
      }
    });
  }

  public static void main(String[] args) {
    App app = new App();
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
  }
}
