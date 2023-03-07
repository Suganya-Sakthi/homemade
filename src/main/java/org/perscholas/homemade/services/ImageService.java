package org.perscholas.homemade.services;

import lombok.extern.slf4j.Slf4j;
import org.perscholas.homemade.controllers.ImageController;
import org.perscholas.homemade.dao.ChefRepoI;
import org.perscholas.homemade.dao.ImageRepoI;
import org.perscholas.homemade.dao.ProductRepoI;
import org.perscholas.homemade.models.Image;
import org.perscholas.homemade.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
@Slf4j
public class ImageService {
    private final ChefRepoI chefRepoI;
    private final Path root = Paths.get("./uploads");


    ProductRepoI productRepoI;
    ProductService productService;
    ImageRepoI imageRepoI;


    @Autowired
    public ImageService(ProductRepoI productRepoI, ProductService productService, ImageRepoI imageRepoI,
                        ChefRepoI chefRepoI) {
        this.productRepoI = productRepoI;
        this.productService = productService;
        this.imageRepoI = imageRepoI;
        this.chefRepoI = chefRepoI;
    }



    public void init() throws Exception {
        try {
            if(Files.exists(root)){
                log.debug("Folder Exists!");
            }else {
                Files.createDirectories(root);
                log.debug("Folder Created!");
            }
        } catch (IOException e) {
            throw new Exception("Could not initialize folder for upload!");
        }
    }


    public void save(MultipartFile file, Product product) throws Exception {
        try {

            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            log.warn(ext);
            String imageName=product.getId().toString().concat("-").concat(product.getName().replaceAll("\\s","_")).concat(String.valueOf(LocalDate.now().getYear())).concat(ext);
            log.warn(imageName);
            Files.copy(file.getInputStream(), this.root.resolve(imageName));
            Path p = root.resolve(imageName);
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ImageController.class, "getImage", p.getFileName().toString()).build().toString();
            log.warn(url);

            imageRepoI.save(new Image(imageName,url,product));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new Exception("A file of that name already exists.");
            } else {
                throw new Exception("Error copying file to HD" + file.getOriginalFilename());
            }

        }
    }



    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


}
