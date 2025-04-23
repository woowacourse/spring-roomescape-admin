package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@RestController
@RequestMapping("times")
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public List<ReservationTimeResponse> getReservationTimes() {
        ReservationTimes reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.getReservationTimes().stream()
                .map(ReservationTimeResponse::new)
                .toList();
    }

    @PostMapping
    public ReservationTimeResponse createReservationTime(@RequestBody ReservationTimeRequest request) {
        ReservationTime newReservationTime = request.toReservationTime(null);
        ReservationTime savedReservationTime = reservationTimeDao.save(newReservationTime);

        return new ReservationTimeResponse(savedReservationTime);
    }

    @DeleteMapping("{id}")
    public void deleteReservationTime(@PathVariable Long id, HttpServletResponse response) {
        boolean isDeleted = reservationTimeDao.deleteById(id);

        if (!isDeleted) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
