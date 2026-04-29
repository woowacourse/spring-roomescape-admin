package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private final Long id;
    private final String memberName;
    private final LocalDate date;
    private final LocalTime time;

    @JsonCreator
    public Reservation(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String memberName,
            @JsonProperty("date") LocalDate date,
            @JsonProperty("time") LocalTime time
    ) {
        this.id = id;
        this.memberName = memberName;
        this.date = date;
        this.time = time;
    }

    public Reservation(String memberName, LocalDate date, LocalTime time) {
        this.id = null;
        this.memberName = memberName;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getMemberName() {
        return memberName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
