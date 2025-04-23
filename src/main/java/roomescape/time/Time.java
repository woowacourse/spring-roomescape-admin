package roomescape.time;

import java.time.LocalTime;
import java.util.Objects;

public record Time(Long id, LocalTime startAt) {

    public Time {
        Objects.requireNonNull(startAt);
    }

    public Time writeId(final Long id){
        validateCurrentIdEmpty();

        return new Time(id, startAt);
    }

    private void validateCurrentIdEmpty() {
        if (this.id != null) {
            throw new IllegalStateException("[ERROR]");
        }
    }

    @Override
    public Long id(){
        Objects.requireNonNull(id);
        return id;
    }
}
