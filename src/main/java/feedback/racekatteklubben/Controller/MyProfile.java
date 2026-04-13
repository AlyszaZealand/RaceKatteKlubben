package feedback.racekatteklubben.Controller;

import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Model.Member;
import feedback.racekatteklubben.Service.CatService;
import feedback.racekatteklubben.Service.MemberService;
import feedback.racekatteklubben.Service.Validation.ValidationResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Cat editCat = catService.getCatByID(id).orElse(null);

        if (editCat == null) {
            return "redirect:/myProfile";
        }

        model.addAttribute("editCat", editCat);
        return "editCat";
    }


//    @PostMapping("/editCat/{id}")
//    public String updateCat(@PathVariable int id, @ModelAttribute Cat editCat) {
//        editCat.setCatID(id); // force set the ID from the URL, not the form
//
//        catService.updateCatInformation(editCat);
//        return "redirect:/myProfile";
//    }

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
        //TODO Global error måske?.
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







    @PostMapping("/editCat/{id}")
    public String updateCat(@PathVariable int id,
                            @ModelAttribute Cat editCat,
                            @RequestParam(value = "image", required = false) MultipartFile image,
                            Model model) throws IOException {

        editCat.setCatID(id);

        if (image != null && !image.isEmpty()) {
            // Convert to Base64 and store directly in DB
            String base64 = Base64.getEncoder().encodeToString(image.getBytes());
            String mimeType = image.getContentType();
            editCat.setImageName("data:" + mimeType + ";base64," + base64);
        } else {
            // Keep existing image if no new one uploaded
            Cat existing = catService.getCatByID(id).orElse(null);
            if (existing != null) {
                editCat.setImageName(existing.getImageName());
            }
        }

        ValidationResult result = catService.updateCatInformation(editCat);
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getErrors());
            model.addAttribute("editCat", editCat);
            return "editCat";
        }

        return "redirect:/myProfile";
    }
}
