package roomescape.reservation.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.RequestReservation;
import roomescape.reservation.dto.ResponseReservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.domain.Time;
import roomescape.time.dto.ResponseTime;
import roomescape.time.repository.TimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public Long save(RequestReservation requestReservation) {
        Time time = timeRepository.findById(requestReservation.timeId());

        Reservation reservation = new Reservation(null, requestReservation.name(),
                requestReservation.date(),
                time);

        return reservationRepository.save(reservation);
    }

    public ResponseReservation findOneById(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        ResponseTime responseTime = new ResponseTime(reservation.getTime().getId(),
                reservation.getTime().getStartAt());

        return new ResponseReservation(reservation.getId(),
                reservation.getName(), reservation.getDate(), responseTime);
    }

    public List<ResponseReservation> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservation -> {
                    ResponseTime responseTime = new ResponseTime(reservation.getTime().getId(),
                            reservation.getTime().getStartAt());
                    return new ResponseReservation(reservation.getId(),
                            reservation.getName(), reservation.getDate(), responseTime);
                })
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
