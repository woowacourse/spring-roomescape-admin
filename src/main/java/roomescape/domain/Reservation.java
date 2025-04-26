package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class Reservation {

    @JsonProperty
    private Long id;
    @JsonProperty
    private final String name;
    @JsonProperty
    private final LocalDate date;
    @JsonProperty
    private ReservationTime time;

    @JsonCreator
    public Reservation(@JsonProperty("id") Long id, @JsonProperty("name") String name,
                       @JsonProperty("date") LocalDate date, @JsonProperty("time") ReservationTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, ReservationTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
