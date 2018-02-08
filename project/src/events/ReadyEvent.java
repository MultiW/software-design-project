package events;

import managers.SoftwareManager;

public class ReadyEvent extends Event {

  /**
   * Constructor for event to initialize an employee or set them as ready is already initialized.
   * 
   * @param softwareManager the program that manages the overall process.
   */
  public ReadyEvent(SoftwareManager softwareManager) {
    super(softwareManager);
  }

  /**
   * calls the method that checks to see if the employee exists and creates one if necessary.
   */
  @Override
  public void doEvent(String[] command) {
    String username = command[2];
    String type = command[1];
    softwareManager.employeeReady(username, type);
  }
}
