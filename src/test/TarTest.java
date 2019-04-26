import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TarTest {
    File abs = new File(".");

    private void assertDirs(String expected, String result) {
        try {
            for (File file: new File(expected).listFiles()) {
                StringBuilder expStr = new StringBuilder();
                BufferedReader readerA = new BufferedReader(new FileReader(file));
                String line;
                while((line = readerA.readLine()) != null) {
                    expStr.append(line);
                }
                StringBuilder resStr = new StringBuilder();
                BufferedReader readerB = new BufferedReader(new FileReader(new File(result + file.getName())));
                while((line = readerB.readLine()) != null) {
                    resStr.append(line);
                }
                assertEquals(expStr.toString(), resStr.toString());
            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void u() {
        new Tar(new File("files/test-u/result/unarch.txt")).unarchive();
        assertDirs("files/test-u/result/", "files/test-u/expected/");
        new File("files/test-u/result/test1.txt").delete();
        new File("files/test-u/result/test2.txt").delete();
        new File("files/test-u/result/sas.txt").delete();

    }

    @org.junit.jupiter.api.Test
    void out() {
        File target = new File("files/test-out/result/output.txt");
        for (File file: new File("files/test-out/result").listFiles()) {
            new Tar(file).archive(target);
        }
        assertDirs("files/test-u/result/", "files/test-u/expected/");
        new File(new File("files/test-out/result/output.txt").getAbsolutePath()).delete();
    }
}