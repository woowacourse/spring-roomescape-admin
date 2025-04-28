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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReservationService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationResponse createReservation(final ReservationRequest request) {
        checkReservationExist(request);
        ReservationTimeRead readTime = reservationTimeDao.findById(request.timeId());
        ReservationTime time = toReservationTime(readTime);

        Reservation reservation = toReservation(request, time);
        ReservationCreate reservationCreate = toReservationCreate(reservation);
        long id = reservationDao.save(reservationCreate);

        ReservationTimeResponse timeResponse = toReservationTimeResponse(time);
        reservation.setId(id);
        return toReservationResponse(reservation, timeResponse);
    }

    public List<ReservationResponse> findAllReservations() {
        List<ReservationRead> reservationReads = reservationDao.getAll();
        List<Reservation> reservations = toReservations(reservationReads);

        return reservations.stream()
                .map(reservation -> {
                    ReservationTimeRead readTime = reservationTimeDao.findById(reservation.getTime().getId());
                    ReservationTime time = toReservationTime(readTime);
                    ReservationTimeResponse timeResponse = toReservationTimeResponse(time);
                    return toReservationResponse(reservation, timeResponse);
                }).toList();
    }

    public void deleteReservation(final Long id) {
        int count = reservationDao.delete(id);
        if (count == 0) {
            throw new IllegalArgumentException("[ERROR] 해당 id에 대한 예약 기록이 존재하지 않습니다.");
        }
    }

    public ReservationTimeResponse createReservationTime(final ReservationTimeRequest request) {
        ReservationTime reservationTime = toReservationTime(request);
        ReservationTimeCreate reservationTimeCreate = toReservationTimeCreate(reservationTime);
        long id = reservationTimeDao.save(reservationTimeCreate);

        reservationTime.setId(id);
        return toReservationTimeResponse(reservationTime);
    }

    public List<ReservationTimeResponse> findAllReservationTimes() {
        List<ReservationTimeRead> readTimes = reservationTimeDao.getAll();
        List<ReservationTime> times = readTimes.stream()
                .map(this::toReservationTime)
                .toList();
        return toReservationTimeResponses(times);
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

    private void checkReservationExist(final ReservationRequest request) {
        if (reservationDao.isReservationExist(request.date().toString(), request.timeId())) {
            throw new IllegalArgumentException("[ERROR] 해당 날짜와 시간에 대한 예약 기록이 존재합니다.");
        }
    }

    private List<Reservation> toReservations(List<ReservationRead> reads) {
        return reads.stream()
                .map(this::toReservation)
                .toList();
    }

    private Reservation toReservation(ReservationRead read) {
        return new Reservation(
                read.reservationId(),
                read.name(),
                LocalDate.parse(read.date(), DATE_FORMATTER),
                new ReservationTime(read.timeId(), LocalTime.parse(read.timeValue(), TIME_FORMATTER)));
    }

    private ReservationCreate toReservationCreate(Reservation reservation) {
        return new ReservationCreate(reservation.getName(), reservation.getDate().toString(), reservation.getTime().getId());
    }

    private Reservation toReservation(ReservationRequest request, ReservationTime time) {
        return new Reservation(request.name(), request.date(), time);
    }

    private ReservationTime toReservationTime(ReservationTimeRead read) {
        return new ReservationTime(read.id(), LocalTime.parse(read.startAt(), TIME_FORMATTER));
    }

    private ReservationTime toReservationTime(ReservationTimeRequest request) {
        return new ReservationTime(request.startAt());
    }

    private ReservationTimeCreate toReservationTimeCreate(ReservationTime time) {
        return new ReservationTimeCreate(time.getStartAt().toString());
    }

    private ReservationResponse toReservationResponse(final Reservation reservation, final ReservationTimeResponse timeResponse) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), timeResponse);
    }

    private List<ReservationTimeResponse> toReservationTimeResponses(final List<ReservationTime> times) {
        return times.stream()
                .map(this::toReservationTimeResponse)
                .toList();
    }

    private ReservationTimeResponse toReservationTimeResponse(final ReservationTime time) {
        return new ReservationTimeResponse(time.getId(), time.getStartAt());
    }
}
