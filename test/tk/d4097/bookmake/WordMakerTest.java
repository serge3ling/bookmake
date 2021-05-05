package tk.d4097.bookmake;

import org.junit.Assert;
import org.junit.Test;

public class WordMakerTest {
  WordMaker maker;

  public void before() {
    maker = new WordMaker();
  }

  @Test
  public void make_() {
    before();

    String expected = "";
    String actual = maker.make(new byte[0]);
    Assert.assertEquals(expected, actual);
  }
}
