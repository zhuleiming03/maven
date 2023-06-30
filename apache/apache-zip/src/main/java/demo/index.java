package demo;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import demo.utils.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class index {

    public static void main(String[] args) {
        try {
            String filePath = String.format("%s\\src\\main\\resources\\发票02446432.zip",
                    new File("").getCanonicalPath());
            File file = new File(filePath);
            try (InputStream in = Files.newInputStream(file.toPath())) {
                byte[] bytes = IOUtils.toByteArray(in);
                List<ZipArchiveEntry> zipArchiveEntryList = ZipUtils.getZipArchiveEntryList(bytes);
                for (ZipArchiveEntry zipArchiveEntry : zipArchiveEntryList) {
                    String fileName = ZipUtils.resetFileName(zipArchiveEntry);
                    System.out.println(fileName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
