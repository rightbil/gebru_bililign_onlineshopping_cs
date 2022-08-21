package dev.cs.onlineshopping.security;
import dev.cs.onlineshopping.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping()
public class MainController {
    @Autowired
    ProductService productService;
    @GetMapping("/")
    public String root() {
        productService.clearVirtualCart();
        return "index";
    }
    @GetMapping("/login")
    public String login(Model model) {
        productService.clearVirtualCart();
        return "login";
    }
    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
}