package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public void validateDuplicateDateAndTime(LocalDate date, LocalTime time) {
        if (reservationRepository.existByDateAndTime(date, time)) {
            throw new IllegalArgumentException("해당 시간은 이미 예약되었습니다.");
        }
    }

    public Reservation add(String name, LocalDate date, LocalTime time) {
        Reservation reservation = new Reservation(name, date, time);
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
