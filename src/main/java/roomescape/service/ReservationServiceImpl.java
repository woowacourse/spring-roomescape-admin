package roomescape.service;

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
        Reservation reservation = new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.time());

        final Reservation saved = reservationRepository.save(reservation);
        return ReservationResponse.from(saved);
    }

    @Override
    public void delete(final long id) {
        reservationRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        reservationRepository.deleteById(id);
    }
}
