package events;

import managers.SoftwareManager;

/**
 * Reads rescan order from loader, sequencer, replenisher ,and picker.
 *
 */
public class RescanEvent extends Event {

  /**
   * Constructor for event that a employee needs to rescan.
   * 
   * @param softwareManager the program that manages the overall process.
   */
  public RescanEvent(SoftwareManager softwareManager) {
    super(softwareManager);
  }

  /**
   * calls the method to request a rescan.
   */
  public void doEvent(String[] command) {
    String username = command[2];
    softwareManager.rescan(username);
  }
}
