package com.cv.spring_workcv.models;

public class CompanyDto {
    private int id;
    private String name_company;
    private String logo;
    private int openPosition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_company() {
        return name_company;
    }

    public void setName_company(String name_company) {
        this.name_company = name_company;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getOpenPosition() {
        return openPosition;
    }

    public void setOpenPosition(int openPosition) {
        this.openPosition = openPosition;
    }

    public CompanyDto() {

    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id=" + id +
                ", name_company='" + name_company + '\'' +
                ", logo='" + logo + '\'' +
                ", openPosition=" + openPosition +
                '}';
    }

    public CompanyDto(int id, String name_company, String logo, int openPosition) {
        this.id = id;
        this.name_company = name_company;
        this.logo = logo;
        this.openPosition = openPosition;
    }
}
