package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationRegisterDto;
import roomescape.service.dto.ReservationResponseDto;

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

        return reservationRepository.save(reservation);
    }

    public ReservationResponseDto findReservationById(final Long id) {
        Reservation foundReservation = findReservationWithId(id);
        return new ReservationResponseDto(foundReservation);
    }

    public List<ReservationResponseDto> findAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::new)
                .toList();
    }

    public void deleteReservationById(final Long id) {
        findReservationWithId(id);
        reservationRepository.deleteById(id);
    }

    private Reservation findReservationWithId(Long id) {
        Optional<Reservation> foundReservation = reservationRepository.findById(id);

        if (foundReservation.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id 와 일치하는 예약 내역이 존재하지 않습니다.");
        }
        return foundReservation.get();
    }

    private ReservationTime findReservationTime(final Long id) {
        Optional<ReservationTime> foundReservationTime = reservationTimeRepository.findById(id);

        if (foundReservationTime.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 id 와 일치하는 예약 시각이 존재하지 않습니다.");
        }
        return foundReservationTime.get();
    }
}
