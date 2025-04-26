package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.create.ReservationCreate;
import roomescape.dto.create.ReservationTimeCreate;
import roomescape.dto.read.ReservationRead;
import roomescape.dto.read.ReservationTimeRead;
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
        ReservationCreate reservationCreate = new ReservationCreate(request.name(), request.date().toString(), request.timeId());
        long id = reservationDao.save(reservationCreate);

        ReservationTimeRead readTime = reservationTimeDao.findById(request.timeId());
        ReservationTime time = readTime.toTime();

        ReservationTimeResponse timeResponse = ReservationTimeResponse.of(time);
        Reservation reservation = reservationCreate.toReservation(id, time);
        return ReservationResponse.of(reservation, timeResponse);
    }

    public List<ReservationResponse> findAllReservations() {
        List<ReservationRead> reservationReads = reservationDao.getAll();
        List<Reservation> reservations = ReservationRead.toReservations(reservationReads);

        return reservations.stream()
                .map(reservation -> {
                    ReservationTimeRead readTime = reservationTimeDao.findById(reservation.getTime().getId());
                    ReservationTime time = readTime.toTime();
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
        ReservationTimeCreate reservationTimeCreate = new ReservationTimeCreate(request.startAt().toString());
        long id = reservationTimeDao.save(reservationTimeCreate);

        ReservationTime reservationTime = reservationTimeCreate.toReservationTime(id);
        return ReservationTimeResponse.of(reservationTime);
    }

    public List<ReservationTimeResponse> findAllReservationTimes() {
        List<ReservationTimeRead> readTimes = reservationTimeDao.getAll();
        List<ReservationTime> times = readTimes.stream()
                .map(ReservationTimeRead::toTime)
                .toList();
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
