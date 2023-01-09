package com.yeshtery.pps.data.repository;


import com.yeshtery.pps.data.entity.photo.Photo;
import com.yeshtery.pps.data.entity.photo.PhotoCategory;
import com.yeshtery.pps.data.entity.photo.PhotoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotosRepository extends JpaRepository<Photo, Integer> {

    List<Photo> findAllByStatusIn(List<PhotoStatus> status);

    List<Photo> findAllByCategoryIn(List<PhotoCategory> categories);
}
