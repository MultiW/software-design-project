package events;

import managers.SoftwareManager;

public class LoadedEvent extends Event {

  /**
   * Event to verify that a loader has loaded a pallet into a truck
   * 
   * @param softwareManager the program that manages the overall process.
   */
  public LoadedEvent(SoftwareManager softwareManager) {
    super(softwareManager);
  }

  @Override
  /**
   * calls method that moved the given pallet unto the truck.
   */
  public void doEvent(String[] command) {
    String username = command[3];
    softwareManager.loaded(username);
  }
}
