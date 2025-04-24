package roomescape.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;

@RestController
@RequestMapping(value = "/times")
public class TimeController {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @GetMapping()
    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimeDaoAll = reservationTimeDao.findAll();

        return reservationTimeDaoAll.stream()
                .map(time -> {
                    return ReservationTimeResponse.toDto(time);
                })
                .toList();
    }

    @PostMapping()
    public Long create(@Valid @RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toTime();
        return reservationTimeDao.create(reservationTime);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Long id) {
        return reservationTimeDao.delete(id);
    }
}
