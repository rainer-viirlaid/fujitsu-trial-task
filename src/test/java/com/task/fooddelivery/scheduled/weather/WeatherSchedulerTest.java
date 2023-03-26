package com.task.fooddelivery.scheduled.weather;

import com.task.fooddelivery.entity.WeatherStation;
import com.task.fooddelivery.repository.WeatherReportRepository;
import com.task.fooddelivery.repository.WeatherStationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class WeatherSchedulerTest {

    @Mock
    private WeatherReportRepository weatherReportRepository;
    @Mock
    private WeatherStationRepository weatherStationRepository;

    @InjectMocks
    private WeatherScheduler weatherScheduler;

    @Test
    public void gatherWeatherDataSavesToDatabaseTest() {
        WeatherStation station = new WeatherStation();
        station.setId(88);
        given(weatherStationRepository.findByStationNameIgnoreCase(anyString()))
                .will((InvocationOnMock i) -> i.getArgument(0).equals("Tallinn-Harku") ? Optional.of(station) : Optional.empty());
        weatherScheduler.getWeatherData();
        then(weatherReportRepository).should().save(any());
    }
}