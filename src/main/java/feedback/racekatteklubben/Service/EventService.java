package feedback.racekatteklubben.Service;

import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Model.Event;
import feedback.racekatteklubben.Model.RepoInterfaces.EventRepositoryImpl;
import feedback.racekatteklubben.Service.Validation.ValidateEvent;
import feedback.racekatteklubben.Service.Validation.ValidationResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepositoryImpl eventRepository;
    private final ValidateEvent validateEvent;

    public EventService(EventRepositoryImpl eventRepository, ValidateEvent validateEvent) {
        this.eventRepository = eventRepository;
        this.validateEvent = validateEvent;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAllEvents();
    }

    public Optional<Event> getEventByID(int eventID) {
        return eventRepository.findEventByID(eventID);
    }

    public void deleteEvent (int eventID){
        eventRepository.deleteEvent(eventID);
    }

    public ValidationResult createEvent(Event event){
        ValidationResult result = validateEvent.validateEvent(event);
        if(result.hasErrors()){
            return result;
        }
        eventRepository.createEvent(event);
        return result;
    }

    public ValidationResult updateEventInformation(Event event){
        ValidationResult result = validateEvent.validateEvent(event);
        if(result.hasErrors()){
            return result;
        }
        eventRepository.updateEventInformation(event);
        return result;
    }

    public void signUpForEvent(int eventID, int catID){
        eventRepository.signUp(eventID, catID);
    }

    public void removeSignUp(int eventID, int catID){
        eventRepository.removeSignUp(eventID, catID);
    }

    //Metoden finder kat id, så måske bruge findCatByID() for at få navne på katte
    public List<Integer> getSignedUpCatsIDs(int eventID){
        return eventRepository.findSignedUpCats(eventID);
    }




}
