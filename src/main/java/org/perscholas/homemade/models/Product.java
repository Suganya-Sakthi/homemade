package org.perscholas.homemade.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Entity
@Slf4j
@NoArgsConstructor
@Setter
@Getter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NonNull
    String name;

    @NonNull
    String category;

    @NonNull
    double price;


    @NonNull
    LocalDate date;

    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Chef chef;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetails> orders = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Image image;

    public Product(@NonNull String name, @NonNull String category, @NonNull double price, @NonNull LocalDate date, Chef chef) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.date = date;
        this.chef = chef;

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
