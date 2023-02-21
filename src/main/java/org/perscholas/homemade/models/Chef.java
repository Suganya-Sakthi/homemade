package org.perscholas.homemade.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

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
    @GeneratedValue
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
}
