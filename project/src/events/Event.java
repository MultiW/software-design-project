package events;

import managers.SoftwareManager;
import warehousing.WriteFile;

public abstract class Event {
  protected SoftwareManager softwareManager;

  /**
   * Constructor for events.
   * 
   * @param softwareManager the program that manages the overall process.
   */
  public Event(SoftwareManager softwareManager) {
    this.softwareManager = softwareManager;
  }

  /**
   * generic method to process the command given by input.
   * 
   * @param commands list of strings representing the input split by spaces
   */
  public abstract void doEvent(String[] commands);

  /**
   * generic method to output the actions in response to the input.
   * 
   * @param input the input given by simulation
   */
  public void createOutput(String input, WriteFile wf) {
    softwareManager.createOutput(input, wf);
  }
}
