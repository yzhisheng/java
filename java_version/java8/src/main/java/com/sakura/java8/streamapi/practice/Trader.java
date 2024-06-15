package com.sakura.java8.streamapi.practice;

/**
 * @Description
 * @ClassName Trader
 * @Author Sakura
 * @DateTime 2024-06-15 00:16:19
 * @Version 1.0
 */
public class Trader {

    private String name;
    private String city;

    public Trader() {
    }

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Trader [name=" + name + ", city=" + city + "]";
    }
}
