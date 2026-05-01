package roomescape.time.presentation;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import roomescape.time.application.TimesService;
import roomescape.time.application.dto.TimeInfo;
import roomescape.time.application.dto.TimeRequest;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimesService timesService;

    @Autowired
    public TimeController(TimesService timesService) {
        this.timesService = timesService;
    }

    @PostMapping
    public ResponseEntity<TimeInfo> createTime(
            @RequestBody TimeRequest request
    ) {
        TimeInfo registered = timesService.register(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registered.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .build();
    }


    @GetMapping
    public ResponseEntity<List<TimeInfo>> getTimes() {
        List<TimeInfo> timeInfos = timesService.getTimes();
        return ResponseEntity.ok(timeInfos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeById(@PathVariable("id") Long id) {
        timesService.deleteTimeById(id);
        return ResponseEntity.ok().build();
    }
}
