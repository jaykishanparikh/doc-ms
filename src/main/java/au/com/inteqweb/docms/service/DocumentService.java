package au.com.inteqweb.docms.service;

import au.com.inteqweb.docms.dto.DocumentDto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public class DocumentService {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentService.class);

    @Value("${document.index}")
    private String contents;

    public String processDocument (DocumentDto documentDto) {
        LOG.info(String.format("DocumentService :: processDocument. Input Param [%s]", documentDto.getFilePath()));

        String text = "";
        try {
            // "/Users/jaykishanparikh/Professional/Pocs/settld/doc-ms/src/main/resources/static/LINCOLN_CONTRACT.pdf"
            File pdfFile = (documentDto.getFilePath() != null) ? new File (documentDto.getFilePath()) : fetchDefaultFile();
            text = getText(pdfFile);
            System.out.println("Text in PDF: " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    private String getText(File pdfFile) throws IOException {
        PDDocument doc = PDDocument.load(pdfFile);
        return new PDFTextStripper().getText(doc);
    }

    public File fetchDefaultFile() throws FileNotFoundException {
        return ResourceUtils.getFile("classpath:static/LINCOLN_CONTRACT.pdf");
    }
}
