import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Tar {
    private File target;
    private byte[] txt;

    public Tar(File file) {
        try {
            FileInputStream reader = new FileInputStream(file);
            txt = new byte[reader.available()];
            reader.read(txt, 0, reader.available());
            target = file;
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void archive(File output) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(output, true));
            writer.write(target.getName() + target.length() + "#" + new String(txt));
            writer.flush();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void unarchive() {
        String str = new String(txt);
        while(str.length() > 0) {
            String[] data = str.substring(0, str.indexOf('#')).split("(?<=\\.txt)(?=\\d)");
            str = str.substring(str.indexOf('#') + 1);
            try {
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(
                                        target.getParent() + "/" + data[0]));
                writer.write(str.substring(0, Integer.parseInt(data[1])));
                writer.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            if (str.length() > Integer.parseInt(data[1])) {
                str = str.substring(Integer.parseInt(data[1]));
            }
            else break;
        }
    }
}
