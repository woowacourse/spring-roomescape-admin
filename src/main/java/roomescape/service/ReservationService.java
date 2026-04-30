package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRequestDto;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(long id) {
        return reservationRepository.findById(id);
    }

    public Reservation save(ReservationRequestDto reservationRequestDto) {
        if (!reservationTimeRepository.isExists(reservationRequestDto.timeId())) {
            throw new IllegalArgumentException("예약시간이 존재하지 않습니다. id: " + reservationRequestDto.timeId());
        }
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequestDto.timeId());

        return reservationRepository.save(reservationRequestDto.toReservation(reservationTime));
    }

    public void delete(long id) {
        reservationRepository.delete(id);
    }
}
