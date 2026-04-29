package roomescape.reservation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.entity.ReservationTime;
import roomescape.time.service.ReservationTimeService;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeService reservationTimeService;

    @Transactional
    public Reservation save(String name, LocalDate date, long timeId) {
        ReservationTime reservationTime = reservationTimeService.getById(timeId);

        LocalTime time = reservationTime.getStartAt();
        if (reservationRepository.existsByDateAndTime(date, time)) {
            throw new IllegalArgumentException("중복으로 예약을 생성할 수 없습니다.");
        }
        Reservation nonIdReservation = Reservation.createNew(name, date, reservationTime);

        return reservationRepository.save(nonIdReservation);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void deleteById(long id) {
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 예약이 존재하지 않습니다.");
        }

        reservationRepository.deleteById(id);
    }

    public Reservation getById(long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
    }

}
