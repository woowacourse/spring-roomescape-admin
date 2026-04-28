package roomescape.domain;

import java.util.concurrent.atomic.AtomicLong;

public class Reservation {

    private atomicLong id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public AtomicLong getNextId() {
        return id.incrementAndGet();
    }
    
}
