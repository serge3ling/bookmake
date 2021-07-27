package tk.d4097.bookmake;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileMaker {
  private final String openName;
  private final String messName;

  private final WordMaker wordMaker = new WordMaker();
  private int volumeDigits;

  public FileMaker(String openName, String messName) {
    this.openName = openName;
    this.messName = messName;
  }

  public void make() throws IOException {
    Files.createDirectories(Paths.get(messName));
    BufferedInputStream reader = new BufferedInputStream(new FileInputStream(openName));

    List<Integer> paragraphList = new Splitter(160, 140, reader.available()).getList();
    List<Integer> chapterList = new Splitter(70, 56, paragraphList.size()).getList();
    List<Integer> volumeList = new Splitter(45, 30, chapterList.size()).getList();

    volumeDigits = countDigits(volumeList.size());
    int chapterN = 0;
    int paraN = 0;
    for (int volumeN = 1; volumeN <= volumeList.size(); volumeN++) {
      BufferedWriter writer = new BufferedWriter(new FileWriter(messName + File.separator + "vol-" + prependZeros(volumeN) + ".txt"));

      for (int chptInner = 1; chptInner <= volumeList.get(volumeN - 1); chptInner++) {
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
    StringBuilder sb = new StringBuilder("" + num);

    for (int i = 0; i < (volumeDigits - digits); i++) {
      sb.insert(0, "0");
    }

    return sb.toString();
  }

  public void unmake() throws IOException {
    final File dir = new File(messName);
    File[] files = dir.listFiles();
    Arrays.sort(files);
    BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(new File(openName)));
    for (int i = 0; i < files.length; i++) {
      System.out.println(files[i]);
      if (!files[i].isDirectory()) {
        BufferedReader reader = new BufferedReader(new FileReader(files[i]));
        String s = reader.readLine();
        while (s != null) {
          writer.write(wordMaker.unmake(s));
          s = reader.readLine();
        }
        reader.close();
      }
    }
    writer.close();
  }
}
