package ru.dzmakats.springbootmvcexample.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.dzmakats.springbootmvcexample.entity.Role;
import ru.dzmakats.springbootmvcexample.entity.User;
import ru.dzmakats.springbootmvcexample.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@SessionAttributes({"currentUser", "roles"})
public class UserController {

    private final UserService userService;

    @ModelAttribute
    List<Role> roles() {
        return List.of(Role.values());
    }

    @GetMapping("/")
    public ModelAndView showAllUsers(
            ModelAndView view,
            List<Role> roles,
            HttpSession session,
            @RequestHeader String accept
    ) {
        session.setAttribute("roles", roles);
        view.addObject("users", userService.findAll());
        view.setViewName("userpage");
        return view;
    }


    @GetMapping("/{id}/")
    public ModelAndView showOneUserAndUsers(
            ModelAndView view,
            @PathVariable Long id
    ) {
        Optional<User> optionalUser = userService.get(id);
        if (optionalUser.isPresent()) {
            view.addObject("user", optionalUser.get());
            view.addObject("users", userService.findAll());
        }
        view.setViewName("userpage");
        return view;
    }

    @PostMapping("/{id}/")
    public String updateOrDeleteUser(
            User user,
            Model model,
            @RequestParam(required = false) String deleteUser
    ) {
        if (Objects.nonNull(deleteUser)) {
            userService.delete(user);
            return "redirect:/users/";
        } else {
            userService.save(user);
            return "redirect:/users/%d/".formatted(user.getId());
        }
    }

    @PostMapping("/")
    public String processNewUserOrLogin(
            User user,
            HttpSession session,
            @RequestParam(required = false) String createUser
    ) {
        if (Objects.nonNull(createUser)) {
            user = userService.save(user);
            return "redirect:/users/%d/".formatted(user.getId());
        } else {
            log.info(" user {} login ", user);
            session.setAttribute("currentUser", user);
            return "redirect:/users/";
        }
    }
}
