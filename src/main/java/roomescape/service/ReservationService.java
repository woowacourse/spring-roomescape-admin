package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDto;
import roomescape.domain.TimeSlot;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final TimeRepository timeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(TimeRepository timeRepository, ReservationRepository reservationRepository) {
        this.timeRepository = timeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> read() {
        return reservationRepository.read();
    }

    public Reservation create(ReservationDto reservationDto) {
        Long reservationId = reservationRepository.create(reservationDto);
        TimeSlot timeSlot = timeRepository.findById(reservationDto.timeId());

        return reservationDto.toEntity(reservationId, timeSlot);
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
