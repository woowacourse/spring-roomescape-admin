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
import roomescape.dao.TimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.TimeRequest;
import roomescape.dto.response.TimeResponse;

@RestController
@RequestMapping(value = "/times")
public class TimeController {

    @Autowired
    private TimeDao timeDao;

    @GetMapping()
    public List<TimeResponse> findAll() {
        List<ReservationTime> reservationTimeDaoAll = timeDao.findAll();

        return reservationTimeDaoAll.stream()
                .map(time -> {
                    return TimeResponse.toDto(time);
                })
                .toList();
    }

    @PostMapping()
    public Long create(@Valid @RequestBody TimeRequest timeRequest) {
        ReservationTime reservationTime = timeRequest.toTime();
        return timeDao.create(reservationTime);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Long id) {
        return timeDao.delete(id);
    }
}
