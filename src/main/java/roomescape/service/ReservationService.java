package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public void validateDuplicateDateAndTimeId(LocalDate date, Long timeId) {
        if (reservationRepository.existByDateAndTimeId(date, timeId)) {
            throw new IllegalArgumentException("해당 시간은 이미 예약되었습니다.");
        }
    }

    public Reservation add(String name, LocalDate date, ReservationTime reservationTime) {
        Reservation reservation = new Reservation(name, date, reservationTime);
        return reservationRepository.insertAndGet(reservation);
    }

    public void deleteById(Long id) {
        int affectedRows = reservationRepository.deleteByIdAndCountAffected(id);
        System.out.println(affectedRows);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약 id입니다.");
        }
    }
}
