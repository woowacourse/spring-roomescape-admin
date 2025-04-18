package roomescape.reservation.domain;

import java.time.LocalDateTime;
import roomescape.reservation.domain.exception.ReserverNameEmptyException;

public class Reservation {

    private final Long id;
    private final String reserverName;
    private final LocalDateTime dateTime;

    public Reservation(Long id, String reserverName, LocalDateTime dateTime) {
        this.id = id;
        this.reserverName = validateReserverName(reserverName);
        this.dateTime = dateTime;
    }

    private String validateReserverName(String reserverName) {
        if (reserverName == null || reserverName.isBlank()) {
            throw new ReserverNameEmptyException("[ERROR] 예약자 이름은 필수입니다.");
        }
        return reserverName;
    }

    public boolean isSameId(final Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    public String getReserverName() {
        return reserverName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
