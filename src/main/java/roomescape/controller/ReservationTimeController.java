package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
public class ReservationTimeController {

    private final ReservationTimeRepository repository;

    public ReservationTimeController(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/times")
    public List<ReservationTimeResponseDto> readReservationTime() {
        return repository.findAll().stream()
                .map(ReservationTimeResponseDto::toDto)
                .toList();
    }

    @PostMapping("/times")
    public ReservationTimeResponseDto postReservationTime(@RequestBody ReservationTimeRequestDto requestDto) {
        ReservationTime newReservation = repository.save(requestDto.toEntity(null));
        return ReservationTimeResponseDto.toDto(newReservation);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
