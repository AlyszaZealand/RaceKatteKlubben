package feedback.racekatteklubben.Repository;

import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Model.RepoInterfaces.CatRepositoryImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class CatRepository implements CatRepositoryImpl {

    private final JdbcTemplate jdbcTemplate;

    public CatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Cat> catRowMapper = (rs, rownum) -> {
        return new Cat(
                rs.getInt("catID"),
                rs.getString("catRace"),
                rs.getString("catName"),
                rs.getObject("catBirthday", LocalDate.class),
                rs.getString("catGender"),
                rs.getString("catDescription"),
                rs.getInt("memberID"),
                rs.getString("imageName")
        );
    };


    public void saveCat(Cat cat){
        String sql = "INSERT into kat (catrace, catname, catbirthday,catgender,catdescription, memberID,imageName) values (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                cat.getCatRace(),
                cat.getCatName(),
                cat.getCatBirthday(),
                cat.getCatGender(),
                cat.getCatDescription(),
                cat.getMemberID(),
                cat.getImageName()
        );

    }

    public void updateCatInformation(Cat cat){
        String sql = "UPDATE kat set catname = ?, catbirthday = ?, catgender = ?, catdescription = ?, imageName = ? where catID = ?";

        jdbcTemplate.update(sql,
                cat.getCatName(),
                cat.getCatBirthday(),
                cat.getCatGender(),
                cat.getCatDescription(),
                cat.getImageName(),
                cat.getCatID()
        );
    }

    public void deleteCat(int catID){
        String sql = "DELETE from kat WHERE catID = ?";

        jdbcTemplate.update(sql,catID);
    }

    public Optional<Cat> findCatByID(int catID){
        String sql = "SELECT * from kat where catID = ?";

        try {
            Cat cat = jdbcTemplate.queryForObject(sql, new Object[]{catID}, catRowMapper);
            return Optional.of(cat);
        }  catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Cat> findCatsByMemberID(int memberID){
        String sql = "SELECT * from Kat where memberID = ?";

        return jdbcTemplate.query(sql, catRowMapper, memberID);
    }

    public List<Cat> findAllCats(){
        String sql = "Select * from Kat";

        return jdbcTemplate.query(sql, catRowMapper);
    }

}
