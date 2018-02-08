package warehousetesting;

import static org.junit.Assert.*;

import org.junit.Test;

import warehousing.Order;
import warehousing.OrderSet;
import warehousing.ReadFile;
import warehousing.Truck;

public class TruckTest {

  @Test
  public void testTruckLoadPallet() {
    Truck truck = new Truck();
    assertFalse(truck.isTruckFull());
    for (int i = 0; i < 20; i++) {
      truck.loadOrderSet(null);
    }
    assertEquals(true, truck.isTruckFull());
  }

  @Test
  public void testTruckLoadPalletOverfilled() {
    Truck truck = new Truck();
    assertFalse(truck.isTruckFull());
    for (int i = 0; i < 20; i++) {
      truck.loadOrderSet(null);
    }
    assertEquals(false, truck.loadOrderSet(null));
  }

  @Test
  public void testFullTruckPrintOrders() {
    Truck truck = new Truck();
    OrderSet o = new OrderSet();
    o.add(new Order("S", "White"));// 1,2
    o.add(new Order("SE", "Beige"));// 11,12
    o.add(new Order("SES", "Red")); // 21, 22
    o.add(new Order("SEL", "Green"));// 31, 32
    for (int i = 0; i < 20; i++) {
      truck.loadOrderSet(o);
    }
    truck.printOrderSetDelivered();

    ReadFile rd = new ReadFile("orders.csv");
    for (int i = 0; i < 20; i++) {
      assertEquals("White,S", rd.readLine());
      assertEquals("Beige,SE", rd.readLine());
      assertEquals("Red,SES", rd.readLine());
      assertEquals("Green,SEL", rd.readLine());
    }
    assertEquals(null, rd.readLine());
    assertFalse(truck.isTruckFull());
    rd.closeFile();
  }

  @Test
  public void testNotFullTruckPrintOrders() {
    Truck truck = new Truck();
    OrderSet o = new OrderSet();
    o.add(new Order("S", "White"));// 1,2
    o.add(new Order("SE", "Beige"));// 11,12
    o.add(new Order("SES", "Red")); // 21, 22
    o.add(new Order("SEL", "Green"));// 31, 32
    for (int i = 0; i < 10; i++) {
      truck.loadOrderSet(o);
    }
    truck.printOrderSetDelivered();

    ReadFile rd = new ReadFile("orders.csv");
    for (int i = 0; i < 10; i++) {
      assertEquals("White,S", rd.readLine());
      assertEquals("Beige,SE", rd.readLine());
      assertEquals("Red,SES", rd.readLine());
      assertEquals("Green,SEL", rd.readLine());
    }
    assertEquals(null, rd.readLine());
    assertFalse(truck.isTruckFull());
    rd.closeFile();
  }
}
