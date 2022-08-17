package dev.cs.onlineshopping.controllers;
import dev.cs.onlineshopping.models.Product;
import dev.cs.onlineshopping.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    // display all products for the customer - can also see the details or add to cart
    @GetMapping()
    public String showAllProducts(HttpServletRequest request, Model model) {
        int page = 0;
        int size = 8;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("products", productService.showAllProducts(PageRequest.of(page, size)));
        return "productlist";
    }
    // dispaly products by page number
    @GetMapping("/page")
    public String showAllProductByPage(HttpServletRequest request, @RequestParam("page") int page, Model model) {
        int size = 10;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("products", productService.showAllProducts(PageRequest.of(page, size)));
        return "productlist";
    }
    // Customers can see the details of product if they need
    @GetMapping("/detail/{productcode}")
    public String showProductDetail(@PathVariable String productcode, Model model) {
        model.addAttribute("product", productService.showProductDetail(productcode));
        return "productdetail";
    }
    // customers can add products to cart just before they are committed to buy
    @GetMapping("/cart/{productcode}")
    public void addItemToCart(@PathVariable String productcode, HttpServletResponse response) throws IOException {
        productService.addItemToCart(productService.showProductDetail(productcode));
        response.sendRedirect("/product/");
    }
    // customers can see what they have in their cart
    @GetMapping("/mycart")
    public String showItemInCart(Model model) {
        model.addAttribute("cc", productService.showCartItems());
        return "productcart";
    }
    // customers can see what they have in their cart
    @GetMapping("/emptycart")
    public void emptyCart(HttpServletResponse response) throws IOException {
        productService.clearCart();
        response.sendRedirect("/product/mycart");
    }
    // customers car remove products from cart
    @GetMapping("/removeproduct/{productcode}")
    public void removeItemfromCart(HttpServletResponse response, @PathVariable String productcode) throws IOException {
        productService.removeItemfromCart(productcode);
        response.sendRedirect("/product/mycart");
    }
    // Admins will see products by defaultgfg
    @GetMapping("/admin")
    public String productDashboard(HttpServletRequest request, Model model) {
        int page = 0;
        int size = 8;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("products", productService.showAllProducts(PageRequest.of(page, size)));
        return "adminproduct";
    }
    @GetMapping("/admin/page")
    public String productDashboardByPage(HttpServletRequest request, @RequestParam("page") int page, Model model) {
        int size = 10;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("products", productService.showAllProducts(PageRequest.of(page, size)));
        return "adminproduct";
    }
    // Add
    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "productadd";
    }
    @PostMapping("/add")
    public String addingProduct(@Valid @ModelAttribute("product") Product product, BindingResult result,
                                Model model) {
        model.addAttribute("product", product);
        if (result.hasErrors()) {
            return "productadd";
        } else {
            // productRepository .saveUser(user);
            productService.saveProduct(product);
            model.addAttribute("message", "Product was created");
        }
        return "redirect:/product/admin";
    }
    // Crud operations product
    // Delete
    // Update

}