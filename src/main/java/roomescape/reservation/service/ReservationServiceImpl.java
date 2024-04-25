package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.dto.ReservationsResponseDto;
import roomescape.reservation.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional(readOnly = true)
    public ReservationsResponseDto findAllReservation() {
        List<ReservationResponseDto> response = reservationRepository.findAll().stream()
                .map(r -> new ReservationResponseDto(r.getId(), r.getName(), r.getDate(), r.getTime()))
                .toList();

        return new ReservationsResponseDto(response);
    }

    @Transactional
    public ReservationResponseDto addReservation(final ReservationRequestDto request) {
        Reservation reservation = reservationRepository.insert(new Reservation(request.name(), request.date(), request.time()));

        return new ReservationResponseDto(
                reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @Transactional
    public void deleteReservationById(final Long id) {
        int updateCount = reservationRepository.deleteById(id);
        if (updateCount == 0) {
            throw new IllegalArgumentException(String.format("ID %d - Reservation 정보가 존재하지 않습니다.", id));
        }
    }
}
