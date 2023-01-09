package com.yeshtery.pps.data.entity.photo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeshtery.pps.data.entity.appuser.AppUser;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotEmpty
    private String fileName;

    @Enumerated(EnumType.STRING)
    private PhotoCategory category;

    private String contentType;
    @JsonIgnore
    private byte[] data;

    @ManyToOne
    @JoinColumn (name = "app_user", referencedColumnName = "id")
    private AppUser appUser;
    @Enumerated(EnumType.STRING)
    private PhotoStatus status;

    public Photo() {
    }

    public PhotoStatus getStatus() {
        return status;
    }

    public void setStatus(PhotoStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String filename) {
        this.fileName = filename;
    }

    public PhotoCategory getCategory() {
        return category;
    }

    public void setCategory(PhotoCategory category) {
        this.category = category;
    }

    //raw data

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


}
