package roomescape.domain;

import java.util.concurrent.atomic.AtomicLong;

public record Person(long id, String name) {

    private static final AtomicLong personIndex = new AtomicLong(1);

    public Person(String name) {
        this(personIndex.getAndIncrement(), name);
    }
}
