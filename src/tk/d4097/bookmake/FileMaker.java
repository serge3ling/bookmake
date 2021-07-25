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


    /*
    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
    writer.write(str);
    writer.close();
    * */
    input.close();
  }
}
