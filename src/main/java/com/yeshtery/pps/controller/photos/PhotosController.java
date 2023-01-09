package com.yeshtery.pps.controller.photos;


import com.yeshtery.pps.data.entity.appuser.AppUser;
import com.yeshtery.pps.data.entity.photo.Photo;
import com.yeshtery.pps.data.entity.photo.PhotoCategory;
import com.yeshtery.pps.data.entity.photo.PhotoStatus;
import com.yeshtery.pps.service.PhotosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
public class PhotosController {


    private final PhotosService photosService;

    public PhotosController(@Autowired PhotosService photosService) {
        this.photosService = photosService;
    }

    @GetMapping("/api/v1/photos/")
    public Iterable<Photo> getAllPhotos() {
        return photosService.getAllPhotos();
    }

    @GetMapping("/api/v1/photos/status")
    public List<Photo> getAllPhotosByStatus(@RequestParam PhotoStatus status) {
        return photosService.getAllByStatus(Collections.singletonList(status));
    }

    @GetMapping("/api/v1/photos/category")
    public List<Photo> getAllPhotosByCategory(@RequestParam PhotoCategory category) {
        return photosService.getAllByCategory(Collections.singletonList(category));
    }


    @PostMapping("/api/v1/photos/upload")
    public RedirectView create(@RequestParam("image") MultipartFile file, @RequestParam("category") String category, Model model) throws IOException {
        String message = "";
        String fileName = null;
        if (file.getOriginalFilename() != null) {
            fileName = StringUtils.cleanPath(file.getOriginalFilename());
            log.info("{}:{}", getClass().getSimpleName(), fileName);
        }
        try {

            photosService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes(), category);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            model.addAttribute("success-message", message);
        }catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename()+  ". Error: " + e.getMessage();;
            model.addAttribute("error-message", message);
        }

        return new RedirectView("/home",true);
    }
/*
    @PostMapping("/api/v1/photos/uploadJSON")
    public Photo create(@RequestPart("image") MultipartFile file,@RequestPart("category") String category) throws IOException {
        return photosService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes(), category);
    }
*/

    @GetMapping("/api/v1/photos/{id}")
    public Photo photos(@PathVariable Integer id) {
        Photo photo = photosService.get(id);
        if (photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return photo;
    }

    @DeleteMapping("/api/v1/photos/{id}")
    public void delete(@PathVariable Integer id) {
        photosService.remove(id);
    }

 /*   @PutMapping("/api/v1/photos/{id}")
    public Photo updatePhotoName(@PathVariable Integer id, @RequestPart("name") String name) throws IOException {
        return photosService.updateName(id, name);
    }*/

    @PutMapping("/api/v1/photos/{id}")
    public Photo updatePhotoStatus(@PathVariable Integer id, @RequestPart("status") @NotNull String status) throws IOException {


        if (status.equalsIgnoreCase(PhotoStatus.APPROVED.toString())) {
            return photosService.updateStatus(id, PhotoStatus.APPROVED);
        } else if (status.equalsIgnoreCase(PhotoStatus.PENDING.toString())) {
            return photosService.updateStatus(id, PhotoStatus.PENDING);
        } else if (status.equalsIgnoreCase(PhotoStatus.DECLINED.toString())) {
            return photosService.updateStatus(id, PhotoStatus.DECLINED);
        } else {
            return photosService.updateStatus(id, PhotoStatus.DELETED);
        }

    }
}
