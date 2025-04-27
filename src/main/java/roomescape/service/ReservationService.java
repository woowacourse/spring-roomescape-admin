package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain_entity.Id;
import roomescape.domain_entity.Reservation;
import roomescape.domain_entity.ReservationTime;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

@Component
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public List<ReservationResponseDto> findAllReservations() {
        return reservationDao.findAll().stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto createReservation(ReservationRequestDto reservationRequest) {
        Reservation newReservation = reservationRequest.toReservation();
        ReservationTime reservationTime = timeDao.findById(reservationRequest.timeId());
        long reservationId = reservationDao.create(newReservation);

        newReservation.setId(new Id(reservationId));
        newReservation.setTime(reservationTime);

        return ReservationResponseDto.from(newReservation);
    }

    public void deleteReservation(long id) {
        reservationDao.deleteById(new Id(id));
    }
}
