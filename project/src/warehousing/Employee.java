package warehousing;


/**
 * 
 * @author James Abstract Class for all employees of the warehouse company.
 */
public abstract class Employee {
  protected String name;
  protected OrderSet orderset;
  private String tempoutput;
  protected String output;
  protected boolean ready;


  /**
   * constructor for employees, takes in only name
   * 
   * @param name a string that represents the name of the employee.
   */
  public Employee(String name) {
    this.name = name;
    this.output = "";
    this.ready = true;

  }

  /**
   * getter method for the name of employee.
   * 
   * @return a string representing the name of employee
   */
  public String getName() {
    return this.name;
  }

  /**
   * getter for the out put log.
   * 
   * @return a string representing the out put log
   */
  public String getOutput() {
    tempoutput = this.output;
    this.output = "";
    return tempoutput;
  }

  /**
   * checks if the employee is ready for more work.
   * 
   * @return true if ready, false otherwise
   */
  public boolean isReady() {
    return this.ready;
  }

  /**
   * changes the status of the employee to ready. when the employee is ready it can be assigned more
   * work.
   * 
   */
  public void setReady() {
    this.ready = true;
  }

  /**
   * returns the current order set that the employee is using.
   * 
   * @return an OrderSet object
   */
  public OrderSet getOrder() {
    return this.orderset;
  }

  /**
   * sets the order set for the employee to work with.
   * 
   * @param orderset an OrderSet object
   */
  public void setOrder(OrderSet orderset) {
    this.orderset = orderset;
  }

  /**
   * employee rescans a fascia.
   */
  public abstract void rescanFascia();

  /**
   * employee scans a fascia.
   * 
   * @param scannedsku the sku of the fascia being scanned.
   */
  public abstract void scanFascia(String scannedsku);

  /**
   * confirms that the order can be moved to the next stage.
   * 
   */
  public abstract void confirm();

  /**
   * returns a description of the status the employee needs for an order.
   * 
   * @return a string which matches with the status of an order.
   */
  public String getRequiredStatus() {
    return "Function not implemeneted";
  }
}
