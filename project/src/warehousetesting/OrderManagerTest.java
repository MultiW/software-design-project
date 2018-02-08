package warehousetesting;

import static org.junit.Assert.*;

import org.junit.Test;

import managers.OrderManager;
import warehousing.OrderSet;

public class OrderManagerTest {


  @Test
  public void testAddOrder() {
    OrderManager o = new OrderManager();

    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    assertEquals(1, o.getOrderSetList().size());
  }

  @Test
  public void testGetFirstOrderSet() {
    OrderManager o = new OrderManager();
    assertEquals(null, o.getFirstOrderSet());
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    assertEquals("full", o.getFirstOrderSet().getStatus());
  }

  @Test
  public void testRemoveFirstOrderSet() {
    OrderManager o = new OrderManager();
    assertEquals(null, o.getFirstOrderSet());
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    int hold = o.getOrderSetList().size();
    o.removeFirstOrderSet();
    assertEquals(hold - 1, o.getOrderSetList().size());
  }

  @Test
  public void testUpdateOrderStatus() {

    OrderManager o = new OrderManager();
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    OrderSet s = o.getOrderSetList().get(0);
    o.updateOrderStatus(s);
    assertEquals("picking", o.getOrderSetList().get(0).getStatus());
  }

  @Test
  public void testUpdateThrowOut() {
    OrderManager o = new OrderManager();
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    o.addOrder("SES", "Blue");
    OrderSet s = o.getOrderSetList().get(0);
    o.updateOrderStatus(s);
    o.throwOut(s);
    assertEquals("full", o.getOrderSetList().get(0).getStatus());
  }
}
