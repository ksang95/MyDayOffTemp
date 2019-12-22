package com.team4.dayoff.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="OrderView")
public class OrderView {
    @Id
    Integer orderId;

    Integer productId;
    String productThumbnailName;
    String productName;
    Integer productIsAvailable;
    String orderColor;
    String orderSize;
    Integer orderQuantity;
    Integer orderPrice;
    Integer groupId;
    Integer userId;
    String userName;
    Integer gradeDiscount;
    Integer couponDiscount;
    Integer pointUse;
    Integer totalPay;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone =  "Asia/Seoul")
    private Date orderDate;
    private Integer invoice;
    String code;
    String codeContent;


}