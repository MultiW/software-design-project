package warehousetesting;

import static org.junit.Assert.*;

import org.junit.Test;

import warehousing.SkuTranslation;

public class SkuTranslationTest {
  @Test
  public void testGetFrontFascia() {
    SkuTranslation table = new SkuTranslation();
    String sku = table.getFrontSku("SE", "Black");
    assertEquals("43", sku);
  }

  @Test
  public void testGetBackFascia() {
    SkuTranslation table = new SkuTranslation();
    String sku = table.getBackSku("S", "White");
    assertEquals("2", sku);
  }
}
