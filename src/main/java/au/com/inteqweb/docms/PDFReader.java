package au.com.inteqweb.docms;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PDFReader extends PDFTextStripper {

    static List<String> lines = new ArrayList<String>();
    static int contentIndex;
    Map<String, String> auMap = new HashMap();
    boolean objFlag = false;

    public PDFReader() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        String fileName = "/Users/jaykishanparikh/Professional/Pocs/settld/doc-ms/src/main/resources/static/LINCOLN_CONTRACT.pdf";
        PDFReader reader = new PDFReader();
        reader.processFile(fileName);
    }

    public void processFile(String fileName) throws IOException {
        PDDocument document = null;

        List<String> indexTitleList = new ArrayList<String>();

        try {
            document = PDDocument.load(new File(fileName));
            PDFTextStripper stripper = getPdfTextStripper(document);
            stripper.writeText(document, new OutputStreamWriter(new ByteArrayOutputStream()));

            Object[] linesArray = lines.toArray();
            linesArray[contentIndex] = linesArray[contentIndex].toString().replaceAll("\\d", "").replaceAll("\\.", "").trim();
            for (int i = contentIndex + 1; ; i++) {
                linesArray[i] = linesArray[i].toString().replaceAll("^\\d+\\. |(?:\\d+\\.)?\\d+$", "").replaceAll("\\.", "").trim();
                if (linesArray[contentIndex].equals(linesArray[i])) {
                    break;
                }
                if (!(linesArray[i] == null || linesArray[i].toString().length() < 1 || linesArray[i].toString().contains("\\/"))) {
                    indexTitleList.add(linesArray[i].toString());
                }
            }

            lines.stream().forEach(str -> str = str.toString().replaceAll("^\\d+\\. |(?:\\d+\\.)?\\d+$", "").replaceAll("\\.", "").trim());

            linesArray = lines.toArray();
            for (int i = contentIndex + 1; ; i++) {
                String titleName = "";
                String nextTitleName = "";
                List<String> contentLines = new ArrayList<> ();
                int k = 0;
                for (String title: indexTitleList) {
                   if (title.equals(linesArray[i])) {
                       if(titleName.length() > 0) {
                           if (nextTitleName.length() == 0) {
                               nextTitleName = title;
                           } else {
                               title = nextTitleName;
                               nextTitleName = "";
                           }
                       } else {
                           titleName = title;
                       }
                       break;
                   } else {
                       contentLines.add(linesArray[i].toString());
                   }
                }

            }
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
        lines.add(string);
        if ("CONTENTS".equals(string)) contentIndex = lines.size();
        System.out.println();
    }

    private PDFTextStripper getPdfTextStripper(PDDocument document) throws IOException {
        PDFTextStripper stripper = new PDFReader();
        stripper.setSortByPosition(true);
        stripper.setStartPage(0);
        stripper.setEndPage(document.getNumberOfPages());
        return stripper;
    }
}