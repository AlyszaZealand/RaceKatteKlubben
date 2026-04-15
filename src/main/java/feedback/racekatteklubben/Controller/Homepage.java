package feedback.racekatteklubben.Controller;

import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Model.Event;
import feedback.racekatteklubben.Model.Member;
import feedback.racekatteklubben.Service.CatService;
import feedback.racekatteklubben.Service.EventService;
import feedback.racekatteklubben.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class Homepage {

    private MemberService memberService;
    private CatService catService;
    private EventService eventService;

    public Homepage(MemberService memberService, CatService catService, EventService eventService) {
        this.memberService = memberService;
        this.catService = catService;
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String homepage(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "homepage";
    }


    @GetMapping("/MemberList")
    public String memberListPage(Model model) {
        List<Member> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "memberList";
    }

    @GetMapping("/CatList")
    public String catListPage(Model model) {
        List<Cat> cats = catService.getAllCats();
        model.addAttribute("cats", cats);
        return "catList";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }


}
