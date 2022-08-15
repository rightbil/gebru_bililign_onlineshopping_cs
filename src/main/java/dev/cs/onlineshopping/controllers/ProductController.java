package dev.cs.onlineshopping.controllers;
import dev.cs.onlineshopping.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping()
    public String showAllProducts(HttpServletRequest request, Model model) {
        int page = 0;
        int size = 10;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("products", productService.showAllProducts(PageRequest.of(page, size)));
        return "products";
    }
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
        return "products";
    }
    // Product Details
    @GetMapping("/detail/{productcode}")
    public String showProductDetail(@PathVariable String productcode, Model model) {
         model.addAttribute("product", productService.showProductDetail(productcode));
        return "productdetail";
    }
    @GetMapping("/cart/{productcode}")
    public void addItemToCart(@PathVariable String productcode, HttpServletResponse response) throws IOException {
        productService.addItemToCart(productService.showProductDetail(productcode));
        response.sendRedirect("/product/");
    }
    @GetMapping("/mycart")
    public String showItemInCart(Model model) {
        model.addAttribute("cc", productService.showCartItems());
        return "productcart";
    }
    @GetMapping("/emptycart")
    public void emptyCart(HttpServletResponse response) throws IOException {
        productService.clearCart();
        response.sendRedirect("/product/mycart");
    }

    @GetMapping("/removeproduct/{productcode}")
    public void removeItemfromCart(HttpServletResponse  response, @PathVariable String productcode) throws IOException {
        productService.removeItemfromCart(productcode);
        response.sendRedirect("/product/mycart");
    }










}