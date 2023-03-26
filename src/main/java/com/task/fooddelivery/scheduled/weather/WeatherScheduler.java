package com.task.fooddelivery.scheduled.weather;

import com.task.fooddelivery.entity.WeatherReport;
import com.task.fooddelivery.entity.WeatherStation;
import com.task.fooddelivery.repository.WeatherReportRepository;
import com.task.fooddelivery.repository.WeatherStationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Component
public class WeatherScheduler {

    public static final String WEATHER_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
    public static final String WEATHER_ZONE = "Europe/Tallinn";

    private final WeatherReportRepository weatherReportRepository;
    private final WeatherStationRepository weatherStationRepository;

    @Scheduled(cron = "0 15 * * * *", zone = WEATHER_ZONE)
    public void getWeatherData() {
        try {
            URL weather_url = new URL(WEATHER_URL);
            JAXBContext context = JAXBContext.newInstance(Observations.class);
            Observations observations = (Observations) context.createUnmarshaller().unmarshal(weather_url);

            LocalDateTime time = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(observations.getTimestamp()),
                    ZoneOffset.UTC);

            for (Station station : observations.getStations()) {
                Optional<WeatherStation> weatherStation = weatherStationRepository.findByStationNameIgnoreCase(station.getName());
                if (weatherStation.isEmpty()) continue;
                WeatherReport report = WeatherReport.builder()
                        .weatherStation(weatherStation.get())
                        .stationName(station.getName())
                        .wmoCode(station.getWmocode())
                        .airTemperature(station.getAirtemperature())
                        .windSpeed(station.getWindspeed())
                        .phenomenon(station.getPhenomenon())
                        .timestamp(time)
                        .build();
                weatherReportRepository.save(report);
            }
        } catch (JAXBException | MalformedURLException e) {
            log.error("Could not retrieve weather data.", e);
        }
        log.info("New weather data received.");
    }
}
