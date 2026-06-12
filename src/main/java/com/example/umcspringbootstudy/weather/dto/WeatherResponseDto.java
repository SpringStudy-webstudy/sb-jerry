package com.example.umcspringbootstudy.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponseDto {

    private String city;
    private Double latitude;
    private Double longitude;
    private Double temperature;
    private Double windSpeed;
    private Integer weatherCode;
    private Boolean cached;
}
