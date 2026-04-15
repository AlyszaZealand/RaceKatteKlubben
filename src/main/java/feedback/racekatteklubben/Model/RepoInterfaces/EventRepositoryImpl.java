package feedback.racekatteklubben.Model.RepoInterfaces;


import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepositoryImpl {


    void createEvent(Event event);
    Optional<Event> findEventByID(int eventID);
    void updateEventInformation(Event event);
    void deleteEvent(int eventID);
    List<Event> findAllEvents();
    void signUp(int eventID, int catID);
    void removeSignUp(int eventID, int catID);
    List<Integer> findSignedUpCats(int eventID);

}
