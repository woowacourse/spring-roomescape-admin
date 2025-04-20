package roomescape.domain;

import java.util.concurrent.atomic.AtomicLong;

public class Counter {

    private final AtomicLong value = new AtomicLong(1);

    public long getAndIncrease() {
        return value.getAndIncrement();
    }
}
