package roomescape.infra.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@ConditionalOnProperty(name = "room-escape.console-view.enabled", havingValue = "true")
public class IdGenerator {

    private static final int INITIAL_VALUE = 0;
    private static final int DELTA = 1;
    private static final AtomicLong ID_GENERATOR = new AtomicLong(INITIAL_VALUE);

    public long get() {
        return ID_GENERATOR.addAndGet(DELTA);
    }
}
