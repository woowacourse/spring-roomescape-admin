package roomescape.console;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.console.view.InputView;
import roomescape.console.view.Menu;
import roomescape.console.view.OutputView;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.dao.ReservationDaoImpl;
import roomescape.dao.ReservationTimeDaoImpl;
import roomescape.domain.Reservation;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.CreateReservationResponse;
import roomescape.mapper.ReservationMapper;
import roomescape.mapper.ReservationTimeMapper;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RoomescapeConsoleApplication {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ReservationController reservationController = new ReservationController(new ReservationService(new ReservationDaoImpl(new JdbcTemplate()), new ReservationMapper()));
        ReservationTimeController reservationTimeController = new ReservationTimeController(new ReservationTimeService(new ReservationTimeDaoImpl(new JdbcTemplate()), new ReservationDaoImpl(new JdbcTemplate()), new ReservationTimeMapper()));

        while (true) {
            Menu menu = inputView.inputMenu();
            if (menu == Menu.RESERVATION) {
                List<String> commands = inputView.inputReservationTimeMenu();
                if (commands.get(0).equals("POST")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    CreateReservationRequest request = new CreateReservationRequest(
                            commands.get(1),
                            LocalDate.parse(commands.get(2), formatter),
                            Integer.parseInt(commands.get(3))
                    );
                    ResponseEntity<CreateReservationResponse> response = reservationController.createReservation(request);
                    CreateReservationResponse body = response.getBody();
                    outputView.announceSuccessfulReservation(body);
                }
                if (commands.get(0).equals("GET")) {
                    ResponseEntity<List<Reservation>> response = reservationController.findAllReservations();
                    List<Reservation> body = response.getBody();
                    outputView.announceReservations(body);
                }
                if (commands.get(0).equals("DELETE")) {

                }
            }
            if (menu == Menu.RESERVATION_TIME) {
                inputView.inputReservationMenu();
            }
            if (menu == Menu.END) {
                break;
            }
        }
    }
}
