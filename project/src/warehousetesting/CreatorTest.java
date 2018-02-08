package warehousetesting;

import static org.junit.Assert.*;
import warehousing.Loader;
import warehousing.Sequencer;
import warehousing.Picker;
import warehousing.Replenisher;

import org.junit.Test;

import employeecreator.LoaderCreator;
import employeecreator.PickerCreator;
import employeecreator.ReplenisherCreator;
import employeecreator.SequencerCreator;

public class CreatorTest {

  @Test
  public void testPicker() {
    PickerCreator a = new PickerCreator();
    Picker pa = (Picker) a.createEmployee("Patrick");
    assertEquals("Patrick", pa.getName());
    assertEquals(false, pa.isFull());
  }

  @Test
  public void testLoader() {
    LoaderCreator a = new LoaderCreator();
    Loader lo = (Loader) a.createEmployee("Lorry");
    assertEquals("Lorry", lo.getName());
  }

  @Test
  public void testSequencer() {
    SequencerCreator a = new SequencerCreator();
    Sequencer sa = (Sequencer) a.createEmployee("Sarra");
    assertEquals("Sarra", sa.getName());
  }

  @Test
  public void testReplenisher() {
    ReplenisherCreator a = new ReplenisherCreator();
    Replenisher ri = (Replenisher) a.createEmployee("Rick");
    assertEquals("Rick", ri.getName());
  }

}
