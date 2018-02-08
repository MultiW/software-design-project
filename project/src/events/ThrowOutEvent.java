package events;

import managers.SoftwareManager;

public class ThrowOutEvent extends Event {

  /**
   * Constructor for throw out event, where the Sequencer can decide to toss out the pallets and
   * restart.
   * 
   * @param softwareManager the program that manages the overall process.
   */
  public ThrowOutEvent(SoftwareManager softwareManager) {
    super(softwareManager);
  }

  /**
   * method throw out the pallets.
   */
  @Override
  public void doEvent(String[] commands) {
    softwareManager.throwOut(commands[2]);
  }

}
