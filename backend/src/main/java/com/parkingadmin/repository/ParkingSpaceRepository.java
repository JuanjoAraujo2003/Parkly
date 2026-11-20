package com.parkingadmin.repository;

import com.parkingadmin.entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parkingadmin.entity.SpaceStatus;

import java.util.List;
import java.util.Optional;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, String> {
    boolean existsByVehicleAndStatus(String vehicle, SpaceStatus status);

    Optional<ParkingSpace> findFirstByStatusOrderByIdAsc(SpaceStatus status);

    Optional<ParkingSpace> findFirstByVehicleAndStatus(String vehicle, SpaceStatus status);

    @Query("select distinct s.zone from ParkingSpace s order by s.zone")
    List<String> findDistinctZones();
}
