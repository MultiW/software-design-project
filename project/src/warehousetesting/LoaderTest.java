package warehousetesting;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import warehousing.Loader;
import warehousing.Order;
import warehousing.OrderSet;

public class LoaderTest {
  private Loader loader;
  private OrderSet orderSet;

  @Before
  public void initialize() {
    orderSet = new OrderSet();
    orderSet.add(new Order("S", "White"));// 1,2
    orderSet.add(new Order("SE", "Beige"));// 11,12
    orderSet.add(new Order("SES", "Red")); // 21, 22
    orderSet.add(new Order("SEL", "Green"));// 31, 32

    loader = new Loader("Bob");
  }

  @Test
  public void testCorrectScans() {
    loader.setOrder(orderSet);
    loader.scanFascia("2");
    assertEquals("Correct scan.", loader.getOutput());
    loader.scanFascia("12");
    assertEquals("Correct scan.", loader.getOutput());
    loader.scanFascia("22");
    assertEquals("Correct scan.", loader.getOutput());
    loader.scanFascia("32");
    assertEquals("Correct scan.", loader.getOutput());
    loader.scanFascia("1");
    assertEquals("Correct scan.", loader.getOutput());
    loader.scanFascia("11");
    assertEquals("Correct scan.", loader.getOutput());
    loader.scanFascia("21");
    assertEquals("Correct scan.", loader.getOutput());
    loader.scanFascia("31");
    assertEquals("Correct scan.", loader.getOutput());
  }

  @Test
  public void testTooManyScans() {
    testCorrectScans();
    loader.scanFascia("100");
    assertEquals("Scanned too many fascias. Please rescan.", loader.getOutput());
    loader.scanFascia("100");
    assertEquals("Scanned too many fascias. Please rescan.", loader.getOutput());
  }

  @Test
  public void testIncorrectScans() {
    loader.scanFascia("1");
    assertEquals("Incorrect scan. Please rearrange and rescan or throw out.", loader.getOutput());
    loader.scanFascia("1");
    assertEquals("Incorrect scan. Please rearrange and rescan or throw out.", loader.getOutput());
    testCorrectScans();
  }

  @Test
  public void testLoaderFinishJob() {
    testCorrectScans();
    loader.confirm();
    loader.orderLoaded();
    testTooManyScans();
  }

  @Test
  public void testRequiredStatus() {
    assertEquals("sequenced", loader.getRequiredStatus());
  }
}
