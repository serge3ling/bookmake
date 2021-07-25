package tk.d4097.bookmake;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileMaker {
  private final String nameFrom;
  private final String nameTo;
  private BufferedInputStream input;

  private int byteCnt;
  private List<Integer> paragraphList;

  private int paragraphCnt;
  private List<Integer> chapterList;

  private int chapterCnt;
  private List<Integer> volumeList;

  private int volumeDigits;

  public FileMaker(String nameFrom, String nameTo) {
    this.nameFrom = nameFrom;
    this.nameTo = nameTo;
  }

  public void make() throws IOException {
    Files.createDirectories(Paths.get(nameTo));
    input = new BufferedInputStream(new FileInputStream(nameFrom));

    byteCnt = input.available();
    Splitter paragraphSplitter = new Splitter(160, 140, byteCnt);
    paragraphList = paragraphSplitter.getList();

    paragraphCnt = paragraphList.size();
    Splitter chapterSplitter = new Splitter(70, 56, paragraphCnt);
    chapterList = chapterSplitter.getList();

    chapterCnt = chapterList.size();
    Splitter volumeSplitter = new Splitter(45, 30, chapterCnt);
    volumeList = volumeSplitter.getList();

    volumeDigits = countDigits(volumeList.size());
    for (int volumeN = 0; volumeN < volumeList.size(); volumeN++) {
      BufferedWriter writer = new BufferedWriter(new FileWriter(nameTo + File.separator + "vol-" + prependZeros(volumeN) + ".txt"));
      for (int chapterN = 0; chapterN < volumeList.get(volumeN); chapterN++) {}
      writer.close();
    }

    /*
    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
    writer.write(str);
    writer.close();
    * */
    input.close();
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
