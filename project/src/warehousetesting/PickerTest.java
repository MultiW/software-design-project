package warehousetesting;

import static org.junit.Assert.*;

import org.junit.Test;

import warehousing.Order;
import warehousing.OrderSet;
import warehousing.Picker;

public class PickerTest {

  @Test
  public void testPickerSetOrder() {
    OrderSet orderset = new OrderSet();
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    Picker picker = new Picker("Alice");

    picker.setOrder(orderset);

    assertFalse(picker.isReady());
    assertEquals("Alice please pick next at A,0,0,0", picker.getOutput());

  }

  @Test
  public void testPickerScanRescanFascia() {
    OrderSet orderset = new OrderSet();
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    Picker picker = new Picker("Alice");
    picker.setOrder(orderset);
    picker.scanFascia("1");
    assertEquals("1", picker.getFasciaList()[0]);
    picker.rescanFascia();
    assertEquals("Alice requested rescan", picker.getOutput());
    picker.scanFascia("4");
    assertEquals("Wrong Fascia Alice, please go to A,0,0,0", picker.getOutput());
    picker.rescanFascia();
    picker.scanFascia("1");
    picker.scanFascia("1");
    picker.scanFascia("1");
    picker.scanFascia("1");
    picker.scanFascia("2");
    picker.scanFascia("2");
    picker.scanFascia("2");
    picker.scanFascia("2");
    assertTrue(picker.isFull());
    assertEquals("Alice please Confirm", picker.getOutput());
    picker.scanFascia("");
    assertEquals("Alice picker is full, cannot scan anymore", picker.getOutput());
  }

  @Test
  public void testPickerMarshal() {
    OrderSet orderset = new OrderSet();
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    Picker picker = new Picker("Alice");
    picker.setOrder(orderset);
    picker.scanFascia("1");
    picker.scanFascia("1");
    picker.scanFascia("1");
    picker.scanFascia("1");
    picker.marshall();
    assertEquals("Alice not ready to be marshalled, please pick A,0,0,1", picker.getOutput());
    picker.scanFascia("2");
    picker.scanFascia("2");
    picker.scanFascia("2");
    picker.scanFascia("2");
    picker.marshall();
    assertEquals("Alice reached marshal area", picker.getOutput());
  }

  @Test
  public void testPickerConfirm() {
    Picker picker = new Picker("Alice");
    picker.confirm();
    assertEquals("Alice go to marshal", picker.getOutput());
  }

  @Test
  public void testPickerGetCurrLocation() {
    OrderSet orderset = new OrderSet();
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    Picker picker = new Picker("Alice");
    picker.setOrder(orderset);
    picker.scanFascia("1");
    assertEquals("A,0,0,0", picker.getCurrLocation());
  }

  @Test
  public void testPickerGetCurrLocationFull() {
    OrderSet orderset = new OrderSet();
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    orderset.add(new Order("S", "White"));
    Picker picker = new Picker("Alice");
    picker.setOrder(orderset);
    picker.scanFascia("1");
    picker.scanFascia("1");
    picker.scanFascia("1");
    picker.scanFascia("1");
    picker.scanFascia("2");
    picker.scanFascia("2");
    picker.scanFascia("2");
    picker.scanFascia("2");
    assertEquals("A,0,0,1", picker.getCurrLocation());
  }

  @Test
  public void testPickerGetRequiredStatus() {
    Picker picker = new Picker("Peach");
    assertEquals("full", picker.getRequiredStatus());
  }

}
