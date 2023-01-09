package com.yeshtery.pps.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yeshtery.pps.data.entity.photo.PhotoCategory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PhotoDto {
    @NotEmpty
    private String fileName;

    @Enumerated(EnumType.STRING)
    private String category;

    private String contentType;
    @JsonIgnore
    private byte[] data;

}
