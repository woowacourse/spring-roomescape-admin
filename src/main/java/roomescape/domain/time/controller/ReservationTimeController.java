package roomescape.domain.time.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import roomescape.domain.time.ReservationTime;
import roomescape.domain.time.repository.ReservationTimeRepository;
import roomescape.domain.time.dto.ReservationTimeRequestDTO;
import roomescape.domain.time.dto.ReservationTimeResponseDTO;

@Controller
public class ReservationTimeController {
    private final ReservationTimeRepository repository;

    public ReservationTimeController(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/times")
    public ReservationTimeResponseDTO create(@RequestBody ReservationTimeRequestDTO requestDTO) {
        ReservationTime reservationTime = new ReservationTime(null, requestDTO.getStartAt());
        Long id = repository.save(reservationTime);
        return ReservationTimeResponseDTO.from(
                new ReservationTime(id, reservationTime.getStartAt()));
    }

    @ResponseBody
    @GetMapping("/times")
    public List<ReservationTimeResponseDTO> read() {
        return repository.findAll().stream()
                .map(ReservationTimeResponseDTO::from)
                .collect(Collectors.toList());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/times/{id}")
    public void delete(@PathVariable Long id) {
        repository.delete(id);
    }
}
