package roomescape.service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll();
    }

    public Long create(final String name, final LocalDate date, final LocalTime time) {
        return reservationRepository.create(name, date, time);
    }

    public void delete(final Long id) {
        reservationRepository.delete(id);
    }
}
