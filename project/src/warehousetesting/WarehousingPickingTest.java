package warehousetesting;

import static org.junit.Assert.*;

import org.junit.Test;

import warehousing.WarehousePicking;

public class WarehousingPickingTest {

  @Test
  public void testOptimize() {
    String[] skus = {"1", "8", "2", "5", "6", "9", "4", "17"};
    String[] sku2 = {"37", "38", "21", "22", "43", "44", "43", "44"};
    String[] test =
        {"A,0,0,0", "A,0,0,1", "A,0,0,3", "A,0,1,0", "A,0,1,1", "A,0,1,3", "A,0,2,0", "A,1,1,0"};
    String[] test2 =
        {"A,1,2,0", "A,1,2,1", "B,1,0,0", "B,1,0,1", "B,1,1,2", "B,1,1,2", "B,1,1,3", "B,1,1,3"};
    assertArrayEquals(test, WarehousePicking.optimize(skus));
    assertArrayEquals(test2, WarehousePicking.optimize(sku2));
  }

}
