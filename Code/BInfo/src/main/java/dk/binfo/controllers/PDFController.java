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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


@Controller
public class PDFController {

    @Autowired
    private UserService userService;

    @Autowired
    private ListService listService;

    @RequestMapping(value={"/lists/connect/pdf"})
    public ModelAndView generateConnectListPDF() {
        System.out.println("\n* Initiating listService.generateList *\n");
        ModelAndView modelAndView = new ModelAndView("/lists/connect", "list", listService
                .generateList(Integer.MAX_VALUE, 1));
        System.out.println("\n* Generating PDF *\n");
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Sammenlaegningsliste.pdf"; // filePath
        listService.generateCompleteListPDF(Integer.MAX_VALUE, 1, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println("\n* Generating onscreen messages *\n");
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping(value={"/lists/internal/pdf"})
    public ModelAndView generateInternListPDF() {
        System.out.println("\n* Initiating listService.generateList *\n");
        ModelAndView modelAndView = new ModelAndView("/lists/internal", "list", listService
                .generateList(Integer.MAX_VALUE, 2));
        System.out.println("\n* Generating PDF *\n");
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Intern_liste.pdf"; // filePath
        listService.generateCompleteListPDF(Integer.MAX_VALUE, 2, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println("\n* Generating onscreen messages *\n");
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping(value={"/lists/family/pdf"})
    public ModelAndView generateFamilyListPDF() {
        System.out.println("\n* Initiating listService.generateList *\n");
        ModelAndView modelAndView = new ModelAndView("/lists/family", "list", listService
                .generateList(Integer.MAX_VALUE, 3));
        System.out.println("\n* Generating PDF *\n");
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Familieliste.pdf"; // filePath
        listService.generateCompleteListPDF(Integer.MAX_VALUE, 3, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println("\n* Generating onscreen messages *\n");
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping(value={"/lists/external/pdf"})
    public ModelAndView generateExternalListPDF() {
        System.out.println("\n* Initiating listService.generateList *\n");
        ModelAndView modelAndView = new ModelAndView("/lists/external", "list", listService
                .generateList(Integer.MAX_VALUE, 4));
        System.out.println("\n* Generating PDF *\n");
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Ekstern_liste.pdf"; // filePath
        listService.generateCompleteListPDF(Integer.MAX_VALUE, 4, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println("\n* Generating onscreen messages *\n");
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject(user);
        return modelAndView;
    }

    @RequestMapping(value={"/lists/listapartmentPDF/{id}"}, method = RequestMethod.GET)
    public ModelAndView showSingleApartmentList(@PathVariable("id") Integer id) {
        System.out.println("\n* Initiating listService.generateSingleApartmentList *\n");
        ModelAndView modelAndView = new ModelAndView("/lists/listapartment", "list",
                listService.generateSingleApartmentList(Integer.MAX_VALUE, id));
        System.out.println("\n* Generating PDF *\n");
        String filePath = "/Users/jensbackvall/Desktop/PDF_TEST/Liste_for_lejlighed_" + id + ".pdf"; // filePath
        listService.generateSingleApartmentPDF(Integer.MAX_VALUE, id, filePath);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        System.out.println("\n* Generating onscreen messages *\n");
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("PDFMessage","PDF er gemt på /Users/jensbackvall/Desktop/PDF_TEST/");
        modelAndView.addObject("HeaderMessage","Viser Liste for Lejlighed nr. " + id);
        modelAndView.addObject(user);
        return modelAndView;
    }
}