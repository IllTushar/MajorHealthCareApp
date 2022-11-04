package com.example.healthcareapp.Model;

public class covidModel
{
  private String updated,cases,todayCases,deaths,todayDeaths,recovered,todayRecovered,active,
          critical,affectedCountries;

  public covidModel() {
  }

  public covidModel(String updated, String cases, String todayCases, String deaths, String todayDeaths, String recovered, String todayRecovered, String active, String critical, String affectedCountries) {
    this.updated = updated;
    this.cases = cases;
    this.todayCases = todayCases;
    this.deaths = deaths;
    this.todayDeaths = todayDeaths;
    this.recovered = recovered;
    this.todayRecovered = todayRecovered;
    this.active = active;
    this.critical = critical;
    this.affectedCountries = affectedCountries;
  }

  public String getUpdated() {
    return updated;
  }

  public void setUpdated(String updated) {
    this.updated = updated;
  }

  public String getCases() {
    return cases;
  }

  public void setCases(String cases) {
    this.cases = cases;
  }

  public String getTodayCases() {
    return todayCases;
  }

  public void setTodayCases(String todayCases) {
    this.todayCases = todayCases;
  }

  public String getDeaths() {
    return deaths;
  }

  public void setDeaths(String deaths) {
    this.deaths = deaths;
  }

  public String getTodayDeaths() {
    return todayDeaths;
  }

  public void setTodayDeaths(String todayDeaths) {
    this.todayDeaths = todayDeaths;
  }

  public String getRecovered() {
    return recovered;
  }

  public void setRecovered(String recovered) {
    this.recovered = recovered;
  }

  public String getTodayRecovered() {
    return todayRecovered;
  }

  public void setTodayRecovered(String todayRecovered) {
    this.todayRecovered = todayRecovered;
  }

  public String getActive() {
    return active;
  }

  public void setActive(String active) {
    this.active = active;
  }

  public String getCritical() {
    return critical;
  }

  public void setCritical(String critical) {
    this.critical = critical;
  }

  public String getAffectedCountries() {
    return affectedCountries;
  }

  public void setAffectedCountries(String affectedCountries) {
    this.affectedCountries = affectedCountries;
  }
}
