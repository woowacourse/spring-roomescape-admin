package roomescape.model;

import java.util.concurrent.atomic.AtomicLong;

public class Id {
    private static final AtomicLong index = new AtomicLong(1);
    private long id;

    public Id() {
        this.id = index.getAndIncrement();
    }

    public long getIdValue() {
        return this.id;
    }
}
