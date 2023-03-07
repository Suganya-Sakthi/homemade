package org.perscholas.homemade.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    String name;
    @NonNull
    String url;


    @ToString.Exclude
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    Product product;

    public Image(String name, String url,Product product) {
        this.name=name;
        this.url=url;
        this.product=product;
    }

    public Image(@NonNull String name, @NonNull String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id && name.equals(image.name) && url.equals(image.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url);
    }
}
