package dk.binfo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import dk.binfo.models.User;
import dk.binfo.services.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	// Countdown
	@RequestMapping(value={"/"}, method = RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping(value={"/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}


	@RequestMapping(value = {"/accessDenied"}, method = RequestMethod.GET)
	public ModelAndView accessDenied() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/accessDenied");
		return modelAndView;
	}

	
	@RequestMapping(value={"/registration", "/register"}, method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user",  new User());
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user", "Der eksisterer allerede en bruger med den angivne email");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.register(user);
			modelAndView.addObject("successMessage", "SUCCES!: Du har tilføjet en ny bruger.");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
		}
		return modelAndView;
	}

	@RequestMapping(value= "/home", method = RequestMethod.GET)
	public ModelAndView userHome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);
		modelAndView.addObject("userMessage","Du er logget ind");
		modelAndView.setViewName("/home");
		return modelAndView;
	}
	
	@RequestMapping(value={"/settings/{email:.+}"}, method = RequestMethod.GET)
	public ModelAndView settings(@PathVariable String email){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);
		modelAndView.addObject("userForm",user);
		modelAndView.setViewName("/settings");
		return modelAndView;
	}

	@RequestMapping(value="/settings/{email:.+}", method=RequestMethod.POST)
	public ModelAndView editPhoneSettings(@ModelAttribute @Valid User userinfo, BindingResult bindingResult, @PathVariable String email){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);

		if (bindingResult.hasErrors())
		{
			modelAndView.setViewName("/settings/{email}");
		}
		modelAndView.setViewName("redirect:/settings/{email}");
		userService.updateUserSettings(userinfo);
		return modelAndView;
	}

	@RequestMapping("/users/add")
	public ModelAndView adminCreateNewUser(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);
		modelAndView.addObject("users", new User());
		modelAndView.setViewName("/users/add");
		return modelAndView;
	}

	@RequestMapping(value = "/users/add", method = RequestMethod.POST)
	public ModelAndView adminCreateUser(@Valid User users, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);
		User userExists = userService.findUserByEmail(users.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user", "Der eksisterer allerede en bruger med den angivne email");
		}
		else {
			userService.adminRegisterUser(users);
			modelAndView.addObject("successMessage", "SUCCES!: Du har tilføjet en ny bruger.");
			modelAndView.addObject("users", new User());
			modelAndView.setViewName("/users/add");
		}
		return modelAndView;
	}


	@RequestMapping("/users")
	public ModelAndView showUsers() {
		ModelAndView modelAndView = new ModelAndView("/users", "users", userService.findAll());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("user", user);
		modelAndView.setViewName("/users");
		return modelAndView;
	}
	
}
