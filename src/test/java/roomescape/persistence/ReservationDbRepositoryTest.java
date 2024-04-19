package roomescape.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDbRepositoryTest implements ReservationRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public void save() {

    }

    @Override
    public void findAllByDateAndTime() {

    }

    @Override
    public void findAll() {

    }

    @Override
    public void findById() {

    }

    @Override
    public void deleteById() {

    }
}
