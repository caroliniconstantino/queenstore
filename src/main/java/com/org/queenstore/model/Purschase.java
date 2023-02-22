package com.org.queenstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.org.queenstore.enums.MethodPayment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_purschase")
public class Purschase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private MethodPayment methodPayment;

    @NotNull
    private int quantity_purchase;

    @ManyToOne
    @JsonIgnoreProperties("purschase")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("purschase")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("product")
    private Tag tag;

    public Purschase(Long id, MethodPayment methodPayment, int quantity_purchase) {
        this.id = id;
        this.methodPayment = methodPayment;
        this.quantity_purchase = quantity_purchase;
    }

    public Purschase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MethodPayment getMethodPayment() {
        return methodPayment;
    }

    public void setMethodPayment(MethodPayment methodPayment) {
        this.methodPayment = methodPayment;
    }

    public int getQuantity_purchase() {
        return quantity_purchase;
    }

    public void setQuantity_purchase(int quantity_purchase) {
        this.quantity_purchase = quantity_purchase;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
