package org.example.datn.impl;

import org.example.datn.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Service
public class UploadImpl implements UploadService {

    @Value("${file.path}")
    private String filePath;

    @Override
    public void save(MultipartFile file){
        String dir = System.getProperty("user.dir") + "/" + filePath;
        try {
            file.transferTo(new File(dir + "/" +file.getOriginalFilename()));
        }catch (IOException E){
            System.out.println(E.getMessage());
        }
    }
}
