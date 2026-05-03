package roomescape.reservation;

import java.time.LocalDate;
import roomescape.reservationtime.ReservationTimeResponseDTO;

public class ReservationResponseDTO {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTimeResponseDTO reservationTimeResponseDTO;

    public ReservationResponseDTO(Long id, String name,
                                  LocalDate date,
                                  ReservationTimeResponseDTO reservationTimeResponseDTO) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reservationTimeResponseDTO = reservationTimeResponseDTO;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTimeResponseDTO getReservationTimeResponseDTO() {
        return reservationTimeResponseDTO;
    }
}
