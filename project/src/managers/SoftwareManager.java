package managers;

import warehousing.WriteFile;

/**
 * The controller for 3 sub managers. Allows for them to interact without having direct access
 * to each other's data.
 * @author Kevin He
 *
 */
public class SoftwareManager {
  private WarehouseManager warehouseManager;
  private EmployeeManager employeeManager;
  private OrderManager orderManager;
  
  /**
   * Initializes the software manager with an instance of each submanager.
   */
  public SoftwareManager() {
    warehouseManager = new WarehouseManager();
    employeeManager = new EmployeeManager();
    orderManager = new OrderManager();
  }
  
  /**
   * Adds an order to the order manager. Checks if it can assign a new 
   * OrderSet to an employee.
   * @param model the model of the fascia
   * @param colour the colour of the fascia
   */
  public void inputOrder(String model, String colour) {
    orderManager.addOrder(model, colour);
    int latestOrder = orderManager.getOrderSetList().size() - 1;
    if (latestOrder >= 0) {
      employeeManager.assignTask(orderManager.getOrderSetList().get(latestOrder), orderManager);
    }
  }

  /**
   * Tells the employee manager to confirm the OrderSet was properly processed.
   * Updates the status for the OrderSet and attempts to assign it to a new
   * employee.
   * @param username the name of an employee
   */
  public void confirm(String username) {
    employeeManager.confirm(username, orderManager);
    updateOrderStatus(username);
    employeeManager.assignTask(employeeManager.getOrderSet(username), orderManager);
  }

  /**
   * Loads an OrderSet onto a truck.
   * @param username the name of an employee
   */
  public void loaded(String username) {
    employeeManager.orderSetLoaded(username, orderManager);
    warehouseManager.loadTruck(orderManager);
  }

  /**
   * Generates an output for the console and log, given an input.
   * @param input a string describing an event.
   * @param wf the file writer.
   */
  public void createOutput(String input, WriteFile wf) {
    employeeManager.createOutput(input, wf);
    if (!warehouseManager.createOutput().equals("")){
      System.out.println("Output: " + warehouseManager.createOutput());
      wf.writeLine("output: " + warehouseManager.createOutput());
    }
  }
  
  /**
   * Updates the status of an OrderSet to prepare it for Sequencing.
   * @param username the name of an employee
   */
  public void marshal(String username) {
    employeeManager.pickerMarshal(username);
    updateOrderStatus(username);
    employeeManager.assignTask(employeeManager.getOrderSet(username), orderManager);
  }
  
  /**
   * Readies an employee and attempts to assign a task for it.
   * @param username the name of an employee
   * @param type the job type of an employee
   */
  public void employeeReady(String username, String type) {
    employeeManager.employeeReady(username, type);
    employeeManager.assignTask(username, orderManager);
    employeeManager.replenishTask(username, warehouseManager);
  }
  
  /**
   * Has the employee rescan an SKU.
   * @param username the name of an employee
   */
  public void rescan(String username) {
    employeeManager.rescan(username, warehouseManager);
  }
  
  /**
   * Has the employee scan an SKU.
   * @param username name of the employee
   * @param sku SKU scanned by the employee
   */
  public void scan(String username, String sku) {
    employeeManager.employeeScan(username, sku, warehouseManager);
    employeeManager.replenishTask(warehouseManager);
  }
  
  /**
   * Triggers the end of the simulation.
   */
  public void shutdown() {
    warehouseManager.shutdown();
  }
  
  /**
   * Has an employee throw out an improper OrderSet
   * @param username the name of an employee.
   */
  public void throwOut(String username) {
    employeeManager.throwOut(username);
    orderManager.throwOut(employeeManager.findEmployee(username).getOrder());
    employeeManager.assignTask(employeeManager.getOrderSet(username), orderManager);
  }
  
  /**
   * Updates the status of the OrderSet processed by an employee.
   * @param username the name of an employee
   */
  private void updateOrderStatus(String username) {
	  orderManager.updateOrderStatus(employeeManager.getOrderSet(username));
  }
}
