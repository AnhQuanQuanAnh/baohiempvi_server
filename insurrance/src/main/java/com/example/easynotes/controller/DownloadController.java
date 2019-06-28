package com.example.easynotes.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


/**
 * Created by ThuongPham on 23/05/2019.
 */
@CrossOrigin(origins = "http://localhost:3005")
@RestController
@RequestMapping("/api")
public class DownloadController {

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request, HttpServletResponse res) {
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

    @GetMapping("/showFile/{fileName}")
    public ResponseEntity<Resource> showFile(HttpServletRequest request, HttpServletResponse res, @PathVariable("fileName") String fileName) {
        try {
            File file1 = ResourceUtils.getFile("classpath:"+fileName+".pdf");
            res.setContentType("application/pdf");
            res.setHeader("Expires", "0");
            res.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
            res.setHeader("Content-Disposition","inline;filename=" + file1.getName());
            res.setHeader("Accept-Ranges", "bytes");
            FileInputStream fis = new FileInputStream(file1);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ServletOutputStream sos = res.getOutputStream();
            byte[] buffer = new byte[2048];
            while (true) {
                int bytesRead = bis.read(buffer, 0, buffer.length);
                if (bytesRead < 0) {
                    break;
                }
                sos.write(buffer, 0, bytesRead);
                sos.flush();
            }
            sos.flush();
            bis.close();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
