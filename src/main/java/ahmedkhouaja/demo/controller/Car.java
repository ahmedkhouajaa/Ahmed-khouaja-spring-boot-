package com.restapi.controller;

import java.util.ArrayList;
import java.util.List;

import com.restapi.model.Car;
import com.restapi.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4000")
@RestController
@RequestMapping("/api")
public class CarController {

  @Autowired
  CarService carService;

  @GetMapping("/cars")
  public ResponseEntity<List<Car>> getAllCars(@RequestParam(required = false) String title) {
    try {
      List<Car> cars = new ArrayList<>();

      if (title == null)
        carService.findAll().forEach(cars::add);
      else
        carService.findByTitleContaining(title).forEach(cars::add);

      if (cars.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(cars, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/cars/{id}")
  public ResponseEntity<Car> getCarById(@PathVariable("id") long id) {
    Car car = carService.findById(id);

    if (car != null) {
      return new ResponseEntity<>(car, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/cars")
  public ResponseEntity<Car> createCar(@RequestBody Car car) {
    try {
      Car _car = carService.save(new Car(
          car.getTitle(),
          car.getModel(),
          car.getMake(),
          car.getEssence(),
          false));
      return new ResponseEntity<>(_car, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/cars/{id}")
  public ResponseEntity<Car> updateCar(@PathVariable("id") long id, @RequestBody Car car) {
    Car _car = carService.findById(id);

    if (_car != null) {
      _car.setTitle(car.getTitle());
      _car.setModel(car.getModel());
      _car.setMake(car.getMake());
      _car.setEssence(car.getEssence());
      _car.setDisponible(car.isDisponible());
      return new ResponseEntity<>(carService.save(_car), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/cars/{id}")
  public ResponseEntity<HttpStatus> deleteCar(@PathVariable("id") long id) {
    try {
      carService.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/cars")
  public ResponseEntity<HttpStatus> deleteAllCars() {
    try {
      carService.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/cars/disponible")
  public ResponseEntity<List<Car>> findByDisponible() {
    try {
      List<Car> cars = carService.findByDisponible(true);

      if (cars.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(cars, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
