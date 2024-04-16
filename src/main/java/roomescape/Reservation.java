package roomescape;

import java.util.concurrent.atomic.AtomicLong;

public class Reservation {

    private final static AtomicLong index = new AtomicLong(1);

    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    public Reservation(String name, String date, String time) {
        this.id = index.getAndIncrement();
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

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
