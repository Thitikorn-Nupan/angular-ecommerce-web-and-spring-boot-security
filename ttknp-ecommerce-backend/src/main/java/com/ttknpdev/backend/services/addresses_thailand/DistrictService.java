package com.ttknpdev.backend.services.addresses_thailand;

import com.ttknpdev.backend.entities.addresses_thailand.District;
import com.ttknpdev.backend.repositories.addresses_thailand.DistrictRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {
    private DistrictRepo districtRepo;

    @Autowired
    public DistrictService(DistrictRepo districtRepo) {
        this.districtRepo = districtRepo;
    }

    public List<District> getAllDistrictsByProvinceName(String provinceName) {
        return districtRepo.findByProvinceName(provinceName);
    }

}
