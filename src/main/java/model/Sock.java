package model;

import javax.persistence.*;

@Entity
@Table(name = "socks")
public class Sock {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "color")
  private String color;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "cotton_part")
  private int cottonPart;

  public Sock() {
  }

  public Sock(String color, int quantity, int cottonPart) {
    this.setColor(color);
    this.setQuantity(quantity);
    this.setCottonPart(cottonPart);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void incomeSocks(int quantity) {
    this.quantity += quantity;
  }

  public void outcomeSocks(int quantity) {
    this.quantity -= quantity;
  }

  public int getCottonPart() {
    return cottonPart;
  }

  public void setCottonPart(int cottonPart) {
    this.cottonPart = cottonPart;
  }

}
