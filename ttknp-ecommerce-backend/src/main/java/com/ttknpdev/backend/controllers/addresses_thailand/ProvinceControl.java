package com.ttknpdev.backend.controllers.addresses_thailand;

import com.ttknpdev.backend.entities.addresses_thailand.Province;
import com.ttknpdev.backend.services.addresses_thailand.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/province")
public class ProvinceControl {

    private final ProvinceService provinceService;

    @Autowired
    public ProvinceControl(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Province>> reads() {
        return ResponseEntity
                .accepted()
                .body(this.provinceService.getAllProvinces());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Province>> read(@PathVariable long id) {
        return ResponseEntity
                .accepted()
                .body(this.provinceService.getProvinceById(id));
    }

}
