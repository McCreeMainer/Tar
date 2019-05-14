import java.io.*;
import java.util.ArrayList;

public class Tar {
    private int operator = 0;
    private File target;
    private ArrayList<File> list;

    public Tar(File archFile, File unarchFile, ArrayList<File> fileList) {
        if ((target = archFile) != null) {
            operator = 1;
            list = fileList;
        }
        else if ((target = unarchFile) != null) {
            operator = 2;
        }
    }

    public void tar() {
        switch(operator) {
            case 1: this.archive();
            break;
            case 2: this.unarchive();
            break;
            default: throw new IllegalArgumentException();
        }
    }

    private void archive() {
        try {
            FileInputStream reader;
            BufferedWriter writer = new BufferedWriter(new FileWriter(target, true));
            for (File s : list) {
                reader = new FileInputStream(s);
                byte[] txt = new byte[reader.available()];
                writer.write(s + "#" + s.length() + "#");
                while(reader.read(txt, 0, txt.length) > 0) {
                    writer.write(new String(txt));
                }
            }
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void unarchive() {
        try {
            FileInputStream reader = new FileInputStream(target);
            BufferedWriter writer;
            byte[] txt = new byte[reader.available()];
            File output = null;
            int size = 0;
            String prev = "";
            while (reader.read(txt, 0, txt.length) > 0) {
                String str = new String(txt);
                int position = 0;
                int sharp;
                while ((sharp = str.indexOf('#', position)) != -1) {
                    String result = "";
                    if (prev != "") {
                        result = prev;
                        prev = "";
                    }
                    if (output == null) {
                        result += str.substring(position, sharp);
                        output = new File(result);
                        position = sharp + 1;
                    }
                    else if (size == 0) {
                        result += str.substring(position, sharp);
                        size = Integer.parseInt(result);
                        position = sharp + 1;
                    }
                    if (size != 0) {
                        writer = new BufferedWriter(new FileWriter(output, false));
                        int sas = position + size;
                        if (sas >= str.length()) sas = str.length();
                        result = str.substring(position, sas);
                        writer.write(result);
                        position += size;
                        size -= result.length();
                        if (size == 0) {
                            writer.close();
                            output = null;
                        }
                    }
                }
                if (position < str.length()) prev = str.substring(position);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
