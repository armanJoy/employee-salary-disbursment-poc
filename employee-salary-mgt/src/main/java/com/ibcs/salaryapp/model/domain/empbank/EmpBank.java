package com.ibcs.salaryapp.model.domain.empbank;

import com.ibcs.salaryapp.model.domain.util.TimeAuditor;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EmpBank")
@Getter
@Setter
public class EmpBank extends TimeAuditor implements Serializable {

    @Id
    @Column(name = "empBankId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long empBankId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "acName", nullable = false, length = 80)
    private String acName;

    @Column(name = "acNumber", nullable = false, length = 20)
    private String acNumber;

    @Column(name = "acType", nullable = false, length = 20)
    private String acType;

    @Column(name = "bank", nullable = false, length = 80)
    private String bank;

    @Column(name = "branch", nullable = false, length = 80)
    private String branch;

    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active = true;

}
