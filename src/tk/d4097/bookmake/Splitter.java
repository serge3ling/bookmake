package tk.d4097.bookmake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Splitter {
  private final int avg;
  private final int maxDeviation;
  private final int size;
  private final int min;
  private boolean splitDone;
  private List<Integer> list;
  private final Random random = new Random();

  public List<Integer> getList() {
    if (!splitDone) {
      makeList();
    }
    return list;
  }

  private void makeList() {
    list = new ArrayList<>(size / avg);

    int left = size;

    while (left > 2 * avg) {
      int r = min + random.nextInt(2 * maxDeviation);
      while (left - r < min) {
        r = min + random.nextInt(2 * maxDeviation);
      }

      list.add(r);
      left -= r;
    }

    if (left > 0) {
      list.add(left);
    }

    splitDone = true;
  }

  public Splitter(int avg, int maxDeviation, int size) {
    this.avg = avg;
    this.maxDeviation = maxDeviation;
    min = avg - maxDeviation;
    this.size = size;

    checkArgs();
  }

  private void checkArgs() {
    assert (size < 1) : "Size cannot be less than 1.";
    assert (avg < 1) : "Average cannot be less than 1.";
    assert (avg - maxDeviation < 1) : "Average minus max deviation cannot be less than 1.";
    assert (avg - maxDeviation > size) : "Average minus max deviation cannot be greater than size.";
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder("[avg=" + avg + ", maxDeviation=" + maxDeviation + ", size=" + size);
    b.append(", list=[");
    int last = list.size() - 1;
    if (list.size() > 0) {
      for (int i = 0; i < last; i++) {
        b.append(list.get(i)).append(", ");
      }
      b.append(list.get(last));
    }
    b.append("]]");
    return b.toString();
  }

  public static void main(String[] args) {
    for (int i = 0; i < 29; i++) {
      Splitter splitter = new Splitter(160, 150, 1024);
      splitter.getList();
      System.out.println(splitter);
    }
  }
}
