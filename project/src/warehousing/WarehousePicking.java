package warehousing;

import java.util.ArrayList;

public class WarehousePicking {
  private static String filepath = "traversal_table.csv";

  /**
   * Based on the String SKUs in List 'skus', return a List of locations, where each location is a
   * String containing 5 pieces of information: the zone character (in the range ['A'..'B']), the
   * aisle number (an integer in the range [0..1]), the rack number (an integer in the range
   * ([0..2]), and the level on the rack (an integer in the range [0..3]), and the SKU number.
   * 
   * @param is the list of SKUs to retrieve.
   * @return the List of locations.
   */
  public static String[] optimize(String[] is) {
    String[] locations = new String[8];
    ArrayList<String> traversalmap = traversalTable();
    String[] tempinfo;
    int indexcounter = 0;
    for (String info : traversalmap) {
      tempinfo = info.split(",");
      for (String sku : is) {
        if (tempinfo[4].equals(sku)) {
          locations[indexcounter] =
              tempinfo[0] + "," + tempinfo[1] + "," + tempinfo[2] + "," + tempinfo[3];
          indexcounter++;
          if (indexcounter >= 9) {
            return locations;
          }
        }
      }
    }
    return locations;
  }

  private static ArrayList<String> traversalTable() {
    ReadFile table = new ReadFile(filepath);
    ArrayList<String> traversal = new ArrayList<String>();
    String nextline = table.readLine();
    while (nextline != null) {
      traversal.add(nextline);
      nextline = table.readLine();
    }
    return traversal;
  }

}
