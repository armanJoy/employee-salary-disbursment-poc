package com.ibcs.salaryapp.model.domain.salary;

import com.ibcs.salaryapp.model.domain.util.TimeAuditor;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EmpSalary", uniqueConstraints = {
    @UniqueConstraint(name = "UniqueSalary", columnNames = {"userId", "sMonth", "sYear"})})
public class EmpSalary extends TimeAuditor implements Serializable {

    @Id
    @Column(name = "empSalaryId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long empSalaryId;

    @Column(name = "userId", nullable = false)
    private long userId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private UserInfo user;
    @Column(name = "bankId", nullable = false)
    private Long bankId;

    @Column(name = "sMonth", nullable = false)
    private int sMonth;

    @Column(name = "sYear", nullable = false)
    private int sYear;

    @Column(name = "sAmount", nullable = false)
    private double sAmount;

    @Builder.Default
    @Column(name = "disbursed", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean disbursed = false;
}
