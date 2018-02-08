package warehousetesting;

import static org.junit.Assert.*;

import org.junit.Test;

import managers.EventManager;
import warehousing.ReadFile;
import warehousing.WriteFile;


/**
 * Test for Managers
 * 
 */
public class EventManagerTest {
  private WriteFile wf = new WriteFile("out_test.txt");
  private ReadFile rf = new ReadFile("orderstest.txt");

  @Test
  public void testEventManager() {
    String currline = rf.readLine();
    EventManager eventManager = new EventManager();
    while (currline != null) {
      eventManager.processEvent(currline, wf);
      assertEquals(currline, eventManager.getOutput());
      currline = rf.readLine();
    }
    wf.closeFile();
    rf.closeFile();
  }
}
