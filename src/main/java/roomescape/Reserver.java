package roomescape;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserver {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalDateTime time;

    public Reserver() {
    }

    public Reserver(String name, LocalDate date, LocalDateTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reserver(Long id, String name, LocalDate date, LocalDateTime time) {
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

    public LocalDateTime getTime() {
        return time;
    }

    public static Reserver toEntity(Reserver reserver, Long id) {
        return new Reserver(id, reserver.name, reserver.date, reserver.time);
    }
}
