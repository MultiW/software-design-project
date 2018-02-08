package managers;


import events.ConfirmEvent;
import events.Event;
import events.LoadedEvent;
import events.MarshalEvent;
import events.OrderEvent;
import events.ReadyEvent;
import events.RescanEvent;
import events.ScanEvent;
import events.ShutdownEvent;
import events.ThrowOutEvent;
import java.util.HashMap;
import warehousing.WriteFile;

/**
 * The class that manages all the events that that are sent through input.
 */
public class EventManager {
  private HashMap<String, Event> eventMap;
  private String output;

  /**
   * Constructor for the event Manager.
   */
  public EventManager() {
    SoftwareManager softwareManager = new SoftwareManager();
    output = "";
    eventMap = new HashMap<String, Event>();
    eventMap.put("Pick", new ScanEvent(softwareManager));
    eventMap.put("Sequence", new ScanEvent(softwareManager));
    eventMap.put("Load", new ScanEvent(softwareManager));
    eventMap.put("Replenish", new ScanEvent(softwareManager));
    eventMap.put("Loaded", new LoadedEvent(softwareManager));
    eventMap.put("Order", new OrderEvent(softwareManager));
    eventMap.put("Marshal", new MarshalEvent(softwareManager));
    eventMap.put("Rescan", new RescanEvent(softwareManager));
    eventMap.put("Ready", new ReadyEvent(softwareManager));
    eventMap.put("End", new ShutdownEvent(softwareManager));
    eventMap.put("Confirm", new ConfirmEvent(softwareManager));
    eventMap.put("Throwout", new ThrowOutEvent(softwareManager));
  }

  /**
   * Processes the events based on the map generated in constructor.
   * 
   * @param input string which represents the input given in simulation
   * @param wf WriteFile class used to write the input in the outputlog file.
   */
  public void processEvent(String input, WriteFile wf) {
    String[] commands = input.split(" ");
    System.out.println("Input: " + input);
    output = input;
    eventMap.get(commands[0]).doEvent(commands);
    eventMap.get(commands[0]).createOutput(input, wf);
  }

  /**
   * obtains the output of events.
   * 
   * @return a string representing the output.
   */
  public String getOutput() {
    String tempoutput = output;
    return tempoutput;
  }
}
