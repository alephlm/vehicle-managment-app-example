package com.VehicleManagement.VehicleManagement.repositories;

import com.VehicleManagement.VehicleManagement.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
