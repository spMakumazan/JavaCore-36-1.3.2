import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(94, 10, 2, 254.32);
        GameProgress gameProgress2 = new GameProgress(98, 11, 4, 574.74);
        GameProgress gameProgress3 = new GameProgress(75, 15, 9, 822.22);

        saveGame("Games/savegames/save1.dat", gameProgress1);
        saveGame("Games/savegames/save2.dat", gameProgress2);
        saveGame("Games/savegames/save3.dat", gameProgress3);

        String[] fileNames = {"Games/savegames/save1.dat",
                "Games/savegames/save2.dat",
                "Games/savegames/save3.dat"};

        zipFiles("Games/savegames/zip.zip", fileNames);

        File savegames = new File("Games/savegames");
        for (File file : savegames.listFiles()) {
            if (!file.getPath().endsWith(".zip")) {
                file.delete();
            }
        }
    }

    public static void saveGame(String fileName, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipName, String[] fileNames) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipName))) {
            ZipEntry entry;
            for (String fileName : fileNames) {
                try (FileInputStream fis = new FileInputStream(fileName)) {
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    String[] fileNameArray = fileName.split("/");
                    entry = new ZipEntry(fileNameArray[fileNameArray.length - 1]);
                    zout.putNextEntry(entry);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}