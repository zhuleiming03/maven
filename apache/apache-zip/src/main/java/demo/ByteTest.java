package demo;

import org.apache.commons.lang3.StringEscapeUtils;
import demo.utils.ByteUtils;

public class ByteTest {

    public static void main(String[] args) {

        byte[] pro = ByteUtils.hexToByte("E59FBAE7A180E4BB98E6ACBEE697A52DE59FBAE7A180E4BB98E6ACBEE697A5E7B1BBE59E8B202031EFBC9AE68C87E5AE9AE697A5E69C9F3B32EFBC9AE8AEA1E7AE97E697A5E69C9F");
        byte[] uat = ByteUtils.hexToByte("E59FBAE7A180E4BB98E6ACBEE697A52DE59FBAE7A180E4BB98E6ACBEE697A5E7B1BBE59E8B202031EFBC9AE68C87E5AE9AE697A5E69C9F3B32EFBC9AE8AEA1E7AE97E697A5E69C9F");

        String proStr = StringEscapeUtils.unescapeJava(new String(pro));
        String uatStr = StringEscapeUtils.unescapeJava(new String(uat));

        System.out.println(proStr);
        System.out.println(uatStr);
    }
}
