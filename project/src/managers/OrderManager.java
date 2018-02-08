package managers;

import java.util.ArrayList;

import warehousing.Order;
import warehousing.OrderSet;

/**
 * A controller class for OrderSets
 * @author Kevin He
 *
 */
public class OrderManager {

  private ArrayList<OrderSet> orderSetList;
  private OrderSet unfilledOrderSet;
  
  /**
   * Initializes the manager with empty OrderSets
   */
  public OrderManager() {
    orderSetList = new ArrayList<OrderSet>();
    unfilledOrderSet = new OrderSet();
  }

  /**
   * Returns the first OrderSet in the list of filled OrderSets
   * 
   * @return an OrderSet with 4 Orders
   */
  public OrderSet getFirstOrderSet() {
    if (!orderSetList.isEmpty()) {
      return orderSetList.get(0);
    } else {
      return null;
    }
  }
  
  /**
   * Adds a new order to an unfilled OrderSet.
   * @param model the model of the fascia
   * @param colour the colour of the fascia
   */
  public void addOrder(String model, String colour) {
    unfilledOrderSet.add(new Order(model, colour));
    if (unfilledOrderSet.isFull()) {
      unfilledOrderSet.nextStatus();
      orderSetList.add(unfilledOrderSet);
      unfilledOrderSet = new OrderSet();
    }
  }

  /**
   * Removes and returns the first filled OrderSet in the list.
   * @return an OrderSet with 4 Orders
   */
  public OrderSet removeFirstOrderSet() {
    return orderSetList.remove(0);
  }

  /**
   * Updates current status of an OrderSet. Statuses are "unfilled", "full", "picking",
   * "marshalling", "picked", "sequencing", "sequenced", "loading", "loaded"
   * @param order an OrderSet which is able to move on to the next stage of processing
   */
  public void updateOrderStatus(OrderSet order) {
    order.nextStatus();
  }

  /**
   * Returns the list of OrderSets which are currently filled.
   * @return a list of OrderSets that can be processed.
   */
  public ArrayList<OrderSet> getOrderSetList() {
    return orderSetList;
  }

  /**
   * Resets an incorrectly picked OrderSet.
   * @param orderset an OrderSet with 4 Orders
   */
  public void throwOut(OrderSet orderset) {
    orderset.reset();
  }
}
