package com.org.queenstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private double price;
    private int available_quantity;

    @ManyToOne
    @JsonIgnoreProperties("product")
    private User user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("product")
    private List<Purschase> purschase;

    @ManyToOne
    @JsonIgnoreProperties("product")
    private Tag tag;


    public Product(Long id, String name, double price, int available_quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available_quantity = available_quantity;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Purschase> getPurschase() {
        return purschase;
    }

    public void setPurschase(List<Purschase> purschase) {
        this.purschase = purschase;
    }
}
