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
@RequestMapping("/bloodstocks")
public class BloodStockController {

    @Autowired
    private BloodStockServices bloodStockServices;

    // Display all BloodStock records
    @GetMapping("/all")
    public String getAllBloodStocks(Model model) {
        List<BloodStock> bloodStocks = bloodStockServices.getAllBloodStocks();
        model.addAttribute("bloodStocks", bloodStocks);
        return "bloodstock-list";
    }

    // Save a new BloodStock
    @PostMapping
    public String createBloodStock(BloodStock bloodStock) {
        bloodStockServices.saveBloodStock(bloodStock);
        return "redirect:/bloodstocks/all";
    }

    // Display the update form for a specific BloodStock
    @GetMapping("/update")
    public String updateBloodStockForm(@RequestParam String bloodGroup, @RequestParam String bestBefore, Model model) {
        List<BloodStock> bloodStocks = bloodStockServices.findBloodStockByGroupAndDate(bloodGroup, bestBefore);
        if (!bloodStocks.isEmpty()) {
            model.addAttribute("bloodStock", bloodStocks.get(0)); // Assume one result
            return "bloodstock-update";
        }
        return "redirect:/bloodstocks/all";
    }

    // Save the updated BloodStock details
    @PostMapping("/update")
    public String updateBloodStock(@RequestParam String originalBloodGroup,
                                   @RequestParam String originalBestBefore,
                                   BloodStock bloodStock) {
        bloodStockServices.updateBloodStock(bloodStock, originalBloodGroup, originalBestBefore);
        return "redirect:/bloodstocks/all";
    }

    // Delete a BloodStock by bloodGroup and bestBefore
    @PostMapping("/delete")
    public String deleteBloodStock(@RequestParam String bloodGroup, @RequestParam String bestBefore) {
        bloodStockServices.deleteBloodStock(bloodGroup, bestBefore);
        return "redirect:/bloodstocks/all";
    }
}
