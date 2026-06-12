package com.example.umcspringbootstudy.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class WeatherApiResponseDto {
    private Double latitude;
    private Double longitude;

    @JsonProperty("current_weather")
    private CurrentWeather currentWeather;

    @Getter
    public static class CurrentWeather {
        private Double temperature;

        @JsonProperty("windspeed")
        private Double windSpeed;

        @JsonProperty("weathercode")
        private Integer weatherCode;
    }
}
