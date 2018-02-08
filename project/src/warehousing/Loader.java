
package warehousing;

/**
 * Handles events that direct a Loader object to load one OrderSet. Loader reads fascia scans and
 * checks if the scans are in the correct order. The physical warehouse will know when scanning is
 * over.
 */
public class Loader extends Employee {
  private static final int FASCIAS_PER_PALLET = 4;
  private static final String REQUIRED_STATUS = "sequenced";

  private int skusScannedCount;
  /** Stores the correct sku order of the assigned OrderSet, orderSetToLoad. */
  private String[] skuOrder;

  /**
   * Initialize basic variables.
   * 
   * @param name of Employee
   */
  public Loader(String name) {
    super(name);
    skusScannedCount = 0;
    skuOrder = new String[FASCIAS_PER_PALLET * 2];
  }

  /**
   * Give loader a new order set to load.
   * 
   * @param orderSet is the new order set that the loader will load.
   */
  @Override
  public void setOrder(OrderSet orderSet) {
    this.orderset = orderSet;
    setCorrectSkuOrder();
    output = "Loader " + super.name + " begin scanning picking request " + orderset.getId();
    ready = false;
  }

  /**
   * Receives fascia that is scanned by a loader and checks if it is being loaded in the correct
   * direction. Correct direction is: first rear fascia from first to last order, then front fascia
   * from first to last order.
   * 
   * @param sku SKU of fascia that is scanned.
   */
  @Override
  public void scanFascia(String sku) {
    try {
      if (isScanCorrect(sku)) {
        skusScannedCount++;
        output = "Correct scan.";
      } else {
        output = "Incorrect scan. Please rearrange and rescan or throw out.";
        rescanFascia();
      }
    } catch (IndexOutOfBoundsException error) {
      output = error.getMessage();
    }
  }

  /**
   * Restart scan counter.
   */
  @Override
  public void rescanFascia() {
    skusScannedCount = 0;
  }

  /**
   * Loader has finished loading OrderSet.
   */
  @Override
  public void confirm() {
    skusScannedCount = 0;
    skuOrder = new String[FASCIAS_PER_PALLET * 2];
  }

  /**
   * Loader is done loading.
   */
  public void orderLoaded() {
    orderset = null;
  }

  /**
   * Return the OrderSet status that Loader can process.
   */
  @Override
  public String getRequiredStatus() {
    return REQUIRED_STATUS;
  }
  
  /**
   * Check whether scanned sku is matches the order skus are to be loaded.
   * 
   * @param sku of scanned fascia.
   * @return whether scanned fascia is correct.
   * @throws IndexOutOfBoundsException when too many fascias have been scanned.
   */
  private boolean isScanCorrect(String sku) throws IndexOutOfBoundsException {
    if (skusScannedCount >= 8) {
      throw new IndexOutOfBoundsException("Scanned too many fascias. Please rescan.");
    }
    return sku.equals(skuOrder[skusScannedCount]);
  }
  
  /**
   * Set correct sku order for scanning.
   */
  private void setCorrectSkuOrder() {
    String[] skus = orderset.getSkuList();
    for (int i = 0; i < FASCIAS_PER_PALLET; i++) {
      skuOrder[i] = skus[i + FASCIAS_PER_PALLET];
      skuOrder[i + FASCIAS_PER_PALLET] = skus[i];
    }
  }
}
