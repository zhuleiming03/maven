package demo.utils;

public class ByteUtils {

    private final static String HEX_NUMS_STR = "0123456789ABCDEF";

    public static byte[] hexToByte(String hex) {
        hex = hex.toUpperCase();
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    public static String byteToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte value : bytes) {
            String hex = Integer.toHexString(value & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            result.append(hex);
        }
        return result.toString();
    }
}
