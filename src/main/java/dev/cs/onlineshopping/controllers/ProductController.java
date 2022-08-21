package dev.cs.onlineshopping.controllers;
import dev.cs.onlineshopping.dtos.ProductCartDTO;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        model.addAttribute("products", productService.listAllStoreProducts(PageRequest.of(page, size)));
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
        model.addAttribute("products", productService.listAllStoreProducts(PageRequest.of(page, size)));
        return "productlist";
    }
    // Customers can see the details of product if they need
    @GetMapping("/detail/{productcode}")
    public String showProductDetail(@PathVariable String productcode, Model model) {
        model.addAttribute("product", productService.getProductByProductCode(productcode));
        return "productdetail";
    }
    // customers can add products to cart just before they are committed to buy
    @GetMapping("/cart/{productcode}")
    public void addItemToCart(@PathVariable String productcode, HttpServletResponse response) throws IOException {
//        Product underChange = productService.getProductByProductCode(productcode);
        productService.addItemToVirtualCart(productcode);
//        productService.testDisplayCartContent();
        productService.decreaseStockQuantity(productcode);
        response.sendRedirect("/product/");
    }
    // customers can see what they have in their cart
    @GetMapping("/mycart")
    public String showItemInCart(Model model) {
        List<ProductCartDTO> obj = productService.listAllCartItems();
        if (obj != null) {
            model.addAttribute("cc", productService.listAllCartItems());
        }
        return "productcart";

    }
    // customers can see what they have in their cart
    @GetMapping("/emptycart")
    public void cleanVirtualCart(HttpServletResponse response) throws IOException {
        productService.clearVirtualCart();
        response.sendRedirect("/product/mycart");
    }
    // customers car remove products from cart
    @GetMapping("/removeproduct/{productcode}")
    public void removeItemfromCart(HttpServletResponse response, @PathVariable String productcode) throws IOException {
        productService.removeItemFromVirtualCart(productcode);
        response.sendRedirect("/product/mycart");
    }
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
        model.addAttribute("products", productService.listAllStoreProducts(PageRequest.of(page, size)));
//        System.out.println("from product admin controller calls" + productService.findProductByCode("S00001"));
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
        model.addAttribute("products", productService.listAllStoreProducts(PageRequest.of(page, size)));
        return "adminproduct";
    }
    // Add

    @GetMapping("/cart/reduce/{productcode}")
    public String reduceCartQuantity(@PathVariable String productcode, Model model) throws IOException {
        productService.reduceQuantityFromVirtualCart(productcode);
        productService.increaseStockQuantity(productcode);
        List<ProductCartDTO> obj = productService.listAllCartItems();
        if (obj != null) {
            model.addAttribute("cc", productService.listAllCartItems());
        }
        return "productcart";
    }
    @GetMapping("/cart/more/{productcode}")
    public String moreCartQuantity(@PathVariable String productcode, Model model) throws IOException {
        Product underChange = productService.getProductByProductCode(productcode);
        productService.addItemToVirtualCart(productcode);
        productService.testDisplayCartContent();
        productService.decreaseStockQuantity(productcode);
        List<ProductCartDTO> obj = productService.listAllCartItems();
        if (obj != null) {
            model.addAttribute("cc", productService.listAllCartItems());
        }
        return "productcart";
    }
    @GetMapping("/cart/remove/{productcode}")
    public String removeProductFromCart(@PathVariable String productcode, Model model) throws IOException {
        short returnQuantity = productService.getQuantityFromVirtualCart(productcode);
        productService.increaseStockQuantityBatch(returnQuantity, productcode);
        productService.removeItemFromVirtualCart(productcode);
        List<ProductCartDTO> obj = productService.listAllCartItems();
        if (obj != null) {
            model.addAttribute("cc", productService.listAllCartItems());
        }
        return "productcart";

    }
    @PostMapping("/add")
    public String saveProduct(@ModelAttribute("product") Product product, BindingResult result, Model model) {
       // System.out.println("/product/add POST is called" + product.getProductCode());
        //TODO add productline later on
//        model.addAttribute("product", product);
//        if (result.hasErrors()) {
//            return "productadd";
//        } else {
//        Product p = productService.findProductByCode(product.getProductCode());
//        if (p != null) {
//            result.rejectValue("prodcutCode", "A product  exists with this code");
//        }
//        if (result.hasErrors()) {
//            return "productadd";
//        }
        productService.saveProduct(product);
        System.out.println("Product was created saved ");
        return "redirect:/product/admin";

    }
    @GetMapping("/delete/{productcode}")
    public String deleteProduct(@PathVariable String productcode) {
        productService.deleteProduct(productcode);
        return "redirect:/product/admin";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        Set<String> productcodes = new HashSet<>();
        for (ProductLine pl : productLineService.findAllProductLine()) {
            productcodes.add(pl.getProductLine());

        }
        model.addAttribute("product", new Product());
        model.addAttribute("productlines", productcodes);
        return "productadd";
    }
    @GetMapping("/edit/{productcode}")
    public ModelAndView updatProduct(@PathVariable("productcode") String productcode) {
        ModelAndView editview = new ModelAndView("productedit");

        Set<String> productcodes = new HashSet<>();
        for (ProductLine pl : productLineService.findAllProductLine()) {
            productcodes.add(pl.getProductLine());

        }
        editview.addObject("productlines", productcodes);
        Product product = productService.findProductByProductCode(productcode);
        editview.addObject("product", product);
        return editview;
    }
    @GetMapping("/order")
    public String saveMyOrders(HttpServletRequest request) {
        productService.processMyOrders(request);
        return "redirect:/product/";
    }

}