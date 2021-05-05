package tk.d4097.bookmake;

public class WordMaker {
  public static final String[] PART1 = {"b", "c", "d", "f", "g", "h", "j",
      "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "z"};
  public static final String[] PART2 = {"a", "o", "u", "y", "i", "e", "ao",
      "aw", "oa", "ow", "ia", "io", "an", "am", "on", "om"};

  public String make(byte[] bytes) {
    String s = "";
    return s;
  }

  String fromByte(byte b) {
    return PART1[(b >>> 4) & 15] + PART2[b & 15];
  }

  public byte[] unmake(String s) {
    return new byte[0];
  }

  public static void main(String[] args) {
    WordMaker wm = new WordMaker();
    for (byte b = -128; b < 127; b++) {
      System.out.println(wm.fromByte(b));
    }
  }
}
