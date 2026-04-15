package feedback.racekatteklubben.Controller;

import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Model.Event;
import feedback.racekatteklubben.Model.Member;
import feedback.racekatteklubben.Service.CatService;
import feedback.racekatteklubben.Service.EventService;
import feedback.racekatteklubben.Service.Validation.ValidationResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class EventController {

    private EventService eventService;
    private CatService catService;

    public EventController(EventService eventService, CatService catService) {
        this.eventService = eventService;
        this.catService = catService;
    }

    @GetMapping("/registerEvent")
    public String showEventForm(Model model,HttpSession session) {
        Member loggedInUser = (Member) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // Man skal være logget ind for at tilmelde katte
        }
        model.addAttribute("event", new Event());
        return "registerEvent";
    }

    @PostMapping("/registerEvent")
    public String handleEventForm(@ModelAttribute Event newEvent, Model model){

        ValidationResult result = eventService.createEvent(newEvent);

        if(result.hasErrors()){
            model.addAttribute("errors", result.getErrors());
            return "redirect:/registerEvent";
        }

        return "redirect:/";
    }


    @GetMapping("/events/{id}")
    public String showEventDetails(@PathVariable int id, HttpSession session, Model model) {
        Member loggedInUser = (Member) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // Man skal være logget ind for at tilmelde katte
        }

        Optional<Event> eventOpt = eventService.getEventByID(id);
        if (eventOpt.isEmpty()) {
            return "redirect:/events"; // Event findes ikke
        }

        // Hent brugerens egne katte
        List<Cat> myCats = catService.getCatsForMember(loggedInUser.getMemberID());

        // Hent ID'er på de katte, der allerede ER tilmeldt dette event
        List<Integer> signedUpCatIds = eventService.getSignedUpCatsIDs(id);

        model.addAttribute("event", eventOpt.get());
        model.addAttribute("myCats", myCats);
        model.addAttribute("signedUpCatIds", signedUpCatIds);

        return "eventSignup"; // Kræver en eventSignup.html fil
    }

}
