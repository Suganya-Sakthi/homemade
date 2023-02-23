package org.perscholas.homemade.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;
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
    Blob image;

    @NonNull
    Date date;

    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Chef chef;


    public Product(@NonNull String name, @NonNull String category, @NonNull double price, @NonNull Date date) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.date = date;
    }

    @ManyToMany(mappedBy = "products", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Order> orders = new LinkedHashSet<>();

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
