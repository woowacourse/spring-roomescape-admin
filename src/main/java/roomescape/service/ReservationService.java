package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.service.dto.ReservationResponse;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::toDto)
                .toList();
    }

    public ReservationResponse addReservation(final ReservationCreateRequest reservationCreateRequest) {
        Long id = reservationRepository.add(reservationCreateRequest.toReservation());
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            return ReservationResponse.toDto(reservation.get());
        } else {
            throw new IllegalStateException("예약 추가가 정상적으로 되지 않았습니다.");
        }
    }

    public ReservationResponse getReservationById(final Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            return ReservationResponse.toDto(reservation.get());
        } else {
            throw new IllegalArgumentException("해당 예약을 찾을 수 없습니다.");
        }
    }

    public void deleteReservationById(final Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            reservationRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("해당 예약을 찾을 수 없습니다.");
        }
    }
}
