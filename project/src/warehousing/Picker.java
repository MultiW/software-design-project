package warehousing;

/**
 * A subclass of Employee, specializes in the picking process of the warehouse.
 * 
 * @author James
 *
 */
public class Picker extends Employee {

  private String[] travelmap;
  private String[] fascia;
  private int travelmapindex;
  private String[] fasciaToPick;
  private static final String REQUIRED_STATUS = "full";

  /**
   * Constructor for picker. Takes in a name.
   * 
   * @param name the string that represents the username of the picker.
   */
  public Picker(String name) {
    super(name);
    fascia = new String[8];
    fasciaToPick = new String[8];
  }


  /**
   * Give the picker a order set for it to start picking. Generates the travel map within too.
   * 
   * @param order the order set that the picker needs to follow to pick up the fascia
   */
  public void setOrder(OrderSet order) {
    ready = false;
    orderset = order;
    this.travelmap = WarehousePicking.optimize(orderset.getSkuList());
    travelmapindex = 0;
    fasciaToPick = orderset.getSkuList().clone();

    for (int i = 0; i < 8; i++) {
      fascia[i] = "";
    }
    output = getLocation();
  }

  /**
   * Reads input from order log and records the SKU that the picker scans in.
   */
  @Override
  public void scanFascia(String sku) {
    if (!isFull()) {
      fascia[travelmapindex] = sku;
      travelmapindex++;
      match(sku);
    } else {
      output = getName() + " picker is full, cannot scan anymore";
    }
  }

  /**
   * sends the required info for the order set to be put in marshaling, prepares itself for future
   * jobs (it is ready for next picking).
   */
  public void marshall() {
    if (isFull()) {
      travelmapindex = 0;
      output = getName() + " reached marshal area";
    } else {
      output = getName() + " not ready to be marshalled, please pick " + travelmap[travelmapindex];
    }
  }

  /**
   * Get the current location of the picker.
   * 
   * @return String which represents the location in the warehouse.
   */
  private String getLocation() {
    if (isFull()) {
      return getName() + " please Confirm";
    }
    return getName() + " please pick next at " + travelmap[travelmapindex];
  }

  /**
   * Checks and sees if the SKU scanned in matches the SKU that is expected to be scanned in.
   * 
   * @param sku the String that represents the SKU that was supposed to be scanned in.
   * @return true if the SKU matches, false otherwise.
   */
  private void match(String sku) {


    if (fasciaToPickContains(sku)) {
      output = getLocation();
    } else {
      output = "Wrong Fascia " + getName() + ", please go to " + getPrevLocation();
    }
  }

  /**
   * checks if the inputed sku is supposed to be picked.
   * 
   * @param sku a string id for a fascia.
   * @return true if the fascia needs to be picked, false otherwise.
   */
  private boolean fasciaToPickContains(String sku) {
    for (int i = 0; i < fasciaToPick.length; i++) {
      if (fasciaToPick[i].equals(sku)) {
        fasciaToPick[i] = "";
        return true;
      }

    }
    return false;
  }

  /**
   * gets the location that the picker needs to go to.
   * 
   * @return string that represents the location the picker should go to in the warehouse.
   */
  public String getPrevLocation() {
    return travelmap[travelmapindex - 1];
  }


  /**
   * checks whether or not Picker has picked the 8 necessary fascias.
   * 
   * @return true if full, false otherwise
   */
  public boolean isFull() {
    return (travelmapindex >= 8);
  }

  /**
   * Getter method for the fascia that the picker has picked up.
   * 
   * @return a list of integers containing SKU that the picker has picked up.
   */
  public String[] getFasciaList() {
    return this.fascia;
  }

  /**
   * Employee rescans a fascia.
   */
  @Override
  public void rescanFascia() {
    travelmapindex--;
    output = getName() + " requested rescan";
    fascia[travelmapindex] = "";
  }

  /**
   * Employee confirms that the order is ready for the next stage of processing.
   */
  @Override
  public void confirm() {
    output = getName() + " go to marshal";
  }

  /**
   * Returns the current location of the employee.
   * 
   * @return a string with the location the employee is picking a fascia.
   */
  public String getCurrLocation() {
    if (travelmapindex >= 8) {
      return travelmap[7];
    }
    return travelmap[travelmapindex];

  }

  /**
   * Returns a string with the required status of an order processed by this employee.
   *
   * @return the string "full"
   */
  @Override
  public String getRequiredStatus() {
    return REQUIRED_STATUS;
  }
}
