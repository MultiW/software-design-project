package warehousetesting;

import static org.junit.Assert.*;

import org.junit.Test;

import warehousing.Order;
import warehousing.OrderSet;

public class OrderSetTest {

  @Test
  public void testOrderGetModel() {
    Order o = new Order("S", "White");
    assertEquals("S", o.getModel());
  }

  @Test
  public void testOrderGetColour() {
    Order o = new Order("S", "White");
    assertEquals("White", o.getColour());
  }

  @Test
  public void testOrderSetIsFullFalse() {
    OrderSet o = new OrderSet();
    o.add(new Order("S", "White"));// 1,2
    o.add(new Order("SE", "Beige"));// 9,10
    assertFalse(o.isFull());
  }

  @Test
  public void testOrderSetIsFullTrue() {
    OrderSet o = new OrderSet();
    o.add(new Order("S", "White"));// 1,2
    o.add(new Order("SE", "Beige"));// 9,10
    o.add(new Order("SES", "Red")); // 21, 22
    o.add(new Order("SEL", "Green"));// 31, 32
    assertTrue(o.isFull());
  }

  @Test
  public void testOrderSetEquals() {
    OrderSet o1 = new OrderSet();
    OrderSet o2 = new OrderSet();
    assertFalse(o1.equals(o2));
    o2 = o1;
    assertTrue(o1.equals(o2));
    String teststring = new String();
    teststring = "fail";
    assertFalse(o1.equals(teststring));
  }

  @Test
  public void testOrderSetGetStatus() {
    OrderSet o = new OrderSet();
    assertEquals("unfilled", o.getStatus());
  }

  @Test
  public void testOrderSetGetSkuList() {
    String[] list = {"1", "1", "1", "1", "2", "2", "2", "2"};
    OrderSet o = new OrderSet();
    o.add(new Order("S", "White"));
    o.add(new Order("S", "White"));
    o.add(new Order("S", "White"));
    o.add(new Order("S", "White"));
    assertEquals(list[1], o.getSkuList()[1]);
  }

  @Test
  public void testOrderSetReset() {
    OrderSet o = new OrderSet();
    o.nextStatus();
    o.nextStatus();
    o.reset();
    assertEquals("full", o.getStatus());
  }

}
