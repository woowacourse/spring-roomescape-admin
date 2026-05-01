package roomescape.domain.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.dto.ReservationRequestDTO;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.time.ReservationTime;
import roomescape.domain.time.repository.ReservationTimeRepository;
import roomescape.domain.user.User;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Reservation create(ReservationRequestDTO requestDTO) {
        ReservationTime time = reservationTimeRepository.findById(requestDTO.getTimeId());
        Reservation reservation = new Reservation(null, new User(null, requestDTO.getName()), requestDTO.getDate(), time);
        Long id = reservationRepository.save(reservation);
        return new Reservation(id, reservation.getUser(), reservation.getDate(), reservation.getTime());
    }

    public List<Reservation> read() {
        return reservationRepository.findAll();
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
