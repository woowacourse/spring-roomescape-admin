package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserver {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reserver() {
    }

    public Reserver(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reserver(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
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

    public LocalTime getTime() {
        return time;
    }

    public static Reserver toEntity(Reserver reserver, Long id) {
        return new Reserver(id, reserver.name, reserver.date, reserver.time);
    }
}
