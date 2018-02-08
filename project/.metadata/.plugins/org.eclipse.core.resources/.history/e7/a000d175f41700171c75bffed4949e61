package managers;

import java.util.ArrayList;

import warehousing.Truck;
import warehousing.WarehouseLayout;

/**
 * Class that is in charge of warehouse methods, mainly fascia location and stock.
 */
public class WarehouseManager {

  private WarehouseLayout warehouse;
  private ArrayList<String> replenishQueue;
  private Truck truck;

  /**
   * constructor for the manager.
   */
  public WarehouseManager() {
    replenishQueue = new ArrayList<String>();
    warehouse = new WarehouseLayout("initial.csv");
    truck = new Truck();
  }

  /**
   * Takes a fascia from the location to track picker actions.
   * 
   * @param location the location of a specific fascia.
   */
  public void getFascia(String location) {
    warehouse.getFascia(location);
    checkReplenish();
  }

  /**
   * puts back a fascia that was wrongfully taken.
   * 
   * @param sku the sku label of the fascia.
   */
  public void returnFascia(String sku) {
    warehouse.returnFascia(sku);
  }

  /**
   * checks to see if any locations need to be replenished, and will trigger the replenish task.
   */
  private void checkReplenish() {
    String replenishSku = warehouse.getNeedReplenish();
    if (!replenishSku.equals("")) {
      replenishQueue.add(replenishSku);
    }
  }

  /**
   * 
   * @return the Array List of strings that represent the location which needs to be replenished.
   */
  public ArrayList<String> getReplenishSku() {
    return replenishQueue;
  }

  /**
   * is triggered when shut down event is called, writes the final information files.
   */
  public void shutdown() {
    warehouse.outputFile("final.csv", "initial.csv");
    truck.printOrderSetDelivered();
  }

  /**
   * Refills the location where fascia stock was registered to be low.
   * 
   * @param sku the sku which the replenisher restocked at.
   */
  public void refillFascia(String sku) {
    warehouse.replenish(warehouse.getLocation(sku));
  }

  /**
   * loads a order set unto a waiting truck.
   * 
   * @param orderManager class that holds all the orders, including ones that need to be loaded.
   */
  public void loadTruck(OrderManager orderManager) {
    truck.loadOrderSet(orderManager.removeFirstOrderSet());
    truck.isTruckFull();
  }

  /**
   * writes the final output
   * 
   * @return string that represents the output shown in response to a input.
   */
  public String createOutput() {
    if (truck.isTruckFull()) {
      truck.printOrderSetDelivered();
    }
    if (!warehouse.getOutput().equals("")) {
      return warehouse.getOutput();
    }
    return "";
  }
}
