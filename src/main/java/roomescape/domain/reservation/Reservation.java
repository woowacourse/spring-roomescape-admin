package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import roomescape.domain.user.UserName;

public class Reservation {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final Long id;
    private final UserName name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        this(null, name, date, time);
    }

    protected Reservation(Long id, String name, LocalDate date, LocalTime time) {
        // todo 검증 :  date, time이 현재 시간 이후인지, null
        // 아니면 date, time을 합칠까? 분리해서 받는건 view 영역이고 도메인까지 분리해서 저장할 필요를 못느끼겠음
        this.id = id;
        this.name = new UserName(name);
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() { // todo response로 이동
        return time.format(DATE_TIME_FORMATTER);
    }
}
