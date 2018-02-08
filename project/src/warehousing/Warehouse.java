package warehousing;

import managers.EventManager;

public class Warehouse {

  private static ReadFile simulationfile;
  private static WriteFile outputLog;

  /**
   * The main method for the simulation
   * 
   * @param args arguments for file to run.
   */
  public static void main(String[] args) {
    simulationfile = new ReadFile(args[0]);
    outputLog = new WriteFile("log.txt");
    String currline = simulationfile.readLine();
    EventManager eventManager = new EventManager();
    while (currline != null) {
      eventManager.processEvent(currline, outputLog);
      currline = simulationfile.readLine();
    }
    simulationfile.closeFile();
    outputLog.closeFile();
  }
}
