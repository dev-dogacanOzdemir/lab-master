package com.doa.labmasterv10.Controller;

import com.doa.labmasterv10.Entities.Laborant;
import com.doa.labmasterv10.Service.LaborantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/laborants")
public class LaborantController {
    @Autowired
    private LaborantService laborantService;

    @GetMapping
    public ModelAndView getAllLaborants() {
        List<Laborant> laborants = laborantService.getAllLaborants();
        ModelAndView mav = new ModelAndView("laborants");
        mav.addObject("laborants", laborants);
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView getLaborantById(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("laborantDetails");
        laborantService.getLaborantById(id).ifPresent(mav::addObject);
        return mav;
    }

    @GetMapping("/createLaborant")
    public ModelAndView newLaborantForm() {
        ModelAndView mav = new ModelAndView("createLaborant");
        mav.addObject("laborant", new Laborant());
        return mav;
    }

    @PostMapping("/saveLaborant")
    public ModelAndView saveLaborant(@Valid @ModelAttribute Laborant laborant, BindingResult result) {
        ModelAndView mav = new ModelAndView("createLaborant");
        if (result.hasErrors()) {
            mav.addObject("laborant", laborant);
            return mav;
        }
        try {
            laborantService.saveLaborant(laborant);
            mav.setViewName("redirect:/laborants");
        } catch (IllegalArgumentException e) {
            mav.addObject("errorMessage", e.getMessage());
            mav.addObject("laborant", laborant);
        }
        return mav;
    }

    @GetMapping("/editLaborant/{id}")
    public ModelAndView editLaborantForm(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("editLaborant");
        laborantService.getLaborantById(id).ifPresent(laborant -> mav.addObject("laborant", laborant));
        return mav;
    }

    @PostMapping("/updateLaborant")
    public ModelAndView updateLaborant(@Valid @ModelAttribute Laborant laborant, BindingResult result) {
        ModelAndView mav = new ModelAndView("editLaborant");
        if (result.hasErrors()) {
            mav.addObject("laborant", laborant);
            return mav;
        }
        try {
            laborantService.updateLaborant(laborant);
            mav.setViewName("redirect:/laborants");
        } catch (IllegalArgumentException e) {
            mav.addObject("errorMessage", e.getMessage());
            mav.addObject("laborant", laborant);
        }
        return mav;
    }

    @GetMapping("/delete/{id}")
    public String deleteLaborant(@PathVariable Long id) {
        laborantService.deleteLaborant(id);
        return "redirect:/laborants";
    }
}
