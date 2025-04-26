package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.domain.dto.ReservationTimeRequestDto;
import roomescape.repositiory.GeneralRepository;

@Service
public class ReservationService {

    private final GeneralRepository<Reservation> reservationRepository;
    private final GeneralRepository<ReservationTime> reservationTimeRepository;

    @Autowired
    public ReservationService(GeneralRepository<Reservation> reservationRepository,
                              GeneralRepository<ReservationTime> reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> readReservationAll() {
        return reservationRepository.findAll();
    }

    public Reservation readReservationOne(Long id) {
        return reservationRepository.findById(id);
    }

    public Long addReservation(ReservationRequestDto reservationDto) {
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationDto.timeId());
        return reservationRepository.add(
                new Reservation(reservationDto.name(), reservationDto.date(), reservationTime));
    }

    public void deleteReservation(Long id) {
        reservationRepository.delete(id);
    }

    public Long addTime(ReservationTimeRequestDto reservationTimeRequestDto) {
        return reservationTimeRepository.add(new ReservationTime(reservationTimeRequestDto.startAt()));
    }

    public List<ReservationTime> readTimeAll() {
        return reservationTimeRepository.findAll();
    }

    public ReservationTime readTimeOne(Long id) {
        return reservationTimeRepository.findById(id);
    }

    public void deleteTime(Long id) {
        reservationTimeRepository.delete(id);
    }
}
