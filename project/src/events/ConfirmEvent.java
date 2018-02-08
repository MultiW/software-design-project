package events;

import managers.SoftwareManager;

public class ConfirmEvent extends Event {

  /**
   * Constructor for confirm event, used by employees to confirm their scans.
   * 
   * @param softwareManager the program that manages the overall process.
   */
  public ConfirmEvent(SoftwareManager softwareManager) {
    super(softwareManager);
  }

  /**
   * calls the method to confirm the scans done by an employee.
   */
  @Override
  public void doEvent(String[] commands) {
    String username = commands[2];
    softwareManager.confirm(username);
  }

}
