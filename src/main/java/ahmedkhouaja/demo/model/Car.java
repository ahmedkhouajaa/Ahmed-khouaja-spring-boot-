package com.restapi.model;

public class Car {

  private long id;
  private String title;
  private String model;
  private String make;
  private String essence;
  private boolean disponible;

  public Car() {
  }

  public Car(String title, String model, String make, String essence, boolean disponible) {
    this.title = title;
    this.model = model;
    this.make = make;
    this.essence = essence;
    this.disponible = disponible;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getEssence() {
    return essence;
  }

  public void setEssence(String essence) {
    this.essence = essence;
  }

  public boolean isDisponible() {
    return disponible;
  }

  public void setDisponible(boolean disponible) {
    this.disponible = disponible;
  }

  @Override
  public String toString() {
    return "Car [id=" + id + ", title=" + title + ", model=" + model + ", make=" + make +
           ", essence=" + essence + ", disponible=" + disponible + "]";
  }
}
