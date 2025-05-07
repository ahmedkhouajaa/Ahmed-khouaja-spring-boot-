package com.restapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restapi.model.Car;

@Service
public class CarService {

  static List<Car> cars = new ArrayList<>();
  static long id = 0;

  public List<Car> findAll() {
    return cars;
  }

  public List<Car> findByTitleContaining(String title) {
    return cars.stream()
               .filter(car -> car.getTitle() != null && car.getTitle().contains(title))
               .toList();
  }

  public Car findById(long id) {
    return cars.stream()
               .filter(car -> id == car.getId())
               .findAny()
               .orElse(null);
  }

  public Car save(Car car) {
    if (car.getId() != 0) {
      long _id = car.getId();
      for (int idx = 0; idx < cars.size(); idx++) {
        if (_id == cars.get(idx).getId()) {
          cars.set(idx, car);
          return car;
        }
      }
    }

    car.setId(++id);
    cars.add(car);
    return car;
  }

  public void deleteById(long id) {
    cars.removeIf(car -> id == car.getId());
  }

  public void deleteAll() {
    cars.clear();
  }

  public List<Car> findByDisponible(boolean disponible) {
    return cars.stream()
               .filter(car -> disponible == car.isDisponible())
               .toList();
  }
}
