package org.currencyexchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "rate", uniqueConstraints = @UniqueConstraint(columnNames = "rate_code"))
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    @JsonIgnore
    private Integer id;

    @Column(name = "rate_code", nullable = false)
    private String code;

    @Column(name = "rate_value", nullable = false)
    private BigDecimal value;

    @Column(name = "last_modified")
    @JsonIgnore
    private Timestamp lastModified;
}
