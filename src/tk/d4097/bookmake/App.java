package tk.d4097.bookmake;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
  private JPanel panel;
  private JButton msgBtn;

  public App() {
    msgBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        FileMaker maker = new FileMaker("from.file", "target");
        JOptionPane.showMessageDialog(null, "The message.");
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
