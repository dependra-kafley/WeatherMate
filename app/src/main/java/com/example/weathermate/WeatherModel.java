package com.example.weathermate;

public class WeatherModel {
    String temp;
    String day;
    String Condition;
    String url;

    public WeatherModel(String temp, String day, String condition,String url) {
        this.temp = temp;
        this.day = day;
        Condition = condition;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }
}
