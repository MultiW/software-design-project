package warehousetesting;

import static org.junit.Assert.*;

import org.junit.Test;

import warehousing.ReadFile;
import warehousing.WriteFile;

public class ReadWritefileTest {

  @Test
  public void testReadWriteFile() {
    WriteFile write = new WriteFile("ReadWriteTest.txt");
    write.writeLine("Hello World");
    write.writeLine("So Long World");
    write.closeFile();

    ReadFile read = new ReadFile("ReadWriteTest.txt");
    String line1 = read.readLine();
    String line2 = read.readLine();
    assertEquals("Hello World", line1);
    assertTrue(line2.equals("So Long World"));
    read.closeFile();
  }


}
