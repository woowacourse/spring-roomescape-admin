package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.domain.dto.ReservationTimeRequestDto;
import roomescape.repositiory.ReservationRepository;
import roomescape.repositiory.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> readAll() {
        return reservationRepository.findAll();
    }

    public Reservation readReservationOne(Long id) {
        return reservationRepository.findById(id);
    }

    public Long addReservation(ReservationRequestDto reservationDto) {
        return reservationRepository.add(
                new Reservation(reservationDto.name(), reservationDto.date(), reservationDto.time()));
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }

    public Long addTime(ReservationTimeRequestDto reservationTimeRequestDto) {
        return reservationTimeRepository.add(new ReservationTime(reservationTimeRequestDto.startAt()));
    }

    public ReservationTime readTimeOne(Long id) {
        return reservationTimeRepository.findById(id);
    }
}
