package com.aaronlewis.s3bucketservice;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class S3BucketStorageController {
	
    @Autowired
    S3BucketStorageService service;

    @GetMapping("s3-bucket-service/list")
    public ResponseEntity<List<String>> getListOfFiles() {
        return new ResponseEntity<>(service.listFiles(), HttpStatus.OK);
    }

    @PostMapping("s3-bucket-service/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("fileName") String fileName,
                                             @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(service.uploadFile(fileName, file), HttpStatus.OK);
    }
    
    @GetMapping(value = "s3-bucket-service/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
        ByteArrayOutputStream downloadInputStream = service.downloadFile(filename);

        return ResponseEntity.ok()
                .contentType(contentType(filename))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(downloadInputStream.toByteArray());
    }


    private MediaType contentType(String filename) {
        String[] fileArrSplit = filename.split("\\.");
        String fileExtension = fileArrSplit[fileArrSplit.length - 1];
        switch (fileExtension) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "pdf":
            	return MediaType.APPLICATION_PDF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
    
    @GetMapping(value = "s3-bucket-service/delete/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable("filename") String filename) {
        return new ResponseEntity<>(service.deleteFile(filename), HttpStatus.OK);
    }
}
