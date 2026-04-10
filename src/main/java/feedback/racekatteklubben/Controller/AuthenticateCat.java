package feedback.racekatteklubben.Controller;

import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Model.Member;
import feedback.racekatteklubben.Service.CatService;
import feedback.racekatteklubben.Service.Validation.ValidateCat;
import feedback.racekatteklubben.Service.Validation.ValidationResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticateCat {

    private final ValidateCat validateCat;
    private CatService  catService;

    public AuthenticateCat(CatService catService, ValidateCat validateCat) {
        this.catService = catService;
        this.validateCat = validateCat;
    }


    @GetMapping("/registerKat")
    public String processRegisterCat(Model model){
        model.addAttribute("cat", new Cat());
        return "registerCat";
    }

    @PostMapping("/registerKat")
    public String handleRegisterCat(@ModelAttribute Cat cat, Model model, HttpSession session){
       Member loggedInUser = (Member) session.getAttribute("loggedInUser");

       if (loggedInUser == null){
           return "redirect:/login";
       }

       cat.setMemberID(loggedInUser.getMemberID());

       ValidationResult valResult = validateCat.validateRegisterCat(cat);

       if(valResult.hasErrors()){
            model.addAttribute("errors", valResult.getErrors());
            return "registerCat";
       }

        catService.registerNewCat(cat);

        return "redirect:/registerCat/profile";
    }


}
