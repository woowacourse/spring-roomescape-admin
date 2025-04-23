package roomescape;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRegisterDto;
import roomescape.controller.dto.ReservationResponseDto;
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

    public Long saveReservation(final ReservationRegisterDto reservationRegisterDto) {
        ReservationTime reservationTime = findReservationTime(reservationRegisterDto.timeId());

        Reservation reservation = reservationRegisterDto.toReservation(reservationTime);

        long savedId = reservationRepository.save(reservation);
        reservation.setId(savedId);

        return savedId;
    }

    public ReservationResponseDto findReservationById(final long id) {
        Reservation foundReservation = findReservationWithId(id);
        return new ReservationResponseDto(foundReservation);
    }

    public List<ReservationResponseDto> findAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::new)
                .toList();
    }

    public void deleteReservationById(final long id) {
        findReservationWithId(id);
        reservationRepository.deleteById(id);
    }

    private Reservation findReservationWithId(long id) {
        Optional<Reservation> foundReservation = reservationRepository.findById(id);

        if (foundReservation.isEmpty()) {
            throw new IllegalArgumentException("해당 id 와 일치하는 예약 내역이 존재하지 않습니다.");
        }
        return foundReservation.get();
    }

    private ReservationTime findReservationTime(final long id) {
        Optional<ReservationTime> foundReservationTime = reservationTimeRepository.findById(id);

        if (foundReservationTime.isEmpty()) {
            throw new IllegalArgumentException("해당 id 와 일치하는 예약 시각이 존재하지 않습니다.");
        }
        return foundReservationTime.get();
    }
}
