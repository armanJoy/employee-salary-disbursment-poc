package com.ibcs.salaryapp.model.domain.salary;

import java.time.LocalDate;

public interface EmployeeSalary {

//    long id;
//
//    String firstName;
//
//    private String lastName;
//
//    private int rank;
//
//    private double salary;
    public long getId();

    public void setId(long id);

    public Long getBankId();

    public void setBankId(Long bankId);

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public LocalDate getJoiningDate();

    public void setJoiningDate(LocalDate lastName);

    public int getRank();

    public void setRank(int rank);

    public int getMonth();

    public void setMonth(int month);

    public int getYear();

    public void setYear(int year);

    public double getSalary();

    public void setSalary(double salary);

    public double getBonusPer();

    public void setBonusPer(double bonusPer);

    public boolean getDisbursed();

    public void setDisbursed(boolean disbursed);

}
