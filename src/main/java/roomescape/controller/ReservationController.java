package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.entity.Reservation;
import roomescape.repository.MemoryReservationRepository;
import roomescape.service.ReservationMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final MemoryReservationRepository memoryReservationRepository;

    public ReservationController(MemoryReservationRepository memoryReservationRepository) {
        this.memoryReservationRepository = memoryReservationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> responses = memoryReservationRepository.findAll().stream()
                .map(ReservationMapper::map)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody final ReservationRequest request) {
        Reservation savedReservation = memoryReservationRepository.save(ReservationMapper.map(request));
        ReservationResponse response = ReservationMapper.map(savedReservation);

        return ResponseEntity
                .created(URI.create("/reservations/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationsData(@PathVariable final Long id) {
        if (memoryReservationRepository.deleteById(id) == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
