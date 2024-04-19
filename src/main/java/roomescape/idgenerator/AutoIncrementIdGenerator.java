package roomescape.idgenerator;

import org.springframework.stereotype.Component;

@Component
public class AutoIncrementIdGenerator implements IdGenerator {
    public static final int INITIAL_ID = 1;
    private long id;

    public AutoIncrementIdGenerator() {
        this.id = INITIAL_ID;
    }

    public long generateNewId() {
        return id++;
    }
}
