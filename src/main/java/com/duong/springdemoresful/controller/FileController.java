package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.dto.response.FileResponse;
import com.duong.springdemoresful.helper.ApiResponse;
import com.duong.springdemoresful.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    @Value("${duong.upload-file.base-uri}")
    private String baseURI;

    @PostMapping("/files")
    public ResponseEntity<ApiResponse<FileResponse>> upload(@RequestParam("file") MultipartFile file,
                                                            @RequestParam("folder") String folder) throws URISyntaxException, IOException {
        fileService.createUploadFolder(baseURI + folder);
       String uploadedFile =  fileService.store(file,folder);
        return ApiResponse.success(new FileResponse(uploadedFile, Instant.now()),"success");
    }

    @GetMapping("/files")
    public ResponseEntity<Resource> downLoad(@RequestParam("fileName") String fileName,
                                             @RequestParam("folder") String folder) throws URISyntaxException, IOException {
        InputStreamResource resource = fileService.getFile(fileName,folder);
        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"%s\"".formatted(fileName))
                .contentLength(fileService.getFileLength(fileName,folder))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
