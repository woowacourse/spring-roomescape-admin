package roomescape.times;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.RequestTimes;
import roomescape.dto.ResponseTimes;

import java.util.List;

@Controller
@RequestMapping("/times")
public class TimesController {

    @Autowired
    private TimesRepository timesRepository;

    @GetMapping
    @ResponseBody
    public List<ResponseTimes> times() {
        return timesRepository.findAll()
                .stream()
                .map(ResponseTimes::new)
                .toList();
    }

    @PostMapping
    @ResponseBody
    public ResponseTimes addTimes(@RequestBody RequestTimes requestTimes) {
        Times requestTimesWithoutId = requestTimes.toDomain();
        Long id = timesRepository.add(requestTimesWithoutId);
        Times newTimes = new Times(id, requestTimesWithoutId.getStartAt());
        return new ResponseTimes(newTimes);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteTimes(@PathVariable Long id) {
        timesRepository.remove(id);
    }
}
