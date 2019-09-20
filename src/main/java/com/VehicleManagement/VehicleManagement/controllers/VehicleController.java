package com.VehicleManagement.VehicleManagement.controllers;

import com.VehicleManagement.VehicleManagement.entities.Vehicle;
import com.VehicleManagement.VehicleManagement.repositories.VehicleRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    VehicleRepository vehicleRepository;

    @GetMapping("/get/{id}")
    ResponseEntity<Object> retrieveVehicle(@PathVariable(value="id") Long id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        return ResponseEntity.ok(vehicle.get());
    }

    @PostMapping("/save")
    ResponseEntity<Object> saveVehicles(@RequestBody Vehicle vehicle){
        try {
            vehicleRepository.save(vehicle);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update")
    ResponseEntity<Object> updateVehicles(@RequestBody Vehicle vehicle){
        try {
            vehicleRepository.save(vehicle);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Object> deleteVehicles(@PathVariable(value="id") Long id){
        try{
            Optional<Vehicle> vehicle = vehicleRepository.findById(id);
            vehicleRepository.delete(vehicle.get());
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    ResponseEntity<Object> uploadFileHandler(@RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String xmlFile = new String(bytes);

                XmlMapper xmlMapper = new XmlMapper();
                List<Vehicle> vehicles = xmlMapper.readValue(xmlFile, new TypeReference<List<Vehicle>>() {});

                vehicleRepository.saveAll(vehicles);
                return ResponseEntity.status(HttpStatus.CREATED).body(null);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You failed to upload  because the file was empty.");
        }
    }
}
