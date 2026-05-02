package roomescape.domain.reservation.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.dto.ReservationRequestDTO;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.time.ReservationTime;
import roomescape.domain.time.repository.ReservationTimeRepository;

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
        validateDate(requestDTO.date());
        ReservationTime time = reservationTimeRepository.findById(requestDTO.timeId());
        Reservation reservation = new Reservation(null, requestDTO.name(), requestDTO.date(), time);
        return reservationRepository.save(reservation);
    }

    private void validateDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("예약 날짜는 오늘 이후여야 합니다.");
        }
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public void cancel(Long id) {
        reservationRepository.delete(id);
    }
}
