package com.yeshtery.pps.service;

import com.yeshtery.pps.data.entity.photo.Photo;
import com.yeshtery.pps.data.entity.photo.PhotoCategory;
import com.yeshtery.pps.data.entity.photo.PhotoStatus;

import java.util.List;

public interface PhotosService {

    Photo get(Integer id);
    Iterable<Photo> getAllPhotos();
    List<Photo> getAllByCategory(List<PhotoCategory> categories);
    List<Photo> getAllByStatus(List<PhotoStatus> status);
    Photo save(String originalFilename, String contentType, byte[] bytes, String category);
    void remove(Integer id);
    Photo updateName(Integer id, String name);
    Photo updateStatus(Integer id, PhotoStatus status);
}
