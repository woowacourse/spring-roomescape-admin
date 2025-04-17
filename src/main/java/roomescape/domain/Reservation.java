package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

public class Reservation {

    private final static AtomicLong ID_GENERATOR = new AtomicLong(0);

    @JsonProperty
    private final Long id;
    @JsonProperty
    private final String name;
    @JsonProperty
    private final LocalDate date;
    @JsonProperty
    private final LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }
}
