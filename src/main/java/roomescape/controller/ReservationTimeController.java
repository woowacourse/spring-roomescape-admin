package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.dto.ReservationTimeCreateDto;
import roomescape.dto.ReservationTimeResponseDto;

@RestController
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeResponseDto>> readReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        List<ReservationTimeResponseDto> responseDtos = new ArrayList<>();
        reservationTimes.forEach(time -> responseDtos.add(ReservationTimeResponseDto.from(time)));
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeResponseDto> createReservationTime(
            @RequestBody @Valid ReservationTimeCreateDto createDto) {
        ReservationTime time = new ReservationTime(0L, createDto.getStartAt());
        ReservationTime createdTime = reservationTimeRepository.create(time);
        ReservationTimeResponseDto responseDto = ReservationTimeResponseDto.from(createdTime);
        URI reservationTimeURI = URI.create("/times/" + responseDto.getId());
        return ResponseEntity.created(reservationTimeURI).body(responseDto);
    }
}
