package warehousing;

public class Pallet {
  private final int fasciasPerPallet;
  private String[] fascias;
  private ReadFile initial;
  private int fasciaCount;

  /**
   * Pallets are a collection of fascias. The maximum number of fascias contained in a pallet is
   * decided through a config file. Default is 4.
   */
  public Pallet() {
    initial = new ReadFile("warehouseconfig.txt");
    int num = 4;
    String newLine = initial.readLine();
    while (newLine != null) {
      String[] setting = newLine.split("=");
      if (setting[0].equals("palletsize")) {
        num = Integer.parseInt(setting[1]);
      }
      newLine = initial.readLine();
    }
    this.fasciasPerPallet = num;
    fascias = new String[fasciasPerPallet];
    this.fasciaCount = 0;
  }

  /**
   * add a sku of a fascia to the pallet.
   * 
   * @param sku the sku that represent the fascia being added
   */
  public void addFascia(String sku) {
    if (fasciaCount < fasciasPerPallet) {
      fascias[fasciaCount] = sku;
      fasciaCount++;
    } else {
      System.out.println("Pallet is full. Fascia was not added.");
    }
  }

  /**
   * returns true if pallet is full, returns false otherwise.
   */
  public boolean isFull() {
    return fasciaCount == fasciasPerPallet;
  }

  /**
   * returns the fascia at index num on the pallet.
   * 
   * @param num the index of the fascia
   */
  public String getFascia(int num) {
    return fascias[num];
  }

  /**
   * returns an array containing all the fascia loaded on the pallet.
   */
  public String[] getFasciaArray() {
    return fascias;
  }

  /**
   * empties the pallet of all fascias.
   */
  public void clear() {
    for (int i = 0; i < fasciasPerPallet; i++) {
      fascias[i] = "";
    }
    fasciaCount = 0;
  }
}
