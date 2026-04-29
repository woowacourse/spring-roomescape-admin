package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.repository.ReservationDao;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationResDto createReservation(ReservationCreateReqDto dto) {
        Reservation reservation = Reservation.create(dto.getName(), dto.getDate(), dto.getTime());
        Reservation savedReservation = reservationDao.save(reservation);
        return ReservationResDto.from(savedReservation.getId(), savedReservation.getName(), savedReservation.getDate(), savedReservation.getTime());
    }

    public List<ReservationResDto> getReservations() {
        return reservationDao.findAll()
                .stream()
                .map(r -> ReservationResDto.from(r.getId(), r.getName(), r.getDate(), r.getTime()))
                .toList();
    }

    public ReservationResDto getReservationById(Long id) {
        Reservation reservation = reservationDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID입니다."));
        return ReservationResDto.from(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
