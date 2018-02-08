package warehousing;

import java.util.HashMap;

/**
 * Provides access to the data of fascia types and their corresponding SKUs.
 */
public class SkuTranslation {

  /** Key is a string "model,colour" that maps to value "sku". */
  private HashMap<String, String> fasciaDetailsMap;

  /**
   * Constructor for the SKU translation table.
   */
  public SkuTranslation() {
    initializeMaps("translation.csv");
  }

  /**
   * Read data from translation table text file and store into array and HashMaps.
   * 
   * @param fileName is the path of the translation table text file.
   */
  private void initializeMaps(String fileName) {
    fasciaDetailsMap = new HashMap<String, String>();

    // Read from file and store data
    ReadFile fileReader = new ReadFile(fileName);
    // skip first line
    fileReader.readLine();

    // read each line of translation.csv file
    String line = fileReader.readLine();
    while (line != null) {
      // store data to arrays and HashMaps
      String[] lineSplit = line.split(",");
      storeTranslationData(lineSplit);
      // next line
      line = fileReader.readLine();
    }

    fileReader.closeFile();
  }

  /**
   * Helper method stores data of one fascia (one line) into arrays and HashMaps.
   * 
   * @param lineSplit array of values in one line of the translation.csv file.
   */
  private void storeTranslationData(String[] lineSplit) {
    // helper variables
    String colour = lineSplit[0];
    String model = lineSplit[1];
    String frontSku = lineSplit[2];
    String backSku = lineSplit[3];

    fasciaDetailsMap.put(model + "," + colour, frontSku + "," + backSku);
  }

  /**
   * Returns the front fascia given fascia colour and model.
   * 
   * @param model the model of the fascia
   * @param colour the colour of the fascia
   * @return SKU
   */
  public String getFrontSku(String model, String colour) {
    return fasciaDetailsMap.get(model + "," + colour).split(",")[0];
  }

  /**
   * Returns the back fascia given fascia colour and model.
   * 
   * @param model the model of the fasica
   * @param colour the colour of the fascia
   * @return SKU
   */
  public String getBackSku(String model, String colour) {
    return fasciaDetailsMap.get(model + "," + colour).split(",")[1];
  }
}
