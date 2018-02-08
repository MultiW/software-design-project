package warehousetesting;

import static org.junit.Assert.*;

import org.junit.Test;

import warehousing.Replenisher;

public class ReplenisherTest {

  @Test
  public void testReplenisherGetScanSku() {
    Replenisher r = new Replenisher("Ryan");
    r.scanFascia("15");
    assertEquals("15", r.getSku());
  }

  @Test
  public void testReplenisherRescan() {
    Replenisher r = new Replenisher("Ryan");
    r.scanFascia("123");
    r.rescanFascia();
    assertEquals("", r.getSku());
  }

  @Test
  public void testReplenisherConfirm() {
    Replenisher r = new Replenisher("Ryan");
    r.setSku("33");
    r.scanFascia("33");

    r.confirm();
    assertEquals("Ryan refilled 33", r.getOutput());
    r.rescanFascia();
    r.scanFascia("22");
    r.confirm();
    assertEquals("Ryan wrong refill, please try again", r.getOutput());
  }

}
