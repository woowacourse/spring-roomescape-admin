package roomescape.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;

@Service
public class ReservationService {
    private final JdbcTemplate jdbcTemplate;

    public ReservationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UpdatingDAO getUpdatingDAO() {
        return new UpdatingDAO(jdbcTemplate);
    }

    public QueryingDAO getQueryingDAO() {
        return new QueryingDAO(jdbcTemplate);
    }
}
