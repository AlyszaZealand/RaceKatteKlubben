package feedback.racekatteklubben.Repository;

import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Model.RepoInterfaces.CatRepositoryImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CatRepository {

    private final JdbcTemplate jdbcTemplate;

    public CatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
