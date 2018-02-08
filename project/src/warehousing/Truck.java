package warehousing;

import java.util.ArrayList;

/**
 * Holds OrderSets that are loaded to truck and print out the orders that are shipped.
 */
public class Truck {
  private static final int LEVELS = 10;
  private static final int ORDER_SETS_PER_LEVEL = 2;

  /**
   * Contains OrderSets that are loaded onto the truck. Each index in the array represents a level
   * in the truck, holds OrderSets.
   */
  private ArrayList<OrderSet[]> truckContainer;
  /** Number of OrderSets in truck that is loaded onto the current level. */
  private int orderSetsInLevel;
  /** Array of all OrderSets that are stored in the truck. */
  private ArrayList<OrderSet> loadedOrderSets;
  private boolean truckFull;

  /**
   * Initialize basic variables in class.
   */
  public Truck() {
    resetTruck();
  }

  /**
   * Loads an OrderSet onto the truck and the fullness of the truck.
   * 
   * @param orderSet an order set which is to be delivered.
   * @return success of the load.
   */
  public boolean loadOrderSet(OrderSet orderSet) {
    if (truckFull) {
      return false;
    }
    truckContainer.get(truckContainer.size() - 1)[orderSetsInLevel] = orderSet;
    loadedOrderSets.add(orderSet);
    orderSetsInLevel++;

    // check if current level is full
    if (orderSetsInLevel == ORDER_SETS_PER_LEVEL) {
      // check if truck is full
      if (truckContainer.size() == LEVELS) {
        truckFull = true;

        // move to next level of the truck for loading
      } else {
        orderSetsInLevel = 0;
        truckContainer.add(new OrderSet[ORDER_SETS_PER_LEVEL]);
      }
    }
    return true;
  }

  /**
   * @return true if and only if truck is full.
   */
  public boolean isTruckFull() {
    return truckFull;
  }

  /**
   * Resets the truck to empty after it has been filled completely.
   */
  private void resetTruck() {
    truckContainer = new ArrayList<OrderSet[]>();
    truckContainer.add(new OrderSet[ORDER_SETS_PER_LEVEL]);
    orderSetsInLevel = 0;
    truckFull = false;
    loadedOrderSets = new ArrayList<OrderSet>();
  }

  /**
   * Outputs in orders.csv the orders in the truck which are being shipped off.
   */
  public void printOrderSetDelivered() {
    WriteFile wr = new WriteFile("orders.csv");
    // print all orders
    for (OrderSet orderSet : loadedOrderSets) {
      Order[] orders = orderSet.getOrders();
      for (Order order : orders) {
        wr.writeLine(order.getColour() + "," + order.getModel());
      }
    }
    wr.closeFile();

    resetTruck();
  }
}
