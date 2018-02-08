package events;

import managers.SoftwareManager;

public class OrderEvent extends Event {

  /**
   * Constructor for the event where a new order is generated.
   * 
   * @param softwareManager the program that manages the overall process.
   */
  public OrderEvent(SoftwareManager softwareManager) {
    super(softwareManager);
  }

  @Override
  /**
   * all method that creates a new order and add it to the orderset.
   */
  public void doEvent(String[] command) {
    String model = command[1];
    String colour = command[2];
    softwareManager.inputOrder(model, colour);
  }
}
