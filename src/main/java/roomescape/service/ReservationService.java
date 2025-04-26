package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    public void validateDuplicateReservationDateTime(LocalDate date, Long timeId) {
        if (reservationRepository.existByDateAndTimeId(date, timeId)) {
            throw new IllegalArgumentException("해당 시간은 이미 예약되었습니다.");
        }
    }

    public Reservation addReservationAndReturn(String name, LocalDate date, Long timeId) {
        ReservationTime reservationTime = reservationTimeRepository.findById(timeId);
        Reservation reservation = new Reservation(name, date, reservationTime);
        return reservationRepository.insert(reservation);
    }

    public void deleteReservationById(Long id) {
        int affectedRows = reservationRepository.deleteById(id);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약 id입니다.");
        }
    }

    public List<ReservationTime> getAllReservationTime() {
        return reservationTimeRepository.findAll();
    }

    public void validateDuplicateStartTime(LocalTime startAt) {
        if (reservationTimeRepository.existByStartAt(startAt)) {
            throw new IllegalArgumentException("이미 존재하는 예약 시간입니다.");
        }
    }

    public ReservationTime addReservationTimeAndReturn(LocalTime startAt) {
        ReservationTime reservationTime = new ReservationTime(startAt);
        return reservationTimeRepository.insert(reservationTime);
    }

    public void deleteReservationTimeById(Long id) {
        int affectedRows = reservationTimeRepository.deleteById(id);
        if (affectedRows == 0) {
            throw new IllegalArgumentException("존재하지 않는 예약 시간 id입니다.");
        }
    }
}
