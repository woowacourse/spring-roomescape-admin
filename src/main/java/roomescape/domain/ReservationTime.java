package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservationTime {

    private final Long id;
    private final String startAt;

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        validateStartAtTimeFormat(startAt);
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }

    private void validateStartAtTimeFormat(String startAt) {
        try{
            LocalTime.parse(startAt, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException | NullPointerException e){
        throw new IllegalArgumentException("시간 형식은 HH:mm 입니다. 예) 15:23");
        }
    }
}
