package dk.binfo.controllers;

import dk.binfo.models.User;
import dk.binfo.services.ListService;
import dk.binfo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class ListController {

    @Autowired
    private UserService userService;

    @Autowired
    private ListService listService;


    @RequestMapping(value={"/lists/connect"})
    public ModelAndView showConnectList() {
        ModelAndView modelAndView = new ModelAndView("/lists/connect", "list", listService
                .generateList(Integer.MAX_VALUE, 1));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        return modelAndView;
    }


    @RequestMapping(value={"/lists/internal"})
    public ModelAndView showInternList() {
        ModelAndView modelAndView = new ModelAndView("/lists/internal", "list", listService
                .generateList(20, 2));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        return modelAndView;
    }

    @RequestMapping(value={"/lists/family"})
    public ModelAndView showFamilyList() {
        ModelAndView modelAndView = new ModelAndView("/lists/family", "list", listService
                .generateList(Integer.MAX_VALUE, 3));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        return modelAndView;
    }

    @RequestMapping(value={"/lists/external"})
    public ModelAndView showExternalList() {
        ModelAndView modelAndView = new ModelAndView("/lists/external", "list", listService
                .generateList(Integer.MAX_VALUE, 4));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        return modelAndView;
    }

    @RequestMapping(value={"/lists/listapartment/{id}"}, method = RequestMethod.GET)
    public ModelAndView showSingleApartmentList(@PathVariable Integer id) {
        System.out.println("test1");
        ModelAndView modelAndView = new ModelAndView("/lists/listapartment/{id}", "list",
                listService.generateSingleApartmentList(Integer.MAX_VALUE, id));
        System.out.println("test2");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println("test3");
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.setViewName("/lists/listapartment/{id}");
        return modelAndView;
    }

}
