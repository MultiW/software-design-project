package warehousing;

/**
 * A representation of a replenisher type employee.
 *
 */
public class Replenisher extends Employee {
  private String sku;
  private String refillSku;

  /**
   * constructor for Replenisher.
   * 
   * @param name name of the employee
   */
  public Replenisher(String name) {
    super(name);
    sku = "";
  }

  /**
   * returns the SKU of the fascia being restocked.
   */
  public String getSku() {
    return sku;
  }

  /**
   * checks the SKU of the fascia that needs restocking.
   * 
   * @param scannedsku SKU of fascia to be restocked
   */
  @Override
  public void scanFascia(String scannedsku) {
    sku = scannedsku;
    output = getName() + " scanned " + sku;
  }

  /**
   * rescans a fascia.
   */
  @Override
  public void rescanFascia() {
    sku = "";
  }

  /**
   * confirms that the fascia has been restocked.
   */
  @Override
  public void confirm() {
    if (sku.equals(refillSku)) {
      output = getName() + " refilled " + sku;
    } else {
      output = getName() + " wrong refill, please try again";
    }

  }

  /**
   * sets the fascia that the replenisher needs to restock.
   * 
   * @param sku a string id for a fascia.
   */
  public void setSku(String sku) {
    refillSku = sku;
    ready = false;
  }

}
