package warehousetesting;

import static org.junit.Assert.*;

import org.junit.Test;

import warehousing.Pallet;

public class PalletTest {

  @Test
  public void testPalletIsFull() {
    Pallet p = new Pallet();
    p.addFascia("1");
    p.addFascia("2");
    assertFalse(p.isFull());
    p.addFascia("3");
    p.addFascia("4");
    p.addFascia("5");
    assertTrue(p.isFull());
  }

  @Test
  public void testPalletGet() {
    Pallet p = new Pallet();
    p.addFascia("4");
    p.addFascia("3");
    p.addFascia("2");
    p.addFascia("1");
    String[] skus = {"4", "3", "2", "1"};
    assertEquals("1", p.getFascia(3));
    assertArrayEquals(skus, p.getFasciaArray());
  }
}

