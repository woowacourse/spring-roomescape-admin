package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeResponse;
import roomescape.repository.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationTimeRepository;
import roomescape.util.DateAndTimeConverter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    @Transactional
    public ReservationResponse save(ReservationRequest reservationRequest) {
        Reservation reservation = Reservation.create(
                null,
                reservationRequest.name(),
                DateAndTimeConverter.parseToDate(reservationRequest.date()),
                reservationTimeRepository.getById(reservationRequest.timeId())
        );

        ReservationTime reservationTime = reservation.getTime();
        ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(
                reservationTime.getId(),
                DateAndTimeConverter.formatTime(reservationTime.getStartAt())
        );

        return new ReservationResponse(
                reservationRepository.save(reservation),
                reservation.getName(),
                DateAndTimeConverter.formatDate(reservation.getDate()),
                reservationTimeResponse
        );
    }

    public List<ReservationResponse> getAll() {
        return reservationRepository.getAll()
                .stream()
                .map(reservation -> {

                    ReservationTime reservationTime = reservation.getTime();
                    ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(
                            reservationTime.getId(),
                            DateAndTimeConverter.formatTime(reservationTime.getStartAt())
                    );

                    return new ReservationResponse(
                            reservation.getId(),
                            reservation.getName(),
                            DateAndTimeConverter.formatDate(reservation.getDate()),
                            reservationTimeResponse
                    );
                })
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }
}
