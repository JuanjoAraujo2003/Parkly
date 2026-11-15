package com.parkingadmin.parking;

public record ParkingSpace(
        String id,
        String zone,
        SpaceStatus status,
        String vehicle,
        String since
) {
}
