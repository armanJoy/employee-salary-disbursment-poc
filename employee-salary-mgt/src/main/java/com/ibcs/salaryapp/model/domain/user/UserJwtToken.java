package com.ibcs.salaryapp.model.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "UserJwtToken")
@Getter
@Setter
public class UserJwtToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId", length = 80)
    private String userId;

    @Column(name = "token", length = 255)
    private String token;

}
