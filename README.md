# Endpoints

## Delivery fee calculation

**Endpoint:** `/fee`

Request body:
```json
{
  "city": "city name",
  "method": "delivery method name",
  "time": "(optional) time with timezone"
}
```

Calculate the delivery fee according to the city, delivery method and weather.

By default, current weather data is used. If a timestamp is provided, then weather data from that point in time 
will be used. If no weather data is found at the given timestamp (+- 2 hours), then an exception will be thrown.

Example input:
```json
{
  "city": "tartu",
  "method": "bike",
  "time": "2023-03-26T13:00:00+03:00"
}
```

---

## Changing business rules

The following endpoints can be used to modify business rules.

### Regional base fee

**Endpoint:** `/business/regional_base_fee/create`

Request body:
```json
{
  "cityName": "city name",
  "methodName": "delivery method name",
  "fee": "base fee (as a BigDecimal or double)"
}
```

Save a new regional base fee into the database.

**Endpoint:** `/business/regional_base_fee/read`

Request parameters:
* city (string) - name of the city
* method (string) - name of the delivery method

Get the regional base fee according to city and delivery method.

**Endpoint:** `/business/regional_base_fee/update`

Request body:
```json
{
  "cityName": "city name",
  "methodName": "delivery method name",
  "fee": "base fee (as a BigDecimal or double)"
}
```

Change the regional base fee for the given city and delivery method.

**Endpoint:** `/business/regional_base_fee/delete`

Request parameters:
* city (string) - name of the city
* method (string) - name of the delivery method

Delete a regional base fee from the database.

---

### Air temperature fee

**Endpoint:** `/business/air_temperature_fee/create`

Request body:
```json
{
  "minTemp": "minimum temperature (inclusive) at which the rule will be applied (as a double); will default to -inf",
  "maxTemp": "maximum temperature (inclusive) at which the rule will be applied (as a double); will default to +inf",
  "deliveryForbidden": "whether delivery should be forbidden under this rule (as a boolean); will default to false",
  "methodName": "delivery method name",
  "fee": "fee (as a BigDecimal or double)"
}
```

Save a new air temperature fee into the database.

**Endpoint:** `/business/air_temperature_fee/delete`

Request body:
```json
{
  "minTemp": "minimum temperature value of the rule (as a double); will default to -inf",
  "maxTemp": "maximum temperature value of the rule (as a double); will default to +inf",
  "deliveryMethod": "delivery method name"
}
```

Delete matching air temperature fee(s) from the database. Will throw error if no exact match is found.

---

### Wind speed fee

**Endpoint:** `/business/wind_speed_fee/create`

Request body:
```json
{
  "minSpeed": "minimum wind speed (inclusive) at which the rule will be applied (as a double); will default to -inf",
  "maxSpeed": "maximum wind speed (inclusive) at which the rule will be applied (as a double); will default to +inf",
  "deliveryForbidden": "whether delivery should be forbidden under this rule (as a boolean); will default to false",
  "deliveryMethodName": "delivery method name",
  "fee": "fee (as a BigDecimal or double)"
}
```

Save a new wind speed fee into the database.

**Endpoint:** `/business/wind_speed_fee/delete`

Request body:
```json
{
  "minSpeed": "minimum wind speed value of the rule (as a double); will default to -inf",
  "maxSpeed": "maximum wind speed value of the rule (as a double); will default to +inf",
  "deliveryMethod": "delivery method name"
}
```

Delete matching wind speed fee(s) from the database. Will throw error if no exact match is found.

---

### Weather phenomenon fee

**Endpoint:** `/business/phenomenon_fee/create`

Request body:
```json
{
  "phenomenonName": "name of the phenomenon; the rule is applied if this string is found in the weather report",
  "deliveryForbidden": "whether delivery should be forbidden under this rule (as a boolean); will default to false",
  "deliveryMethodName": "delivery method name",
  "fee": "fee (as a BigDecimal or double)"
}
```

Save a new phenomenon fee into the database.

**Endpoint:** `/business/phenomenon_fee/delete`

Request body:
```json
{
  "phenomenonName": "name of the phenomenon",
  "deliveryMethodName": "delivery method name"
}
```

Delete matching phenomenon fee(s) from the database. Will throw error if no exact match is found.

---

## Business objects

The following endpoints can be used to manage business objects.

### Weather stations

Every weather report is tied to a weather station. If no station with a matching name is found, then the report is discarded.

**Endpoint:** `/business/weather_station/create`

Request parameters:
 * station (string) - name of the weather station

Save a new weather station into the database.

**Endpoint:** `/business/weather_station/read`

Request parameters:
 * station (string) - name of the weather station

Check if the weather station already exists in the database.

**Endpoint:** `/business/weather_station/update`

Request parameters:
 * oldName (string) - old weather station name
 * newName (string) - new weather station name

Change the name of a weather station.

**Endpoint:** `/business/weather_station/delete`

Request parameters:
 * station (string) - name of the weather station

Delete a weather station from the database. Any related cities must be deleted or updated beforehand. Any related weather reports will be deleted.

---

### Cities

**Endpoint:** `/business/city/create`

Request parameters:
* city (string) - name of the city
* station (string) - name of the associated weather station

Save a new city into the database.

**Endpoint:** `/business/city/read`

Request parameters:
* city (string) - name of the city

Get information about a city.

**Endpoint:** `/business/city/update`

Request parameters:
* oldName (string) - old city name
* newName (string) - new city name
* stationName (string) - weather station name

Change the name or weather station of a city.

**Endpoint:** `/business/city/delete`

Request parameters:
* city (string) - name of the city

Delete a city from the database. Any related regional base fees must be deleted beforehand.

---

### Delivery methods

**Endpoint:** `/business/delivery_method/create`

Request parameters:
* method (string) - name of the delivery method

Save a new delivery method into the database.

**Endpoint:** `/business/delivery_method/read`

Request parameters:
* method (string) - name of the delivery method

Check if the delivery method already exists in the database.

**Endpoint:** `/business/delivery_method/update`

Request parameters:
* oldName (string) - old delivery method name
* newName (string) - new delivery method name

Change the name of a delivery method.

**Endpoint:** `/business/delivery_method/delete`

Request parameters:
* method (string) - name of the delivery method

Delete a delivery method from the database. Any related regional base fees must be deleted beforehand.


