package roomescape.idgenerator;

import java.util.concurrent.atomic.AtomicLong;

public class AutoIncrementIdGenerator implements IdGenerator {
    public static final long INITIAL_ID = 1;
    private final AtomicLong id;

    public AutoIncrementIdGenerator() {
        this.id = new AtomicLong(INITIAL_ID);
    }

    public long generateNewId() {
        return id.getAndIncrement();
    }
}
