package com.example.easynotes.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


/**
 * Created by ThuongPham on 23/05/2019.
 */
@RestController
@RequestMapping("/api")
public class DownloadController {

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request) {
        // Load file as Resource
        Resource resource;
        String contentType;
        try {
            File file = ResourceUtils.getFile("classpath:BAOHIEM.doc");
            resource = new UrlResource(file.toURI());
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
