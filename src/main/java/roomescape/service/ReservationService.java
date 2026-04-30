package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationResDto createReservation(ReservationCreateReqDto dto) {
        ReservationTime reservationTime = reservationTimeDao.findById(dto.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));
        Reservation reservation = Reservation.create(dto.getName(), dto.getDate(), reservationTime);
        Reservation savedReservation = reservationDao.save(reservation);
        return ReservationResDto.from(savedReservation.getId(), savedReservation.getName(), savedReservation.getDate(), ReservationTimeResDto.from(savedReservation.getTime().getId(), savedReservation.getTime().getStartAt()));
    }

    public List<ReservationResDto> getReservations() {
        return reservationDao.findAll()
                .stream()
                .map(r -> ReservationResDto.from(r.getId(), r.getName(), r.getDate(), ReservationTimeResDto.from(r.getTime().getId(), r.getTime().getStartAt())))
                .toList();
    }

    public ReservationResDto getReservationById(Long id) {
        Reservation reservation = reservationDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID입니다."));
        return ReservationResDto.from(reservation.getId(), reservation.getName(), reservation.getDate(), ReservationTimeResDto.from(reservation.getTime().getId(), reservation.getTime().getStartAt()));
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
