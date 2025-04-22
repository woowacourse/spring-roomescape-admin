package roomescape;

import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRegisterDto;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Long save(final ReservationRegisterDto reservationRegisterDto) {
        ReservationTime reservationTime = findReservationTime(reservationRegisterDto);
        Reservation reservation = reservationRegisterDto.toReservation(reservationTime);
        long savedId = reservationRepository.save(reservation);
        reservation.setId(savedId);

        return savedId;
    }

    private ReservationTime findReservationTime(ReservationRegisterDto reservationRegisterDto) {
        Optional<ReservationTime> foundReservationTime = reservationTimeRepository.findById(
                reservationRegisterDto.reservationTimeId());
        if (foundReservationTime.isEmpty()) {
            throw new IllegalArgumentException("해당 id 와 일치하는 ReservationTime 이 존재하지 않습니다.");
        }
        return foundReservationTime.get();
    }
}
