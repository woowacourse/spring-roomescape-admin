package roomescape.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.SaveReservationDto;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
        ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Reservation save(SaveReservationDto dto) {
        ReservationTime time = reservationTimeRepository.findById(dto.timeId());
        try {
            return reservationRepository.save(new Reservation(null, dto.name(), LocalDate.parse(dto.date()), time));
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("올바르지 않은 날짜 입력입니다.");
        }
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public void delete(long id) {
        reservationRepository.delete(id);
    }
}
