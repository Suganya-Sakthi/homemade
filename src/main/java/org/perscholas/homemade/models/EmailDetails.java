package org.perscholas.homemade.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    private String recipient;
    private String msgBody;
    private String subject;

}
