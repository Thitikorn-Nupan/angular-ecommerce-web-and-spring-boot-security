package com.ttknpdev.backend.entities.addresses_thailand;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "districts")
@Data
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long did;
    private String name;
    @ManyToOne
    @JoinColumn(name = "pid")
    @JsonIgnore
    private Province province;
}
