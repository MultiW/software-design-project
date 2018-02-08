package warehousetesting;

import static org.junit.Assert.*;

import org.junit.Test;

import warehousing.Order;
import warehousing.OrderSet;
import warehousing.Sequencer;

public class SequencerTest {

  @Test
  public void testSequencerSetOrder() {
    OrderSet o = new OrderSet();
    o.add(new Order("S", "White"));// 1,2
    o.add(new Order("SE", "Beige"));// 9,10
    o.add(new Order("SES", "Red")); // 21, 22
    o.add(new Order("SEL", "Green"));// 31, 32
    Sequencer s = new Sequencer("Bob");
    s.setOrder(o);
    assertEquals("Bob sequence order set" + Integer.toString(o.getId()), s.getOutput());
  }

  @Test
  public void testSequencerGetOrderSet() {
    Sequencer s = new Sequencer("Bob");
    OrderSet o = new OrderSet();
    s.setOrder(o);
    assertTrue(o.equals(s.getOrder()));
  }

  @Test
  public void testSequencerScanFasciaWrong() {
    OrderSet o = new OrderSet();
    o.add(new Order("S", "White"));// 1,2
    o.add(new Order("SE", "Beige"));// 11,12
    o.add(new Order("SES", "Red")); // 21, 22
    o.add(new Order("SEL", "Green"));// 31, 32
    Sequencer s = new Sequencer("Bob");
    s.setOrder(o);
    s.scanFascia("1");
    assertEquals("Bob scanned 1", s.getOutput());
    s.scanFascia("2");
    assertEquals("1", s.getFrontPallet().getFasciaArray()[0]);
    assertEquals(null, s.getBackPallet().getFasciaArray()[0]);
    assertEquals("Bob Sku 2 is incorrect. Rescan or throw away.", s.getOutput());
  }

  @Test
  public void testSequencerScanFasciaCorrect() {
    OrderSet o = new OrderSet();
    o.add(new Order("S", "White"));// 1,2
    o.add(new Order("SE", "Beige"));// 11,12
    o.add(new Order("SES", "Red")); // 21, 22
    o.add(new Order("SEL", "Green"));// 31, 32
    Sequencer s = new Sequencer("Bob");
    s.setOrder(o);
    s.scanFascia("1");
    s.scanFascia("11");
    s.scanFascia("21");
    s.scanFascia("31");
    s.scanFascia("2");
    s.scanFascia("12");
    s.scanFascia("22");
    s.scanFascia("32");
    assertEquals("Bob sequenced correct, please confirm", s.getOutput());
  }

  @Test
  public void testSequencerRescanFascia() {
    OrderSet o = new OrderSet();
    o.add(new Order("S", "White"));// 1,2
    o.add(new Order("SE", "Beige"));// 11,12
    o.add(new Order("SES", "Red")); // 21, 22
    o.add(new Order("SEL", "Green"));// 31, 32
    Sequencer s = new Sequencer("Bob");
    s.setOrder(o);
    s.scanFascia("1");
    s.scanFascia("11");
    s.scanFascia("21");
    s.scanFascia("31");
    s.scanFascia("2");
    s.rescanFascia();
    assertEquals("", s.getFrontPallet().getFascia(3));
  }

  @Test
  public void testSequencerConfirm() {
    Sequencer s = new Sequencer("Bob");
    s.confirm();
    assertEquals("Bob confirmed, ready to load", s.getOutput());
  }

  @Test
  public void testSequencerThrowOut() {
    Sequencer s = new Sequencer("Bob");
    s.throwOut();
    assertTrue(s.isReady());
  }

  @Test
  public void testSequencerGetRequiredStatus() {
    Sequencer s = new Sequencer("Bob");
    assertEquals("picked", s.getRequiredStatus());
  }

}
