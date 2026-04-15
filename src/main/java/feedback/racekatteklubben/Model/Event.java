package feedback.racekatteklubben.Model;

import java.time.LocalDate;
import java.util.List;

public class Event {

        private int eventID;
        private String eventName;
        private String eventDescription;
        private LocalDate eventDate;
        private String createEvent;
        private String addCat;

        public Event() {
        }

        public Event(int eventID, String eventName, String eventDescription, LocalDate eventDate) {
            this.eventID = eventID;
            this.eventName = eventName;
            setEventDescription(eventDescription);
            setEventDate(eventDate);
        }

        public int getEventID() {
            return eventID;
        }

        public void setEventID(int eventID) {
            this.eventID = eventID;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getEventDescription() {
            return eventDescription;
        }

        public void setEventDescription(String eventDescription) {
            if (eventDescription == null || eventDescription.trim().length() < 5) {
                throw new IllegalArgumentException("Udstillingens beskrivelse skal være mindst 5 tegn lang.");
            }
            this.eventDescription = eventDescription;
        }

        public LocalDate getEventDate() {
            return eventDate;
        }

        public void setEventDate(LocalDate eventDate) {
            if (eventDate == null || eventDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Udstillingen skal have en fastsat dato. Datoen må ikke være sat tilbage i tiden.");
            }
            this.eventDate = eventDate;
        }


}
