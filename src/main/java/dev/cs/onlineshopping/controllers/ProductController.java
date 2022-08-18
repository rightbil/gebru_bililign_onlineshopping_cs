package dev.cs.onlineshopping.controllers;
import dev.cs.onlineshopping.models.Product;
import dev.cs.onlineshopping.models.ProductLine;
import dev.cs.onlineshopping.services.ProductLineService;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductLineService productLineService;
    @Autowired
    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    // display all products for the customer - can also see the details or add to cart
    @GetMapping
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
        System.out.println("/prodcut/add get is called" + productLineService.findAllProductLine().size());
//
//        Set<String> productcodes = new HashSet<>();
//        for (ProductLine pl : productLineService.findAllProductLine()) {
//            productcodes.add(pl.getProductLine());
//
//        }
        model.addAttribute("product", new Product());
        model.addAttribute("productlines", productLineService.findAllProductLine());
        return "productadd";
    }
    @PostMapping("/add")
   public String addingProduct(@ModelAttribute("product") Product product,
                               BindingResult result
//                                Model model
                              ) {

        System.out.println("/product/add POST is called" + product.getProductCode() );
        //TODO add productline later on
//        model.addAttribute("product", product);
//        if (result.hasErrors()) {
//            return "productadd";
//        } else {
            Product p =  productService.findProductByCode(product.getProductCode());
            if(p!=null) {
                result.rejectValue("prodcutCode", "A product  exists with this code");
            }
            if(result.hasErrors())
            {
                return "productadd";
            }
//            p.setProductCode(product.getProductCode());
//            p.setProductName(product.getProductName());
//            p.setProductLine(product.getProductLine());
//            p.setProductScale(product.getProductScale());
//            p.setProductVendor(product.getProductVendor());
//            p.setProductDescription(product.getProductDescription());
//            p.setQuantityInStock(product.getQuantityInStock());
//            p.setBuyPrice(product.getBuyPrice());
//            p.setMSRP(product.getMSRP());
            productService.saveProduct(product);
            System.out.println("Product was created");
//           // model.addAttribute("message", "Product was created");
//        }
        return "redirect:/product/admin";
    }
    @PostMapping("/order")
    public String saveMyOrders() {
// use session get the  userid
// get customerid using user id
// get productcode and quanity form mymap {all productcode , with quantities }
// insert productCode to order table
// capture the orderId (Pk)
// pair orderId with each productcode from mymap and orderID() insert into orderdetails
// at the same time update product quantitry
// empty mymap
        return "redirect:/product/admin";
    }
    // Crud operations product
    // Delete
    // Update

}