package employeecreator;

import warehousing.Employee;

/**
 * Create an employee. EmployeeCreator is abstract
 *
 */
public abstract class EmployeeCreator {

  /**
   * Constructor for EmployeeCreator.
   * 
   */
  public EmployeeCreator() {}

  /**
   * generic method to create a employee with input username.
   * 
   * @param username the name of employee which is created by the method
   */
  public abstract Employee createEmployee(String username);

}
