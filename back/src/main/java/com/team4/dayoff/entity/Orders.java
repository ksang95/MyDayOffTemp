package com.team4.dayoff.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Orders
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@DynamicInsert
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="code")
    @ColumnDefault("0000")
    private Code code;

    @ManyToOne
    @JoinColumn(name="groupId")
    private OrderGroup orderGroup;

    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;

    private String color;
    private String size;
    private Integer quantity;
    private Integer price;
}