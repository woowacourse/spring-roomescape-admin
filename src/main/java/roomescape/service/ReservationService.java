package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> findAllReservation() {
        return reservationRepository.list().stream()
                .map(reservation -> {
                            ReservationTime reservationTime = reservation.time();
                            return new ReservationResponse(
                                    reservation.id(),
                                    reservation.name(),
                                    reservation.date(),
                                    new ReservationTimeResponse(
                                            reservationTime.id(),
                                            reservationTime.startAt()
                                    )
                            );
                        }
                )
                .toList();
    }

    public ReservationResponse addReservation(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequest.timeId());
        Reservation reservation = reservationRepository.insert(new Reservation(
                        reservationRequest.name(),
                        reservationRequest.date(),
                        reservationTime
                )
        );

        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                new ReservationTimeResponse(reservation.time().id(), reservation.time().startAt())
        );
    }

    public void removeReservation(Long id) {
        reservationRepository.delete(id);
    }
}
