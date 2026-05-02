package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.command.ReservationCommand;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @Transactional
    public ReservationResDto createReservation(ReservationCommand command) {
        ReservationTime reservationTime = reservationTimeDao.findById(command.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간입니다."));

        Reservation reservation = Reservation.create(command.getName(), command.getDate(), reservationTime);
        Reservation savedReservation = reservationDao.save(reservation);
        return ReservationResDto.from(savedReservation);
    }

    public List<ReservationResDto> getReservations() {
        return reservationDao.findAll()
                .stream()
                .map(ReservationResDto::from)
                .toList();
    }

    public ReservationResDto getReservationById(Long id) {
        Reservation reservation = reservationDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID입니다."));
        return ReservationResDto.from(reservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
