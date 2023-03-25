package com.task.fooddelivery.scheduled.weather;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@XmlRootElement(name = "station")
@XmlAccessorType(XmlAccessType.FIELD)
public class Station {

    private String name;
    private String wmocode;
    private double longitude;
    private double latitude;
    private String phenomenon;
    private double visibility;
    private double precipitations;
    private double airpressure;
    private double relativehumidity;
    private double airtemperature;
    private double winddirection;
    private double windspeed;
    private double windspeedmax;
    private double waterlevel;
    private double waterlevel_eh2000;
    private double watertemperature;
    private double uvindex;
}
