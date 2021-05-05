package tk.d4097.bookmake;

import java.util.ArrayList;
import java.util.List;

public class WordMaker {
  public static final String[] PART1 = {"b", /*"c", */"d", "f", "g", "h", /*"j",*/
      "k", "l", "m", "n", "p", /*"q", */"r", "s", "t", "v", "z", "x", /*"w"*/};
  public static final String[] PART2 = {"a", "o", "u", "y", "i", "e", "ao",
      "aw", "oa", "ow", "ia", "io", "an", "am", "on", "om"};
  public static final char[] VOWELS = {'a', 'o', 'u', 'y', 'i', 'e'};

  public String make(byte[] bytes) {
    StringBuilder sb = new StringBuilder();

    for (byte b : bytes) {
      sb.append(fromByte(b));
    }

    return sb.toString();
  }

  String fromByte(byte b) {
    int extended = 255 & b;
    int part1Index = extended / PART2.length;
    int part2Index = extended % PART2.length;
    return PART1[part1Index] + PART2[part2Index];
  }

  public byte[] unmake(String string) {
    String s = string.toLowerCase();
    List<Byte> byteList = new ArrayList<>();
    int len = s.length();
    int head = moveHeadToVowel(0, s);
    while (head < len) {
      boolean goOn = true;
      int edge = head + 1;

      if ((head + 1) >= len) {
        goOn = false;
      }

      if (goOn) {
        if (!charIsLetter(s.charAt(head + 1))) {
          goOn = false;
        }
      }

      if (goOn) {
        if ((head + 2) >= len) {
          goOn = false;
          edge = head + 2;
        }
      }

      if (goOn) {
        if (!charIsLetter(s.charAt(head + 2))) {
          goOn = false;
          edge = head + 2;
        }
      }

      if (goOn) {
        if (!charIsVowel(s.charAt(head + 2))) {
          edge = head + 2;
        }
      }

      String part1 = s.substring(head - 1, head);
      String part2 = s.substring(head, edge);
      int part1Index = indexOfStringInArray(part1, PART1);
      int part2Index = indexOfStringInArray(part2, PART2);
      if ((part1Index >= 0) && (part2Index >= 0)) {
        byteList.add((byte) (part1Index * PART2.length + part2Index));
      }

      head = moveHeadToVowel(head, s);
    }

    byte[] bytes = new byte[byteList.size()];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = byteList.get(i);
    }
    return bytes;
  }

  int moveHeadToVowel(int head, String s) {
    int len = s.length();
    head = charIsVowel(s.charAt(head)) ? (head + 1) : head;
    do {
      head++;
    } while ((head < len) && !charIsVowel(s.charAt(head)));
    return head;
  }

  boolean charIsVowel(char c) {
    boolean is = false;
    for (char item : VOWELS) {
      if (c == item) {
        is = true;
        break;
      }
    }
    return is;
  }

  boolean charIsLetter(char c) {
    return ((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122));
  }

  int indexOfStringInArray(String string, String[] array) {
    int index = -1;
    for (int i = 0; i < array.length; i++) {
      if (array[i].equals(string)) {
        index = i;
        break;
      }
    }
    return index;
  }

  public static void main(String[] args) {
    WordMaker wm = new WordMaker();
    //byte[] bb = wm.unmake("Zanbo.");

    byte[] b0 = {-128, 4, 68, -29, 0, 122, -17, -48, 51, 127};
    String s = wm.make(b0);
    System.out.println(s);
    byte[] b1 = wm.unmake(s);
    for (int i = 0; i < b0.length; i++) {
      System.out.println("" + b0[i] + " -> " + b1[i]);
    }

    byte[] bytes0 = new byte[256];
    for (byte b = -128; b < 127; b++) {
      bytes0[128 + b] = b;
    }
    bytes0[255] = 127;
    String s1 = wm.make(bytes0);
    System.out.println(s1);
    byte[] bytes1 = wm.unmake(s1);
    boolean fail = false;
    for (int i = 0; i < 256; i++) {
      if (bytes0[i] != bytes1[i]) {
        fail = true;
        break;
      }
    }
    System.out.println("[" + (fail ? "FAIL" : " OK ") + "] All bytes.");
  }
}
