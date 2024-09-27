package org.br.mineradora.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="quotation")
@Data
@NoArgsConstructor
public class QuotationEntity extends PanacheEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    @Column(name="currency_price")
    private String currencyPrice;

    @Column(name="pct_change")
    private String pctChange;

    private String pair;

}
