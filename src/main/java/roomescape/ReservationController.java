package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reserver> reservers = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    @PostMapping("/reservations")
    public ResponseEntity<Void> create(@RequestBody Reserver reserver) {
        Reserver newReserver = Reserver.toEntity(reserver, index.getAndIncrement());
        reservers.add(newReserver);
        return ResponseEntity.created(URI.create("/reservations/" + newReserver.getId())).build();
    }
}
