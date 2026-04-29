package roomescape.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Reservation {
    private final Long id;
    private final String reservationName;
    private final LocalDateTime reservationDateTime;

    public Reservation(Long id,
                       String reservationName,
                       LocalDateTime reservationDateTime) {
        this.id = id;
        this.reservationName = reservationName;
        this.reservationDateTime = reservationDateTime;
    }

    public Reservation(String reservationName,
                       LocalDateTime reservationDateTime) {
        this(null, reservationName, reservationDateTime);
    }

    /*
    필수 테스트 케이스를 수정할수 없어서 테스트용 생성자를 추가했습니다.
     */
    public Reservation(long id, String name, String date, String time) {
        this(id, name, LocalDateTime.parse(date + "T" + time));
    }
}
