package roomescape;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime time;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
