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
import org.springframework.web.bind.annotation.*;

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
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";// Man skal være logget ind for at tilmelde katte
        }
        model.addAttribute("event", new Event());
        return "registerEvent";
    }

    @PostMapping("/registerEvent")
    public String handleEventForm(@ModelAttribute Event newEvent, Model model, HttpSession session){
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        ValidationResult result = eventService.createEvent(newEvent);

        if(result.hasErrors()){
            model.addAttribute("errors", result.getErrors());
            model.addAttribute("event", newEvent);
            return "/registerEvent";
        }

        return "redirect:/";
    }


    @GetMapping("/events/{id}")
    public String showEventDetails(@PathVariable int id, HttpSession session, Model model) {

        // 1. DØRMANDEN: Smid gæster væk med det samme!
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        Optional<Event> eventOpt = eventService.getEventByID(id);
        if (eventOpt.isEmpty()) {
            return "redirect:/";
        }

        // 2. HENT ALT DATA (Vi ved nu, at brugeren er logget ind)
        Member loggedInUser = (Member) session.getAttribute("loggedInUser");
        List<Cat> attendingCats = catService.getCatsForEvent(id);
        List<Cat> myCats = catService.getCatsForMember(loggedInUser.getMemberID());
        List<Integer> signedUpCatIds = eventService.getSignedUpCatsIDs(id);

        // 3. GIV ALT DATA TIL HTML PÅ ÉN GANG
        model.addAttribute("event", eventOpt.get());
        model.addAttribute("attendingCats", attendingCats);
        model.addAttribute("myCats", myCats);
        model.addAttribute("signedUpCatIds", signedUpCatIds);

        return "eventSignup";
    }


    @PostMapping("/events/signup")
    public String signupCat(@RequestParam int eventID, @RequestParam int catID, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        eventService.signUpForEvent(eventID, catID);
        return "redirect:/events/" + eventID;
    }

    @PostMapping("/events/remove")
    public String removeCat(@RequestParam int eventID, @RequestParam int catID, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        eventService.removeSignUp(eventID, catID);
        return "redirect:/events/" + eventID;
    }

}
