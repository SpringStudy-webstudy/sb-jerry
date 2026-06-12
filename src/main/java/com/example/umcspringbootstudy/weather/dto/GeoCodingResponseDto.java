package com.example.umcspringbootstudy.weather.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GeoCodingResponseDto {

    private List<Result> results;

    @Getter
    public static class Result {
        private String name;
        private Double latitude;
        private Double longitude;
        private String country;
    }
}
