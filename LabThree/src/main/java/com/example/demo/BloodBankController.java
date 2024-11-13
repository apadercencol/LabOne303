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
@RequestMapping("/bloodbanks")
public class BloodBankController {

    @Autowired
    private BloodBankServices bloodBankServices;

    // Display all BloodBank records
    @GetMapping("/all")
    public String getAllBloodBanks(Model model) {
        List<BloodBank> bloodBanks = bloodBankServices.getAllBloodBanks();
        model.addAttribute("bloodBanks", bloodBanks);
        return "bloodbank-list";
    }

    // Save a new BloodBank
    @PostMapping
    public String createBloodBank(BloodBank bloodBank) {
        bloodBankServices.saveBloodBank(bloodBank);
        return "redirect:/bloodbanks/all";
    }

    // Display the update form for a specific BloodBank
    @GetMapping("/update")
    public String updateBloodBankForm(@RequestParam String bloodbankName, @RequestParam String city, Model model) {
        List<BloodBank> bloodBanks = bloodBankServices.findBloodBankByNameAndCity(bloodbankName, city);
        if (!bloodBanks.isEmpty()) {
            model.addAttribute("bloodBank", bloodBanks.get(0)); // Assume one result
            return "bloodbank-update";
        }
        return "redirect:/bloodbanks/all"; // Redirect if no blood bank found
    }

    // Save the updated BloodBank details
    @PostMapping("/update")
    public String updateBloodBank(@RequestParam String originalBloodbankName,
                                  @RequestParam String originalCity,
                                  BloodBank bloodBank) {
        bloodBankServices.updateBloodBank(bloodBank, originalBloodbankName, originalCity);
        return "redirect:/bloodbanks/all";
    }

    // Delete a BloodBank by bloodbankName and city
    @PostMapping("/delete")
    public String deleteBloodBank(@RequestParam String bloodbankName, @RequestParam String city) {
        bloodBankServices.deleteBloodBank(bloodbankName, city);
        return "redirect:/bloodbanks/all";
    }
}
