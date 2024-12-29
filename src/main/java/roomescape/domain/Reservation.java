package roomescape.domain;

import io.micrometer.common.util.StringUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import roomescape.exception.BadRequestException;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Reservation {

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 20;

    private Long id;
    private String name;
    private LocalDate date;
    private ReservationTime time;
    private Theme theme;

    public Reservation(String name, LocalDate date, ReservationTime time, Theme theme) {
        validateName(name);
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public Reservation(Long id, Reservation reservation) {
        this(id, reservation.name, reservation.date, reservation.time, reservation.getTheme());
    }

    public void validateName(String name) {
        if (StringUtils.isBlank(name) || name.length() > MAX_NAME_LENGTH) {
            throw new BadRequestException(
                    String.format("예약자의 이름은 %d자 이상 %d자 이하여야 합니다.", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
    }

    public boolean isBefore(LocalDateTime dateTime) {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time.getStartAt());
        return reservationDateTime.isBefore(dateTime);
    }
}
