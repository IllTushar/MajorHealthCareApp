package com.example.healthcareapp.Model;

public class DoctorModel
{
private String name,gmail,phone,conferenceId,image;


    public DoctorModel(String name, String gmail, String phone, String conferenceId, String image) {
        this.name = name;
        this.gmail = gmail;
        this.phone = phone;
        this.conferenceId = conferenceId;
        this.image = image;
     //   this.resume =resume;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
