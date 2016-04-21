package com.xian.patientappv1;

/**
 * Created by PTTsiuoLIVIA on 3/16/16.
 */
public class User {
    private String name;
    private String gendar;
    private String dateOfBirth;
    private int height;
    private double weight;
    private String medicalCardNo;

    public User()
    {
        this.name = "";
        this.gendar = "";
        this.dateOfBirth = "";
        this.height = -1;
        this.weight = -0.1;
        this.medicalCardNo = "";
    }

    public User(String name, String gendar, int height, String dateOfBirth, double weight, String medicalCardNo) {
        this.name = name;
        this.gendar = gendar;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.medicalCardNo = medicalCardNo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGendar() {
        return gendar;
    }

    public void setGendar(String gendar) {
        this.gendar = gendar;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getMedicalCardNo() {
        return medicalCardNo;
    }

    public void setMedicalCardNo(String medicalCardNo) {
        this.medicalCardNo = medicalCardNo;
    }

}
