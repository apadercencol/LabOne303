package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {

    @RequestMapping("/")
    public String showOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "index";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(@ModelAttribute Order order, Model model) {
        // Set the price based on the selected phone model
        switch (order.getModel()) {
            case "iPhone XR":
                order.setPrice(979.00);
                break;
            case "iPhone 11 Pro":
                order.setPrice(1199.00);
                break;
            case "Samsung Galaxy S20":
                order.setPrice(999.00);
                break;
            case "Samsung Galaxy 20 Plus":
                order.setPrice(1099.00);
                break;
            case "Pixel 8 Pro":
                order.setPrice(799.99);
                break;
            case "Pixel 9 Pro":
                order.setPrice(699.99);
                break;
            case "Evolve X":
                order.setPrice(499.99);
                break;
            case "Key2":
                order.setPrice(599.99);
                break;
            default:
                order.setPrice(0.00); // Default price if model is not recognized
                break;
        }

        // Autofill the second address field with "None" if not provided
        if (order.getStreetAddress2() == null || order.getStreetAddress2().isEmpty()) {
            order.setStreetAddress2("None");
        }

        // Add the order object to the model
        model.addAttribute("order", order);
        return "show-order"; // Redirect to the show-order.html template
    }
}
