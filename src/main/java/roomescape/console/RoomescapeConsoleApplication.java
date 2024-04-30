package roomescape.console;

import org.springframework.http.ResponseEntity;
import roomescape.console.dao.ReservationMemoryDao;
import roomescape.console.dao.ReservationTimeMemoryDao;
import roomescape.console.view.InputView;
import roomescape.console.view.Menu;
import roomescape.console.view.OutputView;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.CreateReservationResponse;
import roomescape.dto.CreateReservationTimeRequest;
import roomescape.mapper.ReservationMapper;
import roomescape.mapper.ReservationTimeMapper;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RoomescapeConsoleApplication {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ReservationMemoryDao reservationMemoryDao = ReservationMemoryDao.getInstance();
        ReservationTimeMemoryDao reservationTimeMemoryDao = ReservationTimeMemoryDao.getInstance();
        ReservationController reservationController = new ReservationController(new ReservationService(reservationMemoryDao, new ReservationMapper()));
        ReservationTimeController reservationTimeController = new ReservationTimeController(new ReservationTimeService(reservationTimeMemoryDao, reservationMemoryDao, new ReservationTimeMapper()));

        while (true) {
            Menu menu = inputView.inputMenu();
            if (menu == Menu.RESERVATION_TIME) {
                List<String> commands = inputView.inputReservationTimeMenu();
                if (commands.get(0).equals("POST")) {
                    CreateReservationTimeRequest request = new CreateReservationTimeRequest(
                            LocalTime.parse(commands.get(1))
                    );
                    reservationTimeController.createTime(request);
                }
                if (commands.get(0).equals("GET")) {
                    ResponseEntity<List<ReservationTime>> reservationTimes = reservationTimeController.findAllReservationTimes();
                    outputView.announceReservationTimes(reservationTimes.getBody());
                }
                if (commands.get(0).equals("DELETE")) {
                    reservationTimeController.deleteTime(Long.parseLong(commands.get(1)));
                    outputView.announceReservationTimeDeletion();
                }
            }
            if (menu == Menu.RESERVATION) {
                List<String> commands = inputView.inputReservationMenu();
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
                    reservationController.deleteReservation(Long.parseLong(commands.get(1)));
                    outputView.announceReservationDeletion();
                }
            }
            if (menu == Menu.END) {
                outputView.announceExit();
                break;
            }
        }
    }
}
