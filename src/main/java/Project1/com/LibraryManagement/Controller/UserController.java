package Project1.com.LibraryManagement.Controller;

import Project1.com.LibraryManagement.Entity.Roles;
import Project1.com.LibraryManagement.Entity.Users;
import Project1.com.LibraryManagement.Repository.UserRepos;
import Project1.com.LibraryManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DateTimeException;
import java.time.LocalDate;

@Controller
public class UserController {
    @Autowired
    public UserRepos usersRepos;
    @Autowired
    public UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
    @PostMapping("/saveUsers")
    public String saveUsers(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("fullName") String fullName,
            @RequestParam("phoneNumber") int phoneNumber,
            @RequestParam("adress") String address,
            @RequestParam("dayofBirth")int dayofBirth,
            @RequestParam("monthofBirth")int monthofBirth,
            @RequestParam("yearofBirth")int yearofBirth,
            Model model){
        try {
            if (usersRepos.existsByEmail(email)){
                model.addAttribute("Error","This Account Is Exists");
                return "/signup";
            }
            LocalDate dateOfBirth;
            try {
                dateOfBirth = LocalDate.of(yearofBirth,monthofBirth,dayofBirth);
            }catch (DateTimeException ex){
                model.addAttribute("ErrorDateTime","Invalid Date Of Birth");
                return "signup";
            }
            Users users = new Users();
            users.setEmail(email);
            users.setPassword(password);
            users.setFullName(fullName);
            users.setPhoneNumber(phoneNumber);
            users.setAddress(address);
            users.setDateOfBirth(dateOfBirth);
            users.setRoles(Roles.USERS);
            userService.saveUser(users);
            return "redirect:/login";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("ErrorLogin","Error Uploading File"+e.getMessage());
            return "/signup";
        }
    }


}
