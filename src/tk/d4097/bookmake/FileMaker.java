package tk.d4097.bookmake;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileMaker {
  private final String nameFrom;
  private final String nameTo;
  private BufferedInputStream input;

  public FileMaker(String nameFrom, String nameTo) {
    this.nameFrom = nameFrom;
    this.nameTo = nameTo;
  }

  public void make() throws IOException {
    Files.createDirectories(Paths.get(nameTo));
    input = new BufferedInputStream(new FileInputStream(nameFrom));
    /*
    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
    writer.write(str);
    writer.close();
    * */
    input.close();
  }
}
