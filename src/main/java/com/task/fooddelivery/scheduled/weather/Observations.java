package com.task.fooddelivery.scheduled.weather;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@NoArgsConstructor
@XmlRootElement(name = "observations")
@XmlAccessorType (XmlAccessType.FIELD)
public class Observations {

    @XmlAttribute
    private long timestamp;
    @XmlElement(name = "station")
    private List<Station> stations;

}
