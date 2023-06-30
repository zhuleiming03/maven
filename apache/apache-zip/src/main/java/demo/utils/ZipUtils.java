package demo.utils;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ZipUtils {

    private final static String CODE_UTF_8 = "utf-8";

    private final static String CODE_GBK = "gbk";

    private final static String MAC_OSX = "__MACOSX";

    private final static String DS_STORE = ".DS_Store";

    public static List<ZipArchiveEntry> getZipArchiveEntryList(byte[] bytes) throws IOException {
        try (ZipArchiveInputStream zipArchiveInputStream = new ZipArchiveInputStream(
                new ByteArrayInputStream(bytes), CODE_UTF_8, true)) {
            return getZipArchiveEntryList(zipArchiveInputStream);
        }
    }

    public static String resetFileName(ZipArchiveEntry entry) throws IOException {

        if (isGbk(entry.getName().getBytes())) {
            return new String(entry.getRawName(), CODE_GBK);
        } else {
            return entry.getName();
        }
    }

    private static List<ZipArchiveEntry> getZipArchiveEntryList(ZipArchiveInputStream inputStream) throws IOException {
        List<ZipArchiveEntry> zipArchiveEntries = new LinkedList<>();
        ZipArchiveEntry entry;
        while ((entry = inputStream.getNextZipEntry()) != null) {
            if (isFile(entry)) {
                zipArchiveEntries.add(entry);
            }
        }
        return zipArchiveEntries;
    }

    private static boolean isFile(ZipArchiveEntry entry) {
        if (entry.isDirectory()) {
            return false;
        }
        if (entry.isUnixSymlink()) {
            return false;
        }
        if (StringUtils.containsIgnoreCase(entry.getName(), MAC_OSX)) {
            return false;
        }
        if (StringUtils.containsIgnoreCase(entry.getName(), DS_STORE)) {
            return false;
        }
        return true;
    }

    /**
     * 如果字节流中的字节全部在合理的编码区内 则返回 true
     * GBK/1: A1A1-A9FE
     * GBK/2: B0A1-F7FE
     * GBK/3: 8140-A0FE
     * GBK/4: AA40-FEA0
     * GBK/5: A840-A9A0
     *
     * @param buffer
     * @return
     */
    private static Boolean isGbk(byte[] buffer) {
        boolean isGbk = true;
        int end = buffer.length;
        for (int i = 0; i < end; i++) {
            byte temp = buffer[i];
            System.out.println(Integer.toHexString(Byte.toUnsignedInt(temp)));
            if ((temp & 0x80) == 0) {
                // GBK/1 A1A1-A9FE
                // GBK/2 B0A1-F7FE
                continue;
            } else if ((Byte.toUnsignedInt(temp) < 0xAA && Byte.toUnsignedInt(temp) > 0xA0)
                    || (Byte.toUnsignedInt(temp) < 0xF8 && Byte.toUnsignedInt(temp) > 0xAF)) {
                if (i + 1 < end) {
                    if (Byte.toUnsignedInt(buffer[i + 1]) < 0xFF && Byte.toUnsignedInt(buffer[i + 1]) > 0xA0
                            && Byte.toUnsignedInt(buffer[i + 1]) != 0x7F) {
                        i = i + 1;
                        continue;
                    }
                } // GBK/3 8140-A0FE
            } else if (Byte.toUnsignedInt(temp) < 0xA1 && Byte.toUnsignedInt(temp) > 0x80) {
                if (i + 1 < end) {
                    if (Byte.toUnsignedInt(buffer[i + 1]) < 0xFF && Byte.toUnsignedInt(buffer[i + 1]) > 0x3F
                            && Byte.toUnsignedInt(buffer[i + 1]) != 0x7F) {
                        i = i + 1;
                        continue;
                    }
                } // GBK/4 AA40-FEA0
                // GBK/5 A840-A9A0
            } else if ((Byte.toUnsignedInt(temp) < 0xFF && Byte.toUnsignedInt(temp) > 0xA9)
                    || (Byte.toUnsignedInt(temp) < 0xAA && Byte.toUnsignedInt(temp) > 0xA7)) {
                if (i + 1 < end) {
                    if (Byte.toUnsignedInt(buffer[i + 1]) < 0xA1 && Byte.toUnsignedInt(buffer[i + 1]) > 0x3F
                            && Byte.toUnsignedInt(buffer[i + 1]) != 0x7F) {
                        i = i + 1;
                        continue;
                    }
                }
            }
            isGbk = false;
            break;
        }
        return isGbk;
    }
}
