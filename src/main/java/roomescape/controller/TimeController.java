package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import roomescape.dao.TimeDao;
import roomescape.domain_entity.Id;
import roomescape.domain_entity.Time;
import roomescape.dto.TimeRequestDto;

@Controller
public class TimeController {

    @Autowired
    private TimeDao timeDao;

    @GetMapping("/admin/time")
    public String displayAdminTime() {
        return "/admin/time.html";
    }

    @PostMapping("/times")
    public ResponseEntity<Time> createTime(
            @RequestBody TimeRequestDto timeRequest
    ) {
        Time time = timeRequest.toTime();
        long id = timeDao.create(time);
        time.setId(new Id(id));
        return ResponseEntity.ok().body(time);
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes(
    ) {
        List<Time> times = timeDao.findAll();
        return ResponseEntity.ok().body(times);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable("id") long idRequest
    ) {
        timeDao.delteById(new Id(idRequest));
        return ResponseEntity.ok().build();
    }
}
