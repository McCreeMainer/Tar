import java.io.*;
import java.util.ArrayList;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.MultiFileOptionHandler;
import org.kohsuke.args4j.spi.FileOptionHandler;

public class Archiver {

    @Option(name = "-out", forbids = "-u", handler = FileOptionHandler.class, usage = "Archive files")
    private File archFile;

    @Option(name = "-u", forbids = "-out", handler = FileOptionHandler.class, usage = "Unarchive file")
    private File unarchFile;

    @Argument(handler = MultiFileOptionHandler.class, usage = "Input list of files")
    private ArrayList<File> fileList;

    public static void main(String[] args) {
        new Archiver().start(args);
    }

    private void start(String[] args) throws IllegalArgumentException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar tar.jar -u unarchFile OR fileList -out archFile");
            parser.printUsage(System.err);
            return;
        }
        Tar tar = new Tar(archFile, unarchFile, fileList);
        tar.tar();
    }
}