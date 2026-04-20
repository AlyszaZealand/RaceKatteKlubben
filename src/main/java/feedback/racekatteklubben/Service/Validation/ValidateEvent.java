package feedback.racekatteklubben.Service.Validation;

import feedback.racekatteklubben.Model.Event;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidateEvent {

    public ValidationResult validateEvent(Event event) {
        ValidationResult result = new ValidationResult();

        if (event.getEventName() == null || event.getEventName().length() < 5) {
            result.addError("Udstillings Navn skal være mindst '5' tegn");
        }

        if (event.getEventDescription() == null || event.getEventDescription().trim().length() < 5) {
            result.addError("Udstillingens beskrivelse skal være mindst 5 tegn lang.");
        }

        if (event.getEventDate() == null || event.getEventDate().isBefore(LocalDate.now())) {
            result.addError("Udstillingen skal have en fastsat dato. Datoen må inte være sat tilbage i tiden.");
        }

        return result;
    }


}
