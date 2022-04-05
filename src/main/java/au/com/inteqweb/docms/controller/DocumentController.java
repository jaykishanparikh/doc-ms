package au.com.inteqweb.docms.controller;

import au.com.inteqweb.docms.dto.DocumentDto;
import au.com.inteqweb.docms.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.basepath.uri}")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @PostMapping(path = "/document")
    public ResponseEntity<String> processDocument(@RequestBody DocumentDto documentDto) {
        String docText = documentService.processDocument(documentDto);
        return new ResponseEntity<>(docText, HttpStatus.CREATED);
    }

}
