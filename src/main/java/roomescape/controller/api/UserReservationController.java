package roomescape.controller.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.api.dto.request.CreateReservationRequest;
import roomescape.controller.api.dto.response.ReservationResponse;
import roomescape.service.UserReservationService;
import roomescape.service.dto.query.ReservationQuery;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class UserReservationController {

    private final UserReservationService userReservationService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ReservationResponse> getAll() {
        List<ReservationQuery> queries = userReservationService.getAll();
        return queries.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ReservationResponse create(@RequestBody CreateReservationRequest request) {
        ReservationQuery query = userReservationService.create(request.toCommand());
        return ReservationResponse.from(query);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userReservationService.delete(id);
    }
}
