package com.egroc.service;

import com.egroc.model.Product;
import com.egroc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;



    String uploadPath = System.getProperty("user.dir")+"/src/main/resources/static/uploads/";


    public String saveFile(MultipartFile file) {
        try {
            if(!file.isEmpty()) {

                Path uploadDir = Paths.get(uploadPath);
                if(!Files.exists(uploadDir)) {//creating new folder if it doesnot exists
                    Files.createDirectories(uploadDir);
                }

                byte[] fileBytes = file.getBytes();
                String filename = file.getOriginalFilename();

                String uniquePath = System.currentTimeMillis()+"_"+filename;

                Path filePath = uploadDir.resolve(uniquePath);

                Files.write(filePath,fileBytes);
                return uniquePath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Resource getFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(uploadPath).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found: " + fileName);
            }
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new RuntimeException("Error while accessing file: " + fileName, e);
        }
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
    public void addProduct(Product product){
        productRepository.save(product);
    }
    public void removeProductById(long id){
        productRepository.deleteById(id);
    }
    public Optional<Product> getProductById(long id){
        return productRepository.findById(id);
    }

    public List<Product> getAllProductsByCategoryId(int id){
        return productRepository.findAllByCategory_Id(id);

    }
}
