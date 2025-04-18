package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<ReservationResponse> getReservations() {
        return ReservationResponse.from(reservationRepository.findAll());
    }

    @Override
    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        String name = reservationRequest.name();
        LocalDate date = reservationRequest.date();
        LocalTime time = reservationRequest.time();
        if (reservationRepository.selectByDateAndTime(date,time)) {
            throw new IllegalArgumentException("해당 시간에는 예약이 존재합니다.");
        }

        Reservation reservation = new Reservation(name, date, time);

        final Reservation saved = reservationRepository.save(reservation);
        return ReservationResponse.from(saved);
    }

    @Override
    public void delete(final long id) {
        if (reservationRepository.findById(id) == null) {
            throw new NoSuchElementException("없는 예약번호 입니다.");
        }
        reservationRepository.deleteById(id);
    }
}
