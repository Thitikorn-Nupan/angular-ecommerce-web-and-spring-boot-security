package com.ttknpdev.backend.controllers.addresses_thailand;

import com.ttknpdev.backend.entities.addresses_thailand.District;
import com.ttknpdev.backend.services.addresses_thailand.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/district")
public class DistrictControl {
    private DistrictService districtService;
    @Autowired
    public DistrictControl(DistrictService districtService) {
        this.districtService = districtService;
    }
    @GetMapping(value = "/{provinceName}")
    public ResponseEntity<List<District>> reads(@PathVariable("provinceName") String provinceName) {
        return ResponseEntity
                .accepted()
                .body(this.districtService.getAllDistrictsByProvinceName(provinceName));
    }
}
