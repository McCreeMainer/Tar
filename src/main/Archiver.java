import java.io.*;

public class Archiver {
    public static void main(String[] args) {
        new Archiver().start(args);
    }

    private void start(String[] args) throws IllegalArgumentException {
        if (args[0].equals("-u") && args.length == 2)
            new Tar(new File(System.getProperty("user.dir") + "/" + args[1])).unarchive();
        else if (args[args.length - 2].equals("-out") && args.length > 2) {
            File target = new File(System.getProperty("user.dir") + "/" + args[args.length - 1]);
            for (int i = 0; args[i].equals("-out"); i++) {
                new Tar(new File(System.getProperty("user.dir") + "/" + args[i])).archive(target);
            }
        }
        else throw new IllegalArgumentException();
    }
}