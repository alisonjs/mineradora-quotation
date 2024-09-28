package org.br.mineradora.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="quotation")
@Data
@NoArgsConstructor
public class QuotationEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    @Column(name="currency_price", precision = 5, scale = 4)
    private BigDecimal currencyPrice;

    @Column(name="pct_change")
    private String pctChange;

    private String pair;

}
