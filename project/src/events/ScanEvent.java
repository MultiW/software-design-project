package events;

import managers.SoftwareManager;

/**
 * Reads scan from picker, sequencer, loader, replenisher.
 *
 */
public class ScanEvent extends Event {

  /**
   * Constructor for the event that an employee scans in a fasica.
   * 
   * @param softwareManager the program that manages the overall process.
   */
  public ScanEvent(SoftwareManager softwareManager) {
    super(softwareManager);
  }

  /**
   * calls the method to input the scan that the employee scanner just scaned.
   */
  public void doEvent(String[] command) {
    String sku = command[1];
    String username = command[3];
    softwareManager.scan(username, sku);
  }
}
