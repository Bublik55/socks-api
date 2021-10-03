package com.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cotton_parts")
public class CottonPart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "cotton_part")
    private int cottonPart;

    @OneToMany(mappedBy = "cottonPart", cascade = CascadeType.ALL)
    private Set<Sock> socks;
    public long getId() {
        return id;
    }

    public CottonPart() {
    }
    public CottonPart(int cottonPart) {
        this.cottonPart = cottonPart;
    }

    public CottonPart(int cottonPart, Set<Sock> socks) {
        this.cottonPart = cottonPart;
        this.socks = socks;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCottonPart() {
        return cottonPart;
    }

    public void setCottonPart(int cottonPart) {
        this.cottonPart = cottonPart;
    }

    public Set<Sock> getSocks() {
        return socks;
    }

    public void setSocks(Set<Sock> socks) {
        this.socks = socks;
    }
}
