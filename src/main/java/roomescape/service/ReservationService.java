package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.repositiory.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> readAll() {
        return reservationRepository.findAll();
    }

    public Reservation readOne(Long id) {
        return reservationRepository.findById(id);
    }

    public Long add(ReservationRequestDto reservationDto) {
        return reservationRepository.add(reservationDto);
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
