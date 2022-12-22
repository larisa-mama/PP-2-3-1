package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import web.model.User;
import web.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    //@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    // отображение на стартовой странице таблицы всех user, контроллер отрабатывает первым , потом передает управление сервису
    public String sawUsers(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }

    @GetMapping(value = "/newUser")
    public String makeUser(Model model) {
      model.addAttribute("user", new User());
        return "new_user";
    }
    @PostMapping(value = "/new")
    public String addUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/";
    }
    @GetMapping(value = "/change/{id}")
    public String changeUser(Model model, @PathVariable("id") int id) {
    model.addAttribute("user", userService.getUser(id));
    return "change_user";
    }
    @PostMapping(value = "/update/{id}")
    public String updateUser (@ModelAttribute("user") User user, @PathVariable("id") int id) {
      // user.getId(id);
        userService.edit(id, user);
    return "redirect:/";
    }
    @DeleteMapping(value = "/delete/{id}")
    public String delete (@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/";
    }
}
