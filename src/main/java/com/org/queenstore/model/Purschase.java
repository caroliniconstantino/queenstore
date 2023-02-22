package com.org.queenstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.org.queenstore.enums.QuantityPurchase;

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
    private QuantityPurchase quantity_purchase;

    @ManyToOne
    @JsonIgnoreProperties("purschase")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("purschase")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("product")
    private Tag tag;

    public Purschase(Long id, QuantityPurchase quantity_purchase) {
        this.id = id;
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

    public QuantityPurchase getQuantity_purchase() {
        return quantity_purchase;
    }

    public void setQuantity_purchase(QuantityPurchase quantity_purchase) {
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
