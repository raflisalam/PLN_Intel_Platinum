package com.example.navmenu.datamodel;


import org.json.JSONException;
import org.json.JSONObject;

public class WeatherData {

    private String temp, city, state, icon;
    private int condition;

    public static WeatherData fromJson (JSONObject jsonObject) {
        try {
            WeatherData weatherD = new WeatherData();
            weatherD.city = jsonObject.getString("name");
            weatherD.condition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherD.state = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.icon = updateWeatherIcon(weatherD.condition);
            double tempResult = jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue = (int)Math.rint(tempResult);
            weatherD.temp = Integer.toString(roundedValue);
            return weatherD;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String updateWeatherIcon(int condition) {
        if (condition >= 0 && condition <= 800) {
            return "ic_hujan_deras";
        } else if (condition == 800) {
            return "ic_cerah";
        } else if (condition >= 801 && condition <= 804) {
            return "ic_berawan";
        }
        return "";
    }

    public String getTemp() {
        return temp+"°";
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
