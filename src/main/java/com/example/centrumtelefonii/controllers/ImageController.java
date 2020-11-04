package com.example.centrumtelefonii.controllers;


import com.example.centrumtelefonii.dao.ImageRepo;
import com.example.centrumtelefonii.dao.StorageService;
import com.example.centrumtelefonii.models.Image;
import com.example.centrumtelefonii.payload.ApiResponse;
import com.example.centrumtelefonii.services.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
public class ImageController {

    ImageRepo imageRepo;
    StorageService storageService;

    @Autowired
    public ImageController(ImageRepo imageRepo, StorageService storageService) {
        this.imageRepo = imageRepo;
        this.storageService = storageService;
    }

    @GetMapping("/zdjecia")
    public List<Image> getImages() {
        return imageRepo.findAll();
    }

    @GetMapping("/zdjecia/{id}")
    public Optional<Image> getImage(@PathVariable("id") int id) {
        return imageRepo.findById(id);
    }

    @PutMapping("/zdjecie")
    public Image updateImage(@RequestPart("webpFile") MultipartFile webpFile, @RequestPart("pngFile") MultipartFile pngFile,
                             @RequestPart("pngThumbnailFile") MultipartFile pngThumbnailFile,
                             @RequestPart("alt") String alt, @RequestPart("id") String id) {
        Image image = imageRepo.getOne(Integer.parseInt(id));
        String webpFilename = image.getWebpUrl().split("/")[image.getWebpUrl().split("/").length - 1];
        String pngFilename = image.getPngUrl().split("/")[image.getPngUrl().split("/").length - 1];
        String pngThumbnailname = image.getPngThumbnailUrl().split("/")[image.getPngThumbnailUrl().split("/").length - 1];
        storageService.delete(webpFilename);
        storageService.delete(pngFilename);
        storageService.delete(pngThumbnailname);
        storageService.store(webpFile);
        storageService.store(pngFile);
        storageService.store(pngThumbnailFile);
        image.setWebpUrl("http://centrum-telefonii.pl:25081/static/" + webpFile.getOriginalFilename());
        image.setPngUrl("http://centrum-telefonii.pl:25081/static/" + pngFile.getOriginalFilename());
        image.setPngThumbnailUrl("http://centrum-telefonii.pl:25081/static/" + pngThumbnailFile.getOriginalFilename());
        image.setAlt(alt);
        imageRepo.save(image);
        return image;
    }

    @PutMapping("zdjecie/title")
    public Image updateImageTitle(@RequestBody Image image) {
        imageRepo.save(image);
        return image;
    }

    @DeleteMapping("/zdjecia/{id}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable("id") int id) {
        Image image = imageRepo.getOne(id);
        String webpFilename = image.getWebpUrl().split("/")[image.getWebpUrl().split("/").length - 1];
        String pngFilename = image.getPngUrl().split("/")[image.getPngUrl().split("/").length - 1];
        String pngThumbnailname = image.getPngThumbnailUrl().split("/")[image.getPngThumbnailUrl().split("/").length - 1];
        storageService.delete(webpFilename);
        storageService.delete(pngFilename);
        storageService.delete(pngThumbnailname);
        imageRepo.delete(image);
        return ResponseEntity.ok(new ApiResponse(true, "Poprawnie usunięto zdjęcie"));
    }

    @DeleteMapping("/zdjecia")
    public ResponseEntity<ApiResponse> deleteImages() {
        imageRepo.deleteAll();
        storageService.deleteAll();
        return ResponseEntity.ok(new ApiResponse(true, "Poprawnie usunięto wszystkie zdjęcia"));
    }

    @PostMapping("/zdjecie")
    public Image addImage(@RequestPart("webpFile") MultipartFile webpFile, @RequestPart("pngFile") MultipartFile pngFile,
                           @RequestPart("pngThumbnailFile") MultipartFile pngThumbnailFile,
                           @RequestPart("alt") String alt) {
        Image image = new Image();
        storageService.store(webpFile);
        storageService.store(pngFile);
        storageService.store(pngThumbnailFile);
        image.setWebpUrl("http://centrum-telefonii.pl:25081/static/" + webpFile.getOriginalFilename());
        image.setPngUrl("http://centrum-telefonii.pl:25081/static/" + pngFile.getOriginalFilename());
        image.setPngThumbnailUrl("http://centrum-telefonii.pl:25081/static/" + pngThumbnailFile.getOriginalFilename());
        image.setAlt(alt);
        imageRepo.save(image);
        return image;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
