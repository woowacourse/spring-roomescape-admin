package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import roomescape.model.Entity;
import roomescape.model.Reservation;

public abstract class Repository<T extends Entity<T>> {

    private final Dao dao;

    protected Repository(Dao dao) {
        this.dao = dao;
    }

    protected abstract String getAllQuery();

    protected abstract String saveQuery();

    protected abstract String removeQuery();

    protected abstract T rowMapper(ResultSet resultSet, int rowNum) throws SQLException;

    protected abstract PreparedStatement preparedStatementProvider(PreparedStatement preparedStatement,
        Reservation reservation) throws SQLException;

    public List<T> getAll() {
        return dao.getAll(getAllQuery(), this::rowMapper);
    }

    public Reservation save(Reservation reservation) {
        return dao.save(saveQuery(), reservation, this::preparedStatementProvider);
    }

    public void remove(Long id) {
        dao.remove(removeQuery(), id);
    }
}
