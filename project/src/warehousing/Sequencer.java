package warehousing;

/**
 * Subclass of Employee, in charge of checking to see if the sequence of the fascia are correctly
 * places.
 * 
 * @author James
 *
 */
public class Sequencer extends Employee {

  private Pallet frontpallet;
  private Pallet backpallet;
  private int scanCounter;
  private static final String REQUIRED_STATUS = "picked";

  /**
   * Constructor method for the sequencer.
   * 
   * @param name name of the sequencer.
   */
  public Sequencer(String name) {
    super(name);
    frontpallet = new Pallet();
    backpallet = new Pallet();
    scanCounter = 0;
  }

  /**
   * Receives the fascia and order set of which the sequencer will check to see if it is sequenced
   * properly.
   * 
   * @param orderset the orderset to be added.
   */
  public void setOrder(OrderSet orderset) {
    this.orderset = orderset;
    ready = false;
    frontpallet = new Pallet();
    backpallet = new Pallet();
    output = name + " sequence order set" + Integer.toString(orderset.getId());
  }

  /**
   * Keeps track of each scan as the sequencer scans in the fascias on the pallet.
   * 
   * @param sku represents the amount at which the scanner as scanned in.
   */
  public void scanFascia(String sku) {
    checkScan(sku);
    if (scanCounter >= 8) {
      output = getName() + " sequenced correct, please confirm";
    }
  }

  /**
   * checks if the fascia matches with the order.
   * 
   * @param sku a string id for a fascia.
   */
  private void checkScan(String sku) {
    if (orderset.getSkuList()[scanCounter].equals(sku)) {
      output = getName() + " scanned " + sku;
      addToPallet(sku);
      scanCounter++;
    } else {
      output = getName() + " Sku " + sku + " is incorrect. Rescan or throw away.";
    }
  }

  /**
   * adds a correct fascia to a pallet.
   * 
   * @param sku a string id for a fascia.
   */
  private void addToPallet(String sku) {
    if (scanCounter < 4) {
      frontpallet.addFascia(sku);
    } else {
      backpallet.addFascia(sku);
    }
  }

  /**
   * Returns a Pallet object containing all the front fascia of an order.
   * 
   * @return a pallet with only front fascia.
   */
  public Pallet getFrontPallet() {
    return frontpallet;
  }

  /**
   * Returns a Pallet object containing all the back fascia of an order.
   * 
   * @return a pallet with only back fascia.
   */
  public Pallet getBackPallet() {
    return backpallet;
  }

  /**
   * Employee rescans a fascia.
   */
  @Override
  public void rescanFascia() {
    frontpallet.clear();
    backpallet.clear();
    scanCounter = 0;
  }

  /**
   * Employee confirms that the order is ready for the next stage of processing.
   */
  @Override
  public void confirm() {
    output = getName() + " confirmed, ready to load";
    scanCounter = 0;
  }

  /**
   * Throws out a pallet. Pallets being thrown out are due to the fascias in the pallet being
   * incorrect.
   */
  public void throwOut() {
    frontpallet.clear();
    backpallet.clear();
    scanCounter = 0;

  }

  /**
   * Returns a string with the required status of an order processed by this employee.
   *
   * @return the string "picked".
   */
  @Override
  public String getRequiredStatus() {
    return REQUIRED_STATUS;
  }
}
