package roomescape.controller;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationInfoDto;
import roomescape.dto.ReservationInfosDto;
import roomescape.dto.SaveReservationDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @GetMapping()
    public ResponseEntity<ReservationInfosDto> getReservations() {
        /* 가져오기 */
        ReservationInfosDto data = new ReservationInfosDto(new ArrayList<>());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ReservationInfoDto> addReservation(@RequestBody SaveReservationDto saveReservationDto) {
        /* 등록하기 */
        ReservationInfoDto data = new ReservationInfoDto(-1, "test", "test", "test");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id) {
        /* 삭제하기 */
        System.out.println("delete");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
