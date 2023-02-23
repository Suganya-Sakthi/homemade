package org.perscholas.homemade.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

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
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NonNull
    String name;

    @NonNull
    String email;

    @NonNull
    String password;

    @NonNull
    String phoneNumber;

    @NonNull
    String address;

    @NonNull
    String city;

    @Nonnull
    String state;

    @NonNull
    int zipcode;

    public Chef(@NonNull String name, @NonNull String email, @NonNull String password, @NonNull String phoneNumber, @NonNull String address, @NonNull String city, String state, @NonNull int zipcode) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "chef", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    private Set<Product> products = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chef chef = (Chef) o;
        return zipcode == chef.zipcode && Objects.equals(id, chef.id) && name.equals(chef.name) && email.equals(chef.email) && password.equals(chef.password) && phoneNumber.equals(chef.phoneNumber) && address.equals(chef.address) && city.equals(chef.city) && Objects.equals(state, chef.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, phoneNumber, address, city, state, zipcode);
    }
}
