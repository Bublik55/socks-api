package com.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Entity
@Table(name = "socks")
public class Sock implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "colors_id")
  private Color color;


  @Column(name = "quantity")
  private int quantity;

  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "cotton_parts_id")
  private CottonPart cottonPart;


  public Sock() {
  }


  public Sock(Color color, int quantity, CottonPart cottonPart) {
    this.color = color;
    this.quantity = quantity;
    this.cottonPart = cottonPart;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public CottonPart getCottonPart() {
    return cottonPart;
  }

  public void setCottonPart(CottonPart cottonPart) {
    this.cottonPart = cottonPart;
  }



  public void incomeSocks(int quantity) {
    this.quantity += quantity;
  }

  public void outcomeSocks(int quantity) {
    this.quantity -= quantity;
  }

}
