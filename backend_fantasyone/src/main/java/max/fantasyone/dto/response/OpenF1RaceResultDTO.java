package max.fantasyone.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenF1RaceResultDTO {
    @JsonProperty("raceId")
    private String raceExternalId;

    @JsonProperty("driverId")
    private String driverExternalId;

    @JsonProperty("position")
    private int position;

    @JsonProperty("time")
    private String time;

    @JsonProperty("fastestLap")
    private boolean fastestLap;

    @JsonProperty("pole")
    private boolean pole;

    @JsonProperty("penalty")
    private boolean penalizado;

    @JsonProperty("stops")
    private int stops;

    @JsonProperty("dateTime")
    private String dateTime;

    // Getters y setters
    public String getRaceExternalId() { return raceExternalId; }
    public void setRaceExternalId(String raceExternalId) { this.raceExternalId = raceExternalId; }
    public String getDriverExternalId() { return driverExternalId; }
    public void setDriverExternalId(String driverExternalId) { this.driverExternalId = driverExternalId; }
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public boolean isFastestLap() { return fastestLap; }
    public void setFastestLap(boolean fastestLap) { this.fastestLap = fastestLap; }
    public boolean isPole() { return pole; }
    public void setPole(boolean pole) { this.pole = pole; }
    public boolean isPenalizado() { return penalizado; }
    public void setPenalizado(boolean penalizado) { this.penalizado = penalizado; }
    public int getStops() { return stops; }
    public void setStops(int stops) { this.stops = stops; }
    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
}