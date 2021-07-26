package tk.d4097.bookmake;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileMaker {
  private final String nameFrom;
  private final String nameTo;

  private int volumeDigits;

  public FileMaker(String nameFrom, String nameTo) {
    this.nameFrom = nameFrom;
    this.nameTo = nameTo;
  }

  public void make() throws IOException {
    Files.createDirectories(Paths.get(nameTo));
    BufferedInputStream reader = new BufferedInputStream(new FileInputStream(nameFrom));

    Splitter paragraphSplitter = new Splitter(160, 140, reader.available());
    List<Integer> paragraphList = paragraphSplitter.getList();

    Splitter chapterSplitter = new Splitter(70, 56, paragraphList.size());
    List<Integer> chapterList = chapterSplitter.getList();

    Splitter volumeSplitter = new Splitter(45, 30, chapterList.size());
    List<Integer> volumeList = volumeSplitter.getList();

    WordMaker wordMaker = new WordMaker();
    volumeDigits = countDigits(volumeList.size());
    int chapterN = 0;
    int paraN = 0;
    for (int volumeN = 0; volumeN < volumeList.size(); volumeN++) {
      BufferedWriter writer = new BufferedWriter(new FileWriter(nameTo + File.separator + "vol-" + prependZeros(volumeN) + ".txt"));

      for (int chptInner = 1; chptInner <= volumeList.get(volumeN); chptInner++) {
        writer.write("" + chptInner + ".\n");

        for (int paraInner = 0; paraInner < chapterList.get(chapterN); paraInner++) {
          byte[] bytes = new byte[paragraphList.get(paraN)];
          reader.read(bytes);
          String paragraph = wordMaker.make(bytes);
          writer.write(paragraph + "\n");
          paraN++;
        }

        chapterN++;
      }
      writer.close();
    }

    reader.close();
  }

  private int countDigits(int size) {
    int digits = 1;
    int i = size;
    int divided = i / 10;
    while (divided > 0) {
      i = divided;
      digits++;
      divided = i / 10;
    }
    return digits;
  }

  private String prependZeros(int num) {
    int digits = countDigits(num);
    String s = "" + num;

    for (int i = 0; i < (volumeDigits - digits); i++) {
      s = "0" + s;
    }

    return s;
  }
}
