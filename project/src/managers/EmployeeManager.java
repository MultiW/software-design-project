package managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import employeecreator.EmployeeCreator;
import employeecreator.LoaderCreator;
import employeecreator.PickerCreator;
import employeecreator.ReplenisherCreator;
import employeecreator.SequencerCreator;
import warehousing.Employee;
import warehousing.Loader;
import warehousing.OrderSet;
import warehousing.Picker;
import warehousing.Replenisher;
import warehousing.WriteFile;
import warehousing.Sequencer;

/**
 * Handles events for each Employee and stores all employees.
 */
public class EmployeeManager {

  /** List of all Employees in the warehouse. */
  private HashMap<String, Employee> employeeList;
  /** Maps string to an employee creator class to create instances of Employee. */
  private HashMap<String, EmployeeCreator> employeeIdentifier;

  /**
   * Initializes HashMaps that hold employees and employee initializer objects.
   */
  public EmployeeManager() {
    employeeList = new HashMap<String, Employee>();
    employeeIdentifier = new HashMap<String, EmployeeCreator>();

    employeeIdentifier.put("Picker", new PickerCreator());
    employeeIdentifier.put("Sequencer", new SequencerCreator());
    employeeIdentifier.put("Loader", new LoaderCreator());
    employeeIdentifier.put("Replenisher", new ReplenisherCreator());
  }

  /**
   * Initialize new employee or set employee as ready.
   * 
   * @param username unique id of each employee.
   * @param type employee type.
   */
  public void employeeReady(String username, String type) {
    Employee employee = findEmployee(username);
    if (employee == null) {
      addEmployee(username, type);
    } else {
      employee.setReady();
    }
  }

  /**
   * Handles scan event for all employees.
   * 
   * @param name unique id of each employee.
   * @param sku number of the scanned fascia.
   * @param warehouseManager holds access to warehouse related information.
   */
  public void employeeScan(String name, String sku, WarehouseManager warehouseManager) {
    Employee employee = findEmployee(name);
    employee.scanFascia(sku);
    pickFascia(employee, warehouseManager);
    refillWarehouse(employee, warehouseManager);
  }

  /**
   * Hanldes rescan event for all employees.
   * 
   * @param username unique id of each employee.
   * @param warehouseManager holds access to warehouse related information.
   */
  public void rescan(String username, WarehouseManager warehouseManager) {
    Employee employee = findEmployee(username);

    if (employee instanceof Picker) {
      warehouseManager.returnFascia(((Picker) employee).getCurrLocation());
    }
    employee.rescanFascia();
  }

  /**
   * Notify Picker that OrderSet has been marshalled.
   */
  public void pickerMarshal(String name) {
    ((Picker) findEmployee(name)).marshall();
  }

  /**
   * Notify Loader that OrderSet is loaded onto a truck.
   * 
   * @param username unique id of each employee.
   * @param orderManager holds access to OrderSets in the warehouse.
   */
  public void orderSetLoaded(String username, OrderManager orderManager) {
    Loader loader = (Loader) findEmployee(username);
    loader.orderLoaded();
  }

  /**
   * Notify Employees that current set of scans has been completed.
   * 
   * @param username unique id of each employee.
   * @param orderManager holds access to OrderSets in the warehouse.
   */
  public void confirm(String username, OrderManager orderManager) {
    Employee employee = findEmployee(username);
    employee.confirm();
  }

  /**
   * Throw out current list of scanned fascias.
   * 
   * @param username unique id of each employee.
   */
  public void throwOut(String username) {
    ((Sequencer) findEmployee(username)).throwOut();
  }

  /**
   * Get all outputs from each employee and output them.
   * 
   * @param input of each event.
   * @param wf holds access to file to write to.
   */
  public void createOutput(String input, WriteFile wf) {
    wf.writeLine("input: " + input);
    for (String key : employeeList.keySet()) {
      String output = employeeList.get(key).getOutput();
      if (!output.equals("")) {
        System.out.println("Output: " + output);
        wf.writeLine("output: " + output);
      }
    }
  }

  /**
   * Assigns given OrderSet to an available employee that works with the specific status of the
   * OrderSet. Called every time OrderSet's status changes.
   * 
   * @param orderSet OrderSet that an employee is currently working on.
   * @param orderManager holds access to OrderSets in the warehouse.
   */
  public void assignTask(OrderSet orderSet, OrderManager orderManager) {
    if (orderManager.getOrderSetList().isEmpty() || orderSet == null) {
      return;
    }

    boolean assigned = false;
    Set<String> keySet = employeeList.keySet();
    for (Iterator<String> i = keySet.iterator(); i.hasNext() && !assigned;) {
      String username = i.next();
      Employee employee = employeeList.get(username);
      if (!employee.isReady() || !employee.getRequiredStatus().equals(orderSet.getStatus())) {
        continue;
      }
      if (employee instanceof Loader) {
        if (orderSet.equals(orderManager.getFirstOrderSet())) {
          employee.setOrder(orderSet);
          orderManager.updateOrderStatus(orderSet);
          assigned = true;
        }
      } else {
        employee.setOrder(orderSet);
        orderManager.updateOrderStatus(orderSet);
        assigned = true;
      }
    }
  }

  /**
   * Assigns given employee (from username parameter) to an available OrderSet that they can work
   * with. Called whenever an employee is available.
   * 
   * @param username username of newly availble employee.
   * @param orderManager holds access to OrderSets in the warehouse.
   */
  public void assignTask(String username, OrderManager orderManager) {
    if (orderManager.getOrderSetList().isEmpty()) {
      return;
    }

    ArrayList<OrderSet> orderSets = orderManager.getOrderSetList();
    Employee employee = findEmployee(username);
    if (orderManager.getFirstOrderSet().getStatus().equals(employee.getRequiredStatus())
        && employee instanceof Loader) {
      employee.setOrder(orderManager.getFirstOrderSet());
    } else {
      for (int i = 0; i < orderSets.size(); i++) {
        if (employee.getRequiredStatus().equals(orderSets.get(i).getStatus())
            && employee.isReady()) {
          employee.setOrder(orderSets.get(i));
          orderManager.updateOrderStatus(orderSets.get(i));
        }
      }
    }
  }

  /**
   * Replenish warehouse locations that are low on skus. Called after every scan. If Picker makes a
   * mistake and has to put the fascia back into its location but the location is already
   * replenished and full, then they will throw out the fascia. Note: We want to replenish as soon
   * as possible to avoid empty stocks.
   * 
   * @param warehouseManager holds access to warehouse related information.
   */
  public void replenishTask(WarehouseManager warehouseManager) {
    // match replenisher with the required location.
    ArrayList<String> replenishList = warehouseManager.getReplenishSku();

    Set<String> keySet = employeeList.keySet();
    for (Iterator<String> i = keySet.iterator(); i.hasNext();) {
      String username = i.next();
      Employee employee = employeeList.get(username);
      if (employee.isReady() && employee instanceof Replenisher && !replenishList.isEmpty()) {
        String sku = replenishList.remove(0);
        ((Replenisher) employee).setSku(sku);
      }
    }
  }

  /**
   * Replenish warehouse locations that are low on skus. Called whenever an employee is available in
   * order to assign them to a warehouse location to replenish.
   * 
   * @username of newly availble employee.
   * @param warehouseManager holds access to warehouse related information.
   */
  public void replenishTask(String username, WarehouseManager warehouseManager) {
    ArrayList<String> replenishList = warehouseManager.getReplenishSku();
    Employee employee = findEmployee(username);

    if ((employee instanceof Replenisher) && !replenishList.isEmpty()) {
      String sku = replenishList.remove(0);
      ((Replenisher) employee).setSku(sku);
    }
  }

  /**
   * @param username of employee.
   * @return OrderSet that the employee is currently working on.
   */
  public OrderSet getOrderSet(String username) {
    if (findEmployee(username) == null) {
      return null;
    }
    return findEmployee(username).getOrder();
  }

  /**
   * @param name of Employee.
   * @return Employee with given name.
   */
  public Employee findEmployee(String name) {
    return employeeList.get(name);
  }

  /**
   * Assigns given employee (if Replenisher) to replenish the first location that needs to be
   * replenished.
   * 
   * @param employee that is to replenish a warehouse location.
   * @param warehouseManager holds access to warehouse related information.
   */
  private void refillWarehouse(Employee employee, WarehouseManager warehouseManager) {
    if (employee instanceof Replenisher) {
      warehouseManager.refillFascia(((Replenisher) employee).getSku());
    }
  }

  /**
   * Informs given employee (if picker) that fascia has been scanned.
   * 
   * @param employee to pick/scan fascia.
   * @param warehouseManager holds access to warehouse related information.
   */
  private void pickFascia(Employee employee, WarehouseManager warehouseManager) {
    if (employee instanceof Picker) {
      warehouseManager.getFascia(((Picker) employee).getPrevLocation());
    }
  }

  /**
   * Create new employee.
   * 
   * @param username of employee.
   * @param type of employee.
   */
  private void addEmployee(String username, String type) {
    employeeList.put(username, employeeIdentifier.get(type).createEmployee(username));
  }
}
