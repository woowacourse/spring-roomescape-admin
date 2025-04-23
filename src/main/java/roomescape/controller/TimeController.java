package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.time.TimeRequest;
import roomescape.dto.time.TimeResponse;
import roomescape.model.Time;
import roomescape.model.TimeDao;

@RequestMapping("/times")
@RestController
public class TimeController {

    private final TimeDao timeDao;

    @Autowired
    public TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @PostMapping
    public TimeResponse addTime(@RequestBody TimeRequest request){
        Time savedTime = timeDao.saveTime(request.toEntity());
        return new TimeResponse(savedTime.getId(), savedTime.getStartAt());
    }

    @GetMapping
    public List<TimeResponse> getTimes(){
        return timeDao.getAll().stream()
                .map(TimeResponse::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable("id") Long id){
        timeDao.deleteById(id);
    }
}
