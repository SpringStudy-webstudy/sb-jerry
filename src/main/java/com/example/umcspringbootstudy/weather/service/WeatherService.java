package com.example.umcspringbootstudy.weather.service;

import com.example.umcspringbootstudy.weather.dto.GeoCodingResponseDto;
import com.example.umcspringbootstudy.weather.dto.WeatherApiResponseDto;
import com.example.umcspringbootstudy.weather.dto.WeatherResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestClient restClient;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    private static final Duration WEATHER_CACHE_TTL = Duration.ofMinutes(10);

    public WeatherResponseDto getCurrentWeather(String city) {
        String cacheKey = "weather:" + city;

        String cachedWeather = stringRedisTemplate.opsForValue().get(cacheKey);

        if (cachedWeather != null) {
            try {
                WeatherResponseDto response = objectMapper.readValue(cachedWeather, WeatherResponseDto.class);

                return new WeatherResponseDto(
                        response.getCity(),
                        response.getLatitude(),
                        response.getLongitude(),
                        response.getTemperature(),
                        response.getWindSpeed(),
                        response.getWeatherCode(),
                        true
                );
            } catch (JsonProcessingException e) {
                stringRedisTemplate.delete(cacheKey);
            }
        }

        WeatherResponseDto response = getWeatherFromExternalApi(city);

        try {
            String json = objectMapper.writeValueAsString(response);
            stringRedisTemplate.opsForValue().set(cacheKey, json, WEATHER_CACHE_TTL);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("날씨 정보를 캐시에 저장할 수 없습니다.", e);
        }

        return response;
    }

    private WeatherResponseDto getWeatherFromExternalApi(String city) {
        GeoCodingResponseDto geoResponse = restClient.get()
                .uri("https://geocoding-api.open-meteo.com/v1/search?name={city}&count=1&language=ko&format=json", city)
                .retrieve()
                .body(GeoCodingResponseDto.class);

        if (geoResponse == null || geoResponse.getResults() == null || geoResponse.getResults().isEmpty()) {
            throw new IllegalArgumentException("도시 정보를 찾을 수 없습니다.");
        }

        GeoCodingResponseDto.Result location = geoResponse.getResults().get(0);

        WeatherApiResponseDto weatherResponse = restClient.get()
                .uri("https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}&current_weather=true",
                        location.getLatitude(),
                        location.getLongitude())
                .retrieve()
                .body(WeatherApiResponseDto.class);

        if (weatherResponse == null || weatherResponse.getCurrentWeather() == null) {
            throw new IllegalStateException("날씨 정보를 가져올 수 없습니다.");
        }

        return new WeatherResponseDto(
                city,
                weatherResponse.getLatitude(),
                weatherResponse.getLongitude(),
                weatherResponse.getCurrentWeather().getTemperature(),
                weatherResponse.getCurrentWeather().getWindSpeed(),
                weatherResponse.getCurrentWeather().getWeatherCode(),
                false
        );
    }
}