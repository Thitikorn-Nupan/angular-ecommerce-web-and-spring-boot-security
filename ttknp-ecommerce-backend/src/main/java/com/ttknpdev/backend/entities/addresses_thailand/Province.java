package com.ttknpdev.backend.entities.addresses_thailand;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "provinces")
@Data
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    private String name;
    // Not on table
    @OneToMany(mappedBy = "province") // mappedBy ="<a property named>"
    @JsonIgnore
    private List<District> districts;
}
