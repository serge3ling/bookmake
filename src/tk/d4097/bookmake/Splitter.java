package tk.d4097.bookmake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Splitter {
  private final int avg;
  private final int maxDeviation;
  private final int count;
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
    list = new ArrayList<>(count / avg);

    int left = count;

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

  public Splitter(int avg, int maxDeviation, int count) {
    this.avg = avg;
    this.maxDeviation = maxDeviation;
    min = avg - maxDeviation;
    this.count = count;

    checkArgs();
  }

  private void checkArgs() {
    assert (count < 1) : "Count cannot be less than 1.";
    assert (avg < 1) : "Average cannot be less than 1.";
    assert (avg - maxDeviation < 1) : "Average minus max deviation cannot be less than 1.";
    assert (avg - maxDeviation > count) : "Average minus max deviation cannot be greater than count.";
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder("[avg=" + avg + ", maxDeviation=" + maxDeviation + ", count=" + count);
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
