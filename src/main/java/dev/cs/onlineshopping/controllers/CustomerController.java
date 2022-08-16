package dev.cs.onlineshopping.controllers;
import dev.cs.onlineshopping.services.CustomerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
@Controller
//@RequestMapping("/")
public class CustomerController {
   private final CustomerService customerService;
    public CustomerController( CustomerService customerService) {
            this.customerService = customerService;
    }
    @GetMapping("/")
    public String findAllCustomers(HttpServletRequest request, Model model) {
        int page = 0;
        int size = 8;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        model.addAttribute("customers", customerService.listAllCustomers(PageRequest.of(page, size)));
        return "customers";
    }
    @GetMapping("/page")
    public String findAllCustomersByPage(HttpServletRequest request, @RequestParam("page") int page, Model model) {
        //int page = 0;
        int size = 20;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("customers", customerService.listAllCustomers(PageRequest.of(page, size)));
        return "customers";
    }


// localhost:[port]/clearCache
      // autowire cache manager
        // clear all cache using cache manager
//        @RequestMapping(value = "clearCache")
//        @Scheduled(cron = "0 0/30 * * * ?") // every 30 minutes
//        public void clearCache() {
//            for (String name : cacheManager.getCacheNames()) {
//                cacheManager.getCache(name).clear();            // clear cache by name
//            }
//
//    }
//    @GetMapping("/id")
//    public Customer findCustomerById(String customerId) {
//        return customerService.findCustomerById(customerId);
//    }
    // TODO
//    private final CustomerService customerService;
//    public HomeController(CustomerService customerService) {
//        this.customerService = customerService;
//    }

//    @GetMapping("/")
//    public @ResponseBody List<Customer> listAllEmployees() {
//        return customerService.listAllCustomers();
//    }
//
//
//    //01. just use thymeleaf dependency the index page will be  displayed
//    // only OK 200 on post man , on browser hello world
//    @GetMapping("/")
//    public String homepage() {
//        return "index";
//    }
//
//    //02. works the same as above except that the url is /home
//    @GetMapping("/home") // default page even dont need @Controller
//    public String homepage2() {
//        return "index";
//    }
//
//    // 03 @param and valiables
//    //http://localhost:8080/demo/orders/78/?name=Gebru
//    @RequestMapping(value = "/orders/{id}/", method = RequestMethod.GET)
//    @ResponseBody
//    public String getOrder(@PathVariable("id") final String id,
//                           @RequestParam("name") final String name) {
//
//        return "Hey " + name + " your order id is :" + id + " please keep it.";
//    }
//
//    //04 explore named query
//    @GetMapping("/mymessage")
//    public String getMessage(Model model) {
//        model.addAttribute("thatmessage", "Construct your model");
//        System.out.println("here it is ");
//        return "show";
//    }
//
//    //05 formvalidation
//    @RequestMapping("/loadform")
//    public String loadFormPage() {
//        return "formtemplate";
//    }
//
//    @RequestMapping("demo/processform")
//    public String loadFromPage(@RequestParam("login") String login,
//                               @RequestParam("email") String email,
//                               ModelMap model) {
//        model.addAttribute("loginvalue", login);
//        model.addAttribute("emailvalue", email);
//        return "confirm";
//
//    }
//
//    // 06 Work with Customer
//    @GetMapping("/userf3orm")
//    public String loadformpage(Model model) {
//        model.addAttribute("user", new Customer());
//        return "userform";
//    }
//
//    @GetMapping("/modelandview")
//    public ModelAndView modelAndView(@RequestParam("guest") String couch) {
//        Model model = new Model();
//
//
//        ModelAndView mv = new ModelAndView("modelandview");
//
//        mv.addObject("firstname", "Bililing");
//        return mv;
//
//    }

}