package events;

import managers.SoftwareManager;

/**
 * Event for when picker goes to marshal.
 *
 */
public class MarshalEvent extends Event {

  /**
   * Constructor for marshal event, takes in softwareManager.
   * 
   * @param softwareManager the program that manages the overall process.
   */
  public MarshalEvent(SoftwareManager softwareManager) {
    super(softwareManager);
  }


  @Override
  /**
   * marshals the employee mentioned in input
   */
  public void doEvent(String[] command) {
    String username = command[2];
    softwareManager.marshal(username);
  }
}
