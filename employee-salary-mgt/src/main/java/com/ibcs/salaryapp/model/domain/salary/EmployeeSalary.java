package com.ibcs.salaryapp.model.domain.salary;

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

    public long getBankId();

    public void setBankId(long bankId);

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public int getRank();

    public void setRank(int rank);

    public double getSalary();

    public void setSalary(double salary);

}
