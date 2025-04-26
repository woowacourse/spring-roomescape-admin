package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationDto;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationResponse createReservation(final ReservationRequest request) {
        ReservationTime time = reservationTimeDao.findById(request.timeId());
        ReservationTimeResponse timeResponse = ReservationTimeResponse.of(time);
        Reservation reservation = new Reservation(request.name(), request.date(), time);
        long id = reservationDao.save(reservation);
        reservation.setId(id);
        return ReservationResponse.of(reservation, timeResponse);
    }

    public List<ReservationResponse> findAllReservations() {
        List<ReservationDto> reservationDtos = reservationDao.getAll();
        List<Reservation> reservations = ReservationDto.toReservations(reservationDtos);

        return reservations.stream()
                .map(reservation -> {
                    ReservationTime time = reservationTimeDao.findById(reservation.getTime().getId());
                    ReservationTimeResponse timeResponse = ReservationTimeResponse.of(time);
                    return ReservationResponse.of(reservation, timeResponse);
                }).toList();
    }

    public void deleteReservation(final Long id) {
        int count = reservationDao.delete(id);
        if (count == 0) {
            throw new IllegalArgumentException("[ERROR] 해당 id에 대한 예약 기록이 존재하지 않습니다.");
        }
    }

    public ReservationTimeResponse createReservationTime(final ReservationTimeRequest request) {
        ReservationTime reservationTime = request.toReservationTime();
        long id = reservationTimeDao.save(reservationTime);
        reservationTime.setId(id);
        return ReservationTimeResponse.of(reservationTime);
    }

    public List<ReservationTimeResponse> findAllReservationTimes() {
        List<ReservationTime> times = reservationTimeDao.getAll();
        return ReservationTimeResponse.from(times);
    }

    public void deleteReservationTime(final Long id) {
        int count = reservationTimeDao.delete(id);
        if (count == 0) {
            throw new IllegalArgumentException("[ERROR] 해당 id에 대한 시간 정보가 존재하지 않습니다.");
        }
    }

    public void deleteAllReservationTimes() {
        reservationTimeDao.deleteAll();
    }
}
