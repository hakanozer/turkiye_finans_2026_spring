package com.works.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Product extends BaseEntity implements Serializable {

    private String title;
    private String detail;
    private Integer price;

    // Entity DB’ye ilk kez kaydedilmeden önce
    @PrePersist
    public void onCreate() {

    }

    // Entity DB’ye kaydedildikten sonra
    @PostPersist
    public void afterCreate() {

    }

    // Entity güncellenmeden önce
    @PreUpdate
    public void onUpdate() {

    }

    // Entity silindikten sonra
    @PostRemove
    public void afterDelete() {

    }


}
