package org.perscholas.homemade.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    @NotBlank(message = "Please enter a name")
    String name;

    @NonNull
    @NotBlank(message = "Please enter a email address")
    @Email(message = "Please enter a valid Email Address")
    String email;

    @NonNull
    @Setter(AccessLevel.NONE)
    @NotBlank(message = "Please enter a Password")
    String password;

    @NonNull
    @Pattern(regexp = "[0-9]{3}-[0-9]{3}-[0-9]{4}", message = "Enter a valid phone number in the format xxx-xxx-xxxx")
    String phoneNumber;

    @NonNull
    @NotBlank(message = "Please enter an Address")
    String address;

    @NonNull
    @NotBlank(message = "Please select a city")
    String city;

    @Nonnull
    @NotBlank(message = "Please select a state")
    String state;

    @NonNull
    @Pattern(regexp = "[0-9]{5}", message = "Enter a valid Zipcode - 5 digits only")
    String zipcode;

    public Chef(@NonNull String name, @NonNull String email, @NonNull String password, @NonNull String phoneNumber, @NonNull String address, @NonNull String city, String state, @NonNull String zipcode) {
        this.name = name;
        this.email = email;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public void setPassword(String password) {
        this.password =  new BCryptPasswordEncoder().encode(password);
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "chef", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    private Set<Product> products = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chef chef = (Chef) o;
        return zipcode == chef.zipcode && Objects.equals(id, chef.id) && name.equals(chef.name) && email.equals(chef.email)  && phoneNumber.equals(chef.phoneNumber) && address.equals(chef.address) && city.equals(chef.city) && Objects.equals(state, chef.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phoneNumber, address, city, state, zipcode);
    }
}
