package com.example.healthcareapp.Model;

public class DoctorResumeModel {
    private String name,resumePdf;

    public DoctorResumeModel(String name, String resumePdf) {
        this.name = name;
        this.resumePdf = resumePdf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResumePdf() {
        return resumePdf;
    }

    public void setResumePdf(String resumePdf) {
        this.resumePdf = resumePdf;
    }
}
