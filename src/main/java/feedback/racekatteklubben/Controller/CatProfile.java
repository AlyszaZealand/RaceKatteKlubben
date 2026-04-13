package feedback.racekatteklubben.Controller;

import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Service.CatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CatProfile {

    private CatService catService;
    public CatProfile(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/CatProfile/{id}")
    public String CatProfile(Model model, @PathVariable int id) {
        Cat cat = catService.getCatByID(id).orElse(null);

        model.addAttribute("cat", cat);
        return "CatProfile";
    }



}
