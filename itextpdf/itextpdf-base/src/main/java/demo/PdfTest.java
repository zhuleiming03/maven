package demo;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.Date;

public class PdfTest {

    public static void main(String[] args) {
        try {
            //Step 1—Create a Document.
            Document document = new Document();
            //Step 2—Get a PdfWriter demo.
            String tmpPath = System.getProperty("user.dir") + "\\tmp\\";
            String fileName = String.format("createSamplePDF-%s.pdf", new Date().getTime());
            PdfWriter.getInstance(document, new FileOutputStream(tmpPath + fileName));
            //Step 3—Open the Document.
            document.open();
            //Step 4—Add content.
            Font font = FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED, 10f, Font.NORMAL, BaseColor.BLACK);
            document.add(new Paragraph("Hello World 中文", font));
            //Step 5—Close the Document.
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
