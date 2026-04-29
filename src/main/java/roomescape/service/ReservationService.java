package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.repository.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.util.DateAndTimeConverter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationResponse save(CreateReservationRequest createReservationRequest) {
        Reservation reservation = Reservation.create(
                createReservationRequest.name(),
                DateAndTimeConverter.parseToDate(createReservationRequest.date()),
                DateAndTimeConverter.parseToTime(createReservationRequest.time())
        );

        return new ReservationResponse(
                reservationRepository.save(reservation),
                reservation.getName(),
                DateAndTimeConverter.formatDate(reservation.getDate()),
                DateAndTimeConverter.formatTime(reservation.getTime())
        );
    }

    public List<ReservationResponse> getAll() {
        return reservationRepository.getAll()
                .stream()
                .map(reservation -> new ReservationResponse(
                        reservationRepository.getId(reservation),
                        reservation.getName(),
                        DateAndTimeConverter.formatDate(reservation.getDate()),
                        DateAndTimeConverter.formatTime(reservation.getTime())
                ))
                .toList();
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }
}
