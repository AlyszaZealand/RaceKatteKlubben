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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

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

        //Parameter noget nyt "@RequestParam(value = "image", required = false) MultipartFile image"
        //Kaster IOExcpetion
        //Billede validation, som nok skal rykkes et andet sted måske?
    @PostMapping("/registerKat")
    public String handleRegisterCat(@ModelAttribute Cat cat,
                                    @RequestParam(value = "image", required = false) MultipartFile image,
                                    Model model,
                                    HttpSession session) throws IOException {

        Member loggedInUser = (Member) session.getAttribute("loggedInUser");

        if (loggedInUser == null){
            return "redirect:/login";
        }

        cat.setMemberID(loggedInUser.getMemberID());

        // Validering som måske rykkes
        if (image != null && !image.isEmpty()) {
            String base64 = Base64.getEncoder().encodeToString(image.getBytes());
            String mimeType = image.getContentType();
            cat.setImageName("data:" + mimeType + ";base64," + base64);
        }

        ValidationResult valResult = validateCat.validateRegisterCat(cat);

        if(valResult.hasErrors()){
            model.addAttribute("errors", valResult.getErrors());
            model.addAttribute("cat", cat);
            return "registerCat";
        }

        catService.registerNewCat(cat);

        return "redirect:/myProfile";
    }

}
