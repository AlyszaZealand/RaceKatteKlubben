package feedback.racekatteklubben.Controller;

import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Model.Member;
import feedback.racekatteklubben.Service.CatService;
import feedback.racekatteklubben.Service.MemberService;
import feedback.racekatteklubben.Service.Validation.ValidationResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class MyProfile {


    private CatService catService;
    private MemberService memberService;

    public MyProfile(CatService catService, MemberService memberService) {
        this.catService = catService;
        this.memberService = memberService;
    }

    @GetMapping("/myProfile")
    public String showHomePage(HttpSession session, Model model) {

        // 1. Hent brugeren op fra sessionen
        Member loggedInUser = (Member) session.getAttribute("loggedInUser");

        // 2. Sikkerhed: Er de overhovedet logget ind? Ellers ryg tilbage til login!
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        // Make sure this is hitting the DB fresh every time
        List<Cat> cats = catService.getCatsForMember(loggedInUser.getMemberID());

        // 3. Giv bruger-objektet til Thymeleaf HTML-siden
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("cats", cats);

        return "myProfile";
    }


    @PostMapping("/delete/{id}")
    public String deleteCat(@PathVariable int id) {
        catService.deleteCat(id);
        return "redirect:/myProfile";
    }


    @GetMapping("/editProfile")
    public String showEditProfile(HttpSession session, Model model) {

        Member loggedInUser = (Member) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("editMember", loggedInUser);

        return "editProfile";
    }


    @PostMapping("/editProfile/{id}")
    public String handleEditForm(@PathVariable int id, @ModelAttribute Member editMember, HttpSession session, Model model) {

        // 1. Sørg for at vi opdaterer det rigtige ID
        editMember.setMemberID(id);


        // 2. Send til Servicen og tjek for valideringsfejl (f.eks. tomt navn)
        ValidationResult result = memberService.updateMemberInformation(editMember);

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getErrors());
            model.addAttribute("editMember", editMember); // Send dataen tilbage, så felterne ikke bliver tømt
            return "editProfile";
        }

        // 3. MAGIEN: Hent den friske, opdaterede bruger fra databasen
        Optional<Member> updatedMember = memberService.getMemberByID(id);

        if (updatedMember.isPresent()) {
            // Læg den nye, friske bruger ned i sessionen (Overskriv den gamle)
            session.setAttribute("loggedInUser", updatedMember.get());
        }

        // Send dem tilbage til deres profilside
        return "redirect:/myProfile";
    }

    @GetMapping("/updatePassword")
    public String updatePassword(HttpSession session, Model model) {
        Member loggedInUser = (Member) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("editMember", loggedInUser);
        return "updatePassword";
    }

    @PostMapping("/updatePassword/{id}")
    public String handleUpdatePassword(@PathVariable int id,
                                       @RequestParam String currentPassword,
                                       @RequestParam String newPassword,
                                       @RequestParam String confirmPassword,
                                       HttpSession session,
                                       Model model) {

        Member loggedInUser = (Member) session.getAttribute("loggedInUser");
        loggedInUser.setMemberID(id);

        ValidationResult result = memberService.updateMemberPassword(loggedInUser, currentPassword, newPassword, confirmPassword);

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getErrors());
            model.addAttribute("editMember", loggedInUser);
            return "updatePassword";
        }

        Optional<Member> updatedMember = memberService.getMemberByID(id);
        if (updatedMember.isPresent()) {
            session.setAttribute("loggedInUser", updatedMember.get());
        }

        return "redirect:/myProfile";
    }



    @GetMapping("/editCat/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Cat editCat = catService.getCatByID(id).orElse(null);

        if (editCat == null) {
            return "redirect:/myProfile";
        }

        model.addAttribute("editCat", editCat);
        return "editCat";
    }

    @PostMapping("/editCat/{id}")
    public String updateCat(@PathVariable int id,
                            @ModelAttribute Cat editCat,
                            @RequestParam(value = "image", required = false) MultipartFile image,
                            Model model) throws IOException {

        editCat.setCatID(id);

        ValidationResult result = catService.updateCatInformation(editCat, image);
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getErrors());
            model.addAttribute("editCat", editCat);
            return "editCat";
        }

        return "redirect:/myProfile";
    }

    @PostMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/deleteProfile/{id}")
    public String handleDeleteProfile(HttpSession session, Model model, @PathVariable int id) {
        memberService.deleteProfile(session.getAttribute("loggedInUser")
                != null ? (Member) session.getAttribute("loggedInUser") : null);

        session.invalidate();
        return "redirect:/";
    }

}
