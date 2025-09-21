package Project1.com.LibraryManagement.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {
    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
