package warehousing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Provides access to read text files line by line.
 */
public class ReadFile {

  // file reader variable
  private BufferedReader fileReader;

  /**
   * Create new ReadFile. Opens connection to file.
   * 
   * @param fileName is the location of the file to be read.
   */
  public ReadFile(String fileName) {
    try {
      String filePath =
          new File(new File("").getAbsolutePath()).getParent() + File.separator + fileName;
      fileReader = new BufferedReader(new FileReader(filePath));
    } catch (FileNotFoundException ex) {
      System.out.println("File Not Found: " + fileName);
    }
  }

  /**
   * Reads the current line of the file.
   * 
   * @return current line of the file.
   */
  public String readLine() {
    try {
      return fileReader.readLine();
    } catch (IOException ex) {
      System.out.println("Read line failed.");
      return null;
    }
  }

  /**
   * Closes connection to file.
   */
  public void closeFile() {
    try {
      fileReader.close();
    } catch (IOException ex) {
      System.out.println("File close failed.");
    }
  }
}
