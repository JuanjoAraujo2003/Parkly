package com.parkingadmin.repository;

import com.parkingadmin.entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, String> {
}
