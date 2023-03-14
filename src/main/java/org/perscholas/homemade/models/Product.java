package org.perscholas.homemade.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
    @NotBlank(message = "Please Enter a name")
    String name;

    @NonNull
    @NotBlank(message = "Please Enter a Category")
    String category;

    @NonNull
    double price;

    @NonNull
    @FutureOrPresent(message = "Choose a valid date")
    LocalDate date;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Chef chef;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<OrderDetails> orders = new LinkedHashSet<>();

    //Helper Methods to Add and Remove orders
    public void addOrders(OrderDetails order){
        orders.add(order);
    }
    public void removeOrders(OrderDetails order) {
        orders.remove(order);
    }
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
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && name.equals(product.name) && category.equals(product.category) && date.equals(product.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, price, date);
    }
}
