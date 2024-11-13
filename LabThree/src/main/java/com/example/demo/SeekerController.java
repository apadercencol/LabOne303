package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/seekers")
public class SeekerController {

    @Autowired
    private SeekerServices seekerServices;

    // Display all seekers
    @GetMapping("/all")
    public String getAllSeekers(Model model) {
        List<Seeker> seekers = seekerServices.getAllSeekers();
        model.addAttribute("seekers", seekers);
        return "seeker-list";  // This should correspond to a Thymeleaf template
    }

    // Save a new seeker
    @PostMapping
    public String createSeeker(Seeker seeker) {
        seekerServices.saveSeeker(seeker);
        return "redirect:/seekers/all";
    }

    // Display update form for a seeker
    @GetMapping("/update")
    public String updateSeekerForm(@RequestParam String firstName, @RequestParam String lastName, Model model) {
        List<Seeker> seekers = seekerServices.findSeekersByName(firstName, lastName);
        if (!seekers.isEmpty()) {
            model.addAttribute("seeker", seekers.get(0)); // Assuming only one result
            return "seeker-update";  // This should correspond to an update form template
        }
        return "redirect:/seekers/all";
    }

    // Update a seeker record
    @PostMapping("/update")
    public String updateSeeker(@RequestParam String originalFirstName,
                               @RequestParam String originalLastName,
                               Seeker seeker) {
        seekerServices.updateSeeker(seeker, originalFirstName, originalLastName);
        return "redirect:/seekers/all";
    }

    // Delete a seeker by first name and last name
    @PostMapping("/delete")
    public String deleteSeeker(@RequestParam String firstName, @RequestParam String lastName) {
        seekerServices.deleteSeeker(firstName, lastName);
        return "redirect:/seekers/all";
    }
}
