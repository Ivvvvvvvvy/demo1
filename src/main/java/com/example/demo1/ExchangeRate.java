package com.example.demo1;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="EXCHANGE_RATE")
@Entity
@Setter
@Getter
@ToString
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String currency;

    @Column(name="currency_in_chinese")
    private String currency_in_chinese;

    @Column(name="rate")
    private String rate;
}