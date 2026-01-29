package com.works.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Product extends BaseEntity implements Serializable {

    private String title;
    private String detail;
    private Integer price;

}
