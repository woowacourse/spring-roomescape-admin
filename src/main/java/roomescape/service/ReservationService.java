package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.web.request.ReservationRequest;
import roomescape.domain.reservation.Name;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation reserve(ReservationRequest reservationRequest) {
        if (hasSameTimeReservation(reservationRequest)) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }

        return reservationRepository.add(createReservation(reservationRequest));
    }

    public void deleteReservation(Long id) {
        reservationRepository.delete(id);
    }

    private Reservation createReservation(ReservationRequest reservationRequest) {
        return new Reservation(new Name(reservationRequest.name()), new ReservationDate(reservationRequest.date()),
                reservationTimeRepository.findById(reservationRequest.timeId())
                        .orElseThrow(() -> new NoSuchElementException(reservationRequest.timeId() + "에 해당하는 시간이 없습니다."))
        );
    }

    private boolean hasSameTimeReservation(ReservationRequest reservationRequest) {
        return !reservationRepository.findAllByDateTime(createReservation(reservationRequest)).isEmpty();
    }
}
