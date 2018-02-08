package warehousing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Provides access to read text files line by line.
 */
public class WriteFile {

  // file Writer variable
  private PrintWriter fileWriter;

  /**
   * Creates new WriteFile. Opens connection to file given file name.
   * 
   * @param fileName is the location of the file to be read.
   */

  public WriteFile(String fileName) {
    try {
      String filePath =
          new File(new File("").getAbsolutePath()).getParent() + File.separator + fileName;
      fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
    } catch (IOException ex) {
      System.out.println("Connection to file not successful");
    }
  }

  /**
   * Add a string line to the file.
   */
  public void writeLine(String line) {
    if (!line.equals("")) {
      fileWriter.println(line);
    }
  }

  /**
   * Close connection to file.
   */
  public void closeFile() {
    fileWriter.close();
  }
}


