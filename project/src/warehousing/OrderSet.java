package warehousing;

import java.util.HashMap;

/**
 * OrderSet is a collection of orders. The OrderSet will not be sent to system until there are
 * exactly x orders in it. x is decided through the config file. The default is 4.
 */
public class OrderSet {
  private final int orderSize;
  private static int currid = 1; // Variable is static because that is the most efficient way to
                                 // ensure the ID's are unique for all order sets.
  private int id;
  private Order[] orderSet;
  private int fasiaCount;
  private String status;
  private HashMap<String, String> statusMap = new HashMap<String, String>();
  private String[] skuList;
  private ReadFile initial;

  /**
   * constructor for OrderSet.
   */
  public OrderSet() {
    initial = new ReadFile("warehouseconfig.txt");
    int num = 4;
    String newLine = initial.readLine();
    while (newLine != null) {
      String[] setting = newLine.split("=");
      if (setting[0].equals("ordersetsize")) {
        num = Integer.parseInt(setting[1]);
      }
      newLine = initial.readLine();
    }
    this.orderSize = num;
    id = currid;
    currid++;
    orderSet = new Order[orderSize];
    fasiaCount = 0;
    status = "unfilled";
    statusMap.put("unfilled", "full");
    statusMap.put("full", "picking");
    statusMap.put("picking", "marshalling");
    statusMap.put("marshalling", "picked");
    statusMap.put("picked", "sequencing");
    statusMap.put("sequencing", "sequenced");
    statusMap.put("sequenced", "loading");
    statusMap.put("loading", "loaded");
  }

  /**
   * Add an order to orderSet.
   * 
   * @param order is order needed to be stored in array
   */
  public void add(Order order) {
    orderSet[fasiaCount] = order;
    fasiaCount++;
    if (isFull()) {
      skuList = generateSkuList().clone();
    }
  }

  /**
   * Method to check if orderSet is Full, return boolean.
   */
  public boolean isFull() {
    return fasiaCount == orderSize;
  }

  /**
   * Method to return id of this orderSet.
   */

  public int getId() {
    return id;
  }

  /**
   * Method to return an 2D arraylist which contain list of front sku and back sku.
   */

  private String[] generateSkuList() {

    String[] lst = new String[8];
    int counter = 0;
    for (Order item : orderSet) {
      lst[counter] = item.getFrontSku();
      lst[counter + 4] = item.getBackSku();
      counter++;
    }
    return lst;
  }

  public Order[] getOrders() {
    return orderSet;
  }

  public String[] getSkuList() {
    return skuList;
  }

  /**
   * Method to return the status of loader.
   */

  public String getStatus() {
    return status;
  }

  /**
   * Change the status of this OrderSet to be the next one.
   */
  public void nextStatus() {
    status = statusMap.get(status);
  }

  /**
   * Method to set check if two orderset have the same id.
   */
  public boolean equals(Object object) {
    if (object instanceof OrderSet && ((OrderSet) object).getId() == this.id) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Resets the order set to its default state.
   */
  public void reset() {
    fasiaCount = 0;
    status = "full";
  }
}

