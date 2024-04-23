package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTimeRepository;
import roomescape.dto.ReservationCreateDto;
import roomescape.dto.ReservationResponseDto;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @Transactional
    public ReservationResponseDto createReservation(ReservationCreateDto createDto) {
        validateReservationTime(createDto.timeId());
        Reservation reservation = createDto.toDomain();
        Reservation createdReservation = reservationRepository.create(reservation);
        return ReservationResponseDto.from(createdReservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.removeById(id);
    }

    private void validateReservationTime(Long id) {
        try {
            reservationTimeRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("해당되는 예약 시간이 없습니다.");
        }
    }
}
