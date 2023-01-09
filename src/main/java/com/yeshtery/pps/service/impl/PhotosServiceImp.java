package com.yeshtery.pps.service.impl;


import com.yeshtery.pps.data.entity.photo.Photo;
import com.yeshtery.pps.data.entity.photo.PhotoCategory;
import com.yeshtery.pps.data.entity.photo.PhotoStatus;
import com.yeshtery.pps.data.repository.PhotosRepository;
import com.yeshtery.pps.service.PhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhotosServiceImp implements PhotosService {


    private final PhotosRepository photosRepository;

    public PhotosServiceImp(@Autowired PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Iterable<Photo> getAllPhotos() {
        return photosRepository.findAll();
    }

    public Photo get(Integer id) {
        return photosRepository.findById(id).orElse(null);
    }
    @Override
    public List<Photo> getAllByStatus(List<PhotoStatus> status) {
        return new ArrayList<>(photosRepository.findAllByStatusIn(status));
    }

    @Override
    public List<Photo> getAllByCategory(List<PhotoCategory> categories) {
        return new ArrayList<>(photosRepository.findAllByCategoryIn(categories));
    }
    public Photo save(String filename, String contentType, byte[] data, String category) {
        Photo photo = new Photo();
        photo.setContentType(contentType);
        photo.setCategory(PhotoCategory.valueOf(category));
        photo.setFileName(filename);
        photo.setData(data);
        photo.setStatus(PhotoStatus.PENDING);
        photosRepository.save(photo);
        return photo;
    }

    public void remove(Integer id) {
        photosRepository.deleteById(id);
    }

    public Photo updateName(Integer id, String name){
        Photo photo = photosRepository.findById(id).get();
        photo.setFileName(name);
        photosRepository.save(photo);
        return photo;
    }
    public Photo updateStatus(Integer id,PhotoStatus status){
        Photo photo = photosRepository.findById(id).get();
        photo.setStatus(status);
        photosRepository.save(photo);
        return photo;
    }
}
