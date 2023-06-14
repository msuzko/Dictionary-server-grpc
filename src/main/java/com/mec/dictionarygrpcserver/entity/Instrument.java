package com.mec.dictionarygrpcserver.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Table(name = "instruments")
@Getter
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticker;
    private BigDecimal price;

    public Instrument() {
    }

    public Instrument(String ticker, BigDecimal price) {
        this.ticker = ticker;
        this.price = price;
    }
}
