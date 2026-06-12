package com.example.umcspringbootstudy.weather.controller;

import com.example.umcspringbootstudy.global.apiPayload.ApiResponse;
import com.example.umcspringbootstudy.global.apiPayload.code.GeneralSuccessCode;
import com.example.umcspringbootstudy.weather.dto.WeatherResponseDto;
import com.example.umcspringbootstudy.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public ApiResponse<WeatherResponseDto> getCurrentWeather(@RequestParam String city) {
        return ApiResponse.onSuccess(
                GeneralSuccessCode.OK, weatherService.getCurrentWeather(city)
        );
    }
}
