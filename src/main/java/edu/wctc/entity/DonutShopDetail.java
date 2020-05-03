package edu.wctc.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

/**
 * This class demonstrates a one-to-one table relationship. A DonutShop object has one DonutShopDetail. Each DonutShopDetail belongs to one DonutShop.
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "donut_shop_detail")
public class DonutShopDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private int id;

    @Column(name = "year_founded")
    private int yearFounded;

    @Column(name = "hometown")
    private String hometown;

    @OneToOne(mappedBy = "detail",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonBackReference
    private DonutShop shop;

    public DonutShopDetail(int yearFounded, String hometown) {
        this.yearFounded = yearFounded;
        this.hometown = hometown;
    }

}
