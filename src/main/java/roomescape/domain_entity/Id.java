package roomescape.domain_entity;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Id {

    private final long value;

    public Id(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Id id1 = (Id) o;
        return value == id1.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
