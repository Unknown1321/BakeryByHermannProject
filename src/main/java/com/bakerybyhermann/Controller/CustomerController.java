package com.bakerybyhermann.Controller;
import com.bakerybyhermann.Model.Address;
import com.bakerybyhermann.Model.Customer;
import com.bakerybyhermann.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/show-customer")
    public String getCustomer(Model model){

        model.addAttribute("customersList",customerService.fetchAll());
        return "customer/show-customer";
    }

    @GetMapping("/new-customer")
    public String createCustomer(){
        return "customer/new-customer";
    }

    @PostMapping("/new-customer")
    public String createCustomer(@ModelAttribute Customer customer, @ModelAttribute Address address){
        customer.setAddress(address);
        customerService.addNew(customer, address);
        return "redirect:/show-customer";
    }

    @GetMapping("/customer/{customerId}")
    public String deleteCustomer(@PathVariable("customerId") int customerId){
        customerService.delete(customerId);
        return "redirect:/";
    }

    @GetMapping("/update-customer/{id}")
    public String updateCustomer(@PathVariable("id") int id, Model model){
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "customer/update-customer";
    }

    @PostMapping("/update-customer")
    public String updateCustomer(@ModelAttribute Customer customer, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        customerService.updateById(customer.getPersonId(), customer);
        return "redirect:"+referer;
    }

    @GetMapping("/view-customer/{customerId}")
    public String viewOne(@PathVariable("customerId") int id, Model model){
        model.addAttribute("oneCustomer", customerService.findById(id));
        return "customer/one-customer";
    }
}
