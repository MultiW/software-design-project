package events;

import managers.SoftwareManager;

public class ShutdownEvent extends Event {

  /**
   * Constructor for the shutdown event, at the end of the day/
   * 
   * @param softwareManager the program that manages the overall process.
   */
  public ShutdownEvent(SoftwareManager softwareManager) {
    super(softwareManager);
  }

  /**
   * calls the shutdown process.
   */
  @Override
  public void doEvent(String[] commands) {
    softwareManager.shutdown();
  }

}
