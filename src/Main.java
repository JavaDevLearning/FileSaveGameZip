import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void saveGame(GameProgress gameProgress, String directory) {
        try (FileOutputStream progress = new FileOutputStream(directory);
             ObjectOutputStream prog = new ObjectOutputStream(progress)) {
            prog.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipSaveGame(String fileZip, List<String> fileList) {
        for (int i = 0; i < fileList.size(); i++) {
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(fileZip));
                 FileInputStream fis = new FileInputStream(fileList.get(i))) {
                while (i < fileList.size()) {
                    ZipEntry entry = new ZipEntry("packed_save" + i + ".dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    i++;
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }


    public static void main(String[] args) {
        GameProgress saveGames = new GameProgress(120, 20, 20, 4);
        GameProgress saveGames1 = new GameProgress(100, 5, 35, 10);
        GameProgress saveGames2 = new GameProgress(30, 2, 37, 20);

        saveGame(saveGames, "/Users/andreybelkin/Desktop/Games/savegames/save.dat");
        saveGame(saveGames1, "/Users/andreybelkin/Desktop/Games/savegames/save1.dat");
        saveGame(saveGames2, "/Users/andreybelkin/Desktop/Games/savegames/save2.dat");

        List<String> saveFiles = new LinkedList<>();
        saveFiles.add("/Users/andreybelkin/Desktop/Games/savegames/save.dat");
        saveFiles.add("/Users/andreybelkin/Desktop/Games/savegames/save1.dat");
        saveFiles.add("/Users/andreybelkin/Desktop/Games/savegames/save2.dat");

        zipSaveGame("/Users/andreybelkin/Desktop/Games/savegames/saveZip.zip", saveFiles);

    }
}

