package com.ttknpdev.backend.repositories.addresses_thailand;

import com.ttknpdev.backend.entities.addresses_thailand.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DistrictRepo extends JpaRepository<District, Long> {

    List<District> findByProvinceName(@Param("provinceName") String provinceName);
}
