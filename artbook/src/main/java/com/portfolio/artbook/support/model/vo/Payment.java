package com.portfolio.artbook.support.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String impUid;
    private String merchantUid;
    private int amount;
    private String payMethod;
    private String buyerName;
    private String buyerEmail;
    private String status;
    private Date payDate;
    private int totalAmount;
    private int total;
    private int rank;
    

}
