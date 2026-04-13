package feedback.racekatteklubben.Controller;

import feedback.racekatteklubben.Model.Cat;
import feedback.racekatteklubben.Model.Member;
import feedback.racekatteklubben.Service.CatService;
import feedback.racekatteklubben.Service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class Homepage {

    MemberService memberService;
    CatService catService;
    public Homepage(MemberService memberService, CatService catService) {
        this.memberService = memberService;
        this.catService = catService;
    }

    @GetMapping("/")
    public String homepage() {
        return "homepage";
    }


    @GetMapping("/MemberList")
    public String memberListPage(Model model) {
        List<Member> members = memberService.getAllMembers();
        model.addAttribute("members", members);
        return "memberList";
    }




    @GetMapping("CatList")
    public String catListPage(Model model) {
        List<Cat> cats = catService.getAllCats();
        model.addAttribute("cats", cats);
        return "catList";
    }





}
