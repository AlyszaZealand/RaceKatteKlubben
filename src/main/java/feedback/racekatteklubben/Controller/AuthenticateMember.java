package feedback.racekatteklubben.Controller;

import feedback.racekatteklubben.Model.Member;
import feedback.racekatteklubben.Service.MemberService;
import feedback.racekatteklubben.Service.Validation.ValidateMember;
import feedback.racekatteklubben.Service.Validation.ValidationResult;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthenticateMember {


    public MemberService memberService;
    private final ValidateMember validateMember;

    public AuthenticateMember(MemberService memberService, ValidateMember validateMember) {
        this.memberService = memberService;
        this.validateMember = validateMember;
    }

    @GetMapping("/register")
    public String processRegistration(Model model) {
        model.addAttribute("member", new Member());
        return "registerMember";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute Member newMember, Model model) {

        ValidationResult result = memberService.registerNewMember1(newMember);

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getErrors());
            return "registerMember";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        Optional<Member> memberOpt = memberService.loginValidation(email, password);

        if (memberOpt.isPresent()) {
            session.setAttribute("loggedInUser", memberOpt.get());
            return "redirect:/catClubHomePage";
        }
        else {
            model.addAttribute("error", "Hov! E-mailen eller kodeordet er forkert.");
            return "redirect:/login";
        }
    }

    @GetMapping("/catClubHomePage")
    public String showHomePage(HttpSession session, Model model) {

        // 1. Hent brugeren op fra sessionen
        Member loggedInUser = (Member) session.getAttribute("loggedInUser");

        // 2. Sikkerhed: Er de overhovedet logget ind? Ellers ryg tilbage til login!
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // 3. Giv bruger-objektet til Thymeleaf HTML-siden
        model.addAttribute("loggedInUser", loggedInUser);

        return "catClubHomePage";
    }




}
