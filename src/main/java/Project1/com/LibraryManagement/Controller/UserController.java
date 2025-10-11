package Project1.com.LibraryManagement.Controller;

import Project1.com.LibraryManagement.Entity.Roles;
import Project1.com.LibraryManagement.Entity.Users;
import Project1.com.LibraryManagement.Repository.UserRepos;
import Project1.com.LibraryManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    public UserRepos usersRepos;
    @Autowired
    public UserService userService;
    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping("user/login")
    public String login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)){
            return "redirect:/home";  //return home page when login success
        }
        return "user/login"; //when fail return login page
    }

    @PostMapping("user/login")
    public String processLogin(Model model, Users users){
        Optional<Users> existsUsers = usersRepos.findByEmail(users.getEmail());
        if(existsUsers.isPresent()){
            Users dbUsers = existsUsers.get();
            if(passwordEncoder.matches(users.getPassword(),dbUsers.getPassword())){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        dbUsers.getEmail(),
                        dbUsers.getPassword(),
                        List.of(new SimpleGrantedAuthority("USER"))
                );
                //Set Security Context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                return "redirect:user/login";

            }
            else {
                model.addAttribute("ErrorLogin","Email Or Password Is Not Correct");
            }

        }
        else {
            model.addAttribute("Error","Email Or Password Is Not Correct");
        }
        return "user/login";
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
