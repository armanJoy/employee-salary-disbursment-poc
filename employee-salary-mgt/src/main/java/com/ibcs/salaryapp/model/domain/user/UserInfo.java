package com.ibcs.salaryapp.model.domain.user;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "UserInfo")
@Getter
@Setter
@NoArgsConstructor
public class UserInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "user_sequence"),
                @Parameter(name = "initial_value", value = "1000"),
                @Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;

    @Column(name = "user_email", length = 100)
    private String userEmail;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "first_name", length = 80)
    private String firstName;

    @Column(name = "last_name", length = 80)
    private String lastName;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "nid", length = 17, columnDefinition = "VARCHAR(17) DEFAULT lpad(floor(random()*10^17)::bigint::text,17,'0')")
    private String nid;

    @Column(name = "joiningDate", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE DEFAULT '2023-01-01'")
    private LocalDate joiningDate;

    @Column(name = "user_type", length = 20)
    private String userType;

    @Column(name = "rank")
    private int rank;

    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active = true;

}
