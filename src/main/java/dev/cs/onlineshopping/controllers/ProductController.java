package dev.cs.onlineshopping.controllers;
import dev.cs.onlineshopping.dtos.ProductVirtualCartDTO;
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
/****
 * Product controller is the main business logic
 */
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductLineService productLineService;
    @Autowired
    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    /****
     *display all products for the customer - can also see the details or add to cart
     */
    @GetMapping()
    public String showAllProducts(HttpServletRequest request, Model model) {
        int page = 0;
        int size = 5;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("products", productService.listAllProducts(PageRequest.of(page, size)));
        return "productlist";
    }
    /****
     *    dispaly products by page number
     */
    @GetMapping("/page")
    public String showAllProductByPage(HttpServletRequest request, @RequestParam("page") int page, Model model) {
        int size = 5;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("products", productService.listAllProducts(PageRequest.of(page, size)));
        return "productlist";
    }
    /****
     *
     * @param productcode product code to see its detail
     * @param model constructs the object to the view
     * @return view page to display  the data
     */
    // dispaly products by page number
    @GetMapping("/detail/{productcode}")
    public String showProductDetail(@PathVariable String productcode, Model model) {
        model.addAttribute("product", productService.getProductByProductCode(productcode));
        return "productdetail";
    }
    /****
     *add user selected items to shopping cart
     * @param productcode
     * @param response
     * @throws IOException
     */
    @GetMapping("/cart/{productcode}")
    public void addItemToCart(@PathVariable String productcode, HttpServletResponse response) throws IOException {
        productService.addItemToVirtualCart(productcode);
        productService.decreaseStockQuantity(productcode);
        response.sendRedirect("/product/");
    }
    /****
     * display cart items selected by the user
     * @param model
     * @return
     */
    // customers can see what they have in their cart
    @GetMapping("/mycart")
    public String showItemInCart(Model model) {
        List<ProductVirtualCartDTO> obj = productService.listAllCartItems();
        if (obj != null) {
            model.addAttribute("products", productService.listAllCartItems());
        }
        model.addAttribute("total", productService.totalCharges());
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
        int size = 5;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("products", productService.listAllProducts(PageRequest.of(page, size)));
        return "adminproduct";
    }
    @GetMapping("/admin/page")
    public String productDashboardByPage(HttpServletRequest request, @RequestParam("page") int page, Model model) {
        int size = 5;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("products", productService.listAllProducts(PageRequest.of(page, size)));
        return "adminproduct";
    }
    // Add
    @GetMapping("/cart/reduce/{productcode}")
    public String reduceCartQuantity(@PathVariable String productcode, Model model) throws IOException {
        productService.reduceQuantityFromVirtualCart(productcode);
        productService.increaseStockQuantity(productcode);
        List<ProductVirtualCartDTO> obj = productService.listAllCartItems();
        if (obj != null) {
            model.addAttribute("products", productService.listAllCartItems());
        }
        model.addAttribute("total", productService.totalCharges());
        return "productcart";
    }
    @GetMapping("/cart/more/{productcode}")
    public String moreCartQuantity(@PathVariable String productcode, Model model) throws IOException {
        Product underChange = productService.getProductByProductCode(productcode);
        productService.addItemToVirtualCart(productcode);
        productService.decreaseStockQuantity(productcode);
        List<ProductVirtualCartDTO> obj = productService.listAllCartItems();
        if (obj != null) {
            model.addAttribute("products", productService.listAllCartItems());
        }
        model.addAttribute("total", productService.totalCharges());
        return "productcart";
    }
    @GetMapping("/cart/remove/{productcode}")
    public String removeProductFromCart(@PathVariable String productcode, Model model) throws IOException {
        short returnQuantity = productService.getQuantityFromVirtualCart(productcode);
        productService.increaseStockQuantityBatch(returnQuantity, productcode);
        productService.removeItemFromVirtualCart(productcode);
        List<ProductVirtualCartDTO> obj = productService.listAllCartItems();
        if (obj != null) {
            model.addAttribute("products", productService.listAllCartItems());
        }
        model.addAttribute("total", productService.totalCharges());
        return "productcart";
    }
    @PostMapping("/add")
    public String saveProduct(@ModelAttribute("product") Product product, BindingResult result, Model model) {
        //TODO exception handling if product already exists
        productService.saveProduct(product);
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
    public ModelAndView editProduct(@PathVariable("productcode") String productcode) {
        ModelAndView editview = new ModelAndView("productadd");
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
    @GetMapping("/search/{productname}")
    public String searchProduct(@PathVariable("productname") String productname, Model model) {
        var x = productService.searchProductByName(productname);
        model.addAttribute("products", x);
        return "productlist";

    }
}