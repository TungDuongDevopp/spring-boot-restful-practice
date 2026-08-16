package com.duong.springdemoresful.service;

import com.duong.springdemoresful.helper.exception.InvalidTypeFileException;
import com.duong.springdemoresful.helper.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {
    private static final List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
    private static final List<String> allowedMimeTypes = Arrays.asList(
            "application/pdf",
            "image/jpeg",
            "image/png",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );


    @Value("${duong.upload-file.base-uri}")
    private String baseURI;

    public void createUploadFolder(String folder) throws URISyntaxException {
        URI uri = new URI(folder);
        Path path = Paths.get(uri);
        File tmpDir = new File(path.toString());
        if (!tmpDir.isDirectory()) {
            try {
                Files.createDirectory(tmpDir.toPath());
                System.out.printf(">>> CREATE NEW DIRECTORY SUCCESSFUL, PATH = %s%n", tmpDir.toPath());
            } catch (IOException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        } else {
            System.out.println(">>> SKIP MAKING DIRECTORY, ALREADY EXISTS");
        }
    }
    public String store(MultipartFile file, String folder) throws URISyntaxException, IOException {

        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();

        if(file.isEmpty()) {
          throw new StorageException("File is empty, Please upload again!");
      }
        boolean isValidExtension = allowedExtensions.stream().anyMatch(ext -> {
            assert fileName != null;
            return fileName.toLowerCase().endsWith(".%s".formatted(ext));
        });

        if(!isValidExtension) {
            throw new InvalidTypeFileException("Invalid file extension, please upload again!");
        }

        if (!allowedMimeTypes.contains(contentType)) {
            throw new InvalidTypeFileException("Invalid file type, please upload again!");
        }

        // create unique filename
        String finalName = "%d-%s".formatted(System.currentTimeMillis(), file.getOriginalFilename());

        URI uri = new URI("%s%s/%s".formatted(baseURI, folder, finalName));
        Path path = Paths.get(uri);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path,
                    StandardCopyOption.REPLACE_EXISTING);
        }
        return finalName;
    }


}



