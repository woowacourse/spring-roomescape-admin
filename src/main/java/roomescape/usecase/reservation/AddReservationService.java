package roomescape.usecase.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.usecase.reservationTime.ReservationTimeRepository;


@Service
public class AddReservationService implements AddReservationUseCase {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;


    public AddReservationService(final ReservationRepository reservationRepository,
                                 final ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;

        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Override
    public ReservationOutput addReservation(final ReservationInput reservationInput) {
        ReservationTime reservationTime = reservationTimeRepository.getReservationTime(reservationInput.timeId());
        validateDateTime(reservationInput.date(), reservationTime);
        Reservation reservation = new Reservation(null, reservationInput.name(), reservationInput.date(),
                reservationTime);
        Reservation reservationWithId = reservationRepository.addReservation(reservation);
        return ReservationOutput.from(reservationWithId);
    }

    private void validateDateTime(final LocalDate date, final ReservationTime reservationTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationDateTime = LocalDateTime.of(date, reservationTime.getStart_at());
        if (now.isAfter(reservationDateTime)) {
            throw new IllegalArgumentException("현재 시각 이후의 예약만 가능합니다.");
        }
    }

}
