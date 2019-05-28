import static org.junit.jupiter.api.Assertions.*;

import Tar.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class TarTest {
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
        File archFile = null;
        File unarchFile = new File("files/test-u/result/unarch.txt");
        ArrayList<File> fileList = null;
        try {
            new Tar(archFile, unarchFile, fileList).tar();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertDirs("files/test-u/expected/", "files/test-u/result/");
        new File("files/test-u/result/test1.txt").delete();
        new File("files/test-u/result/test2.txt").delete();
        new File("files/test-u/result/sas.txt").delete();
    }

    @org.junit.jupiter.api.Test
    void out() {
        File archFile = new File("files/test-out/result/output.txt");
        File unarchFile = null;
        ArrayList<File> fileList = new ArrayList<File>() {
            {
                add(new File("files/test-out/result/test1.txt"));
                add(new File("files/test-out/result/test2.txt"));
                add(new File("files/test-out/result/sas.txt"));
            }
        };
        if (archFile.exists()) archFile.delete();
        try {
            new Tar(archFile, unarchFile, fileList).tar();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertDirs("files/test-out/expected/", "files/test-out/result/");
        new File("files/test-out/result/output.txt").delete();
    }
}