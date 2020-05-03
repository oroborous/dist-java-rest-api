package edu.wctc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.wctc.DateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "donut")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Donut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donut_id")
    private int id;

    @NotNull
    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "shop_id")
    private DonutShop shop;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "donut_id")
    private List<DonutReview> reviews;

    @NotNull(message = "required")
    @Size(min = 1, message = "must be greater than 0")
    @Column(name = "nm")
    private String name;

    @NotNull(message = "required")
    @Min(value = 1, message = "must be greater than 0")
    @Column(name = "calories")
    private Integer calories;

    @Column(name = "image_id")
    private Integer imageId;

    @Column(name = "date_added")
    private LocalDate dateAdded;

    public Donut(String name, int calories) {
        this.name = name;
        this.calories = calories;
        this.dateAdded = LocalDate.now();
    }

    public void add(DonutReview tempReview) {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        reviews.add(tempReview);
    }


    public String getFormattedDate() {
        return DateUtils.formatDate(dateAdded);
    }

}
