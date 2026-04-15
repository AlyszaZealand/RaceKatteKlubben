package feedback.racekatteklubben.Repository;

import feedback.racekatteklubben.Model.Event;
import feedback.racekatteklubben.Model.RepoInterfaces.EventRepositoryImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Repository
public class EventRepository implements EventRepositoryImpl {

    private final JdbcTemplate jdbcTemplate;

    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Event> eventRowMapper = (rs, rowNum) -> {
        return new Event(
                rs.getInt("eventID"),
                rs.getString("eventName"),
                rs.getString("eventDescription"),
                rs.getObject("eventDate", LocalDate.class)
        );
    };

    public void createEvent(Event event){
        String sql = "Insert into event (eventID, eventName, eventDescription, eventDate) values (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                event.getEventID(),
                event.getEventName(),
                event.getEventDescription(),
                event.getEventDate());
    }

    public void updateEventInformation(Event event){
        String sql = "update event set eventDescription = ?, eventName = ?, eventDate = ? where eventID = ?";
        jdbcTemplate.update(sql,
                event.getEventDescription(),
                event.getEventName(),
                event.getEventDate(),
                event.getEventID()
        );
    }

    public Optional<Event> findEventByID(int eventID){
        String sql = "select * from event where eventID = ?";
        try{
            Event event = jdbcTemplate.queryForObject(sql, eventRowMapper, eventID);
            return Optional.of(event);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public void deleteEvent(int eventID){
        String sql = "Delete from event where eventID = ?";
        jdbcTemplate.update(sql, eventID);
    }

    public List<Event> findAllEvents(){
        String sql = "SELECT * FROM event";
        return jdbcTemplate.query(sql, eventRowMapper);
    }

    public void signUp(int eventID, int catID){
        String sql = "insert into kat_event (catID, eventID) values (?,?)";
        jdbcTemplate.update(sql, catID, eventID);
    }

    public void removeSignUp(int eventID, int catID){
        String sql = "delete from kat_event where eventID = ? and catID = ?";
        jdbcTemplate.update(sql, eventID, catID);
    }

    public List<Integer> findSignedUpCats(int eventID){
        String sql = "SELECT catID FROM kat_event WHERE eventID = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, eventID);
    }

}
