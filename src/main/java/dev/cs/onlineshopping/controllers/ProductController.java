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
    @GetMapping("/")
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
        Product underChange = productService.getProductByProductCode(productcode);
        productService.addItemToVirtualCart(productcode);
        productService.testDisplayCartContent();
        productService.decreaseStockQuantity(productcode);
        System.out.println(" Testing quantity at  modifications:" + productService.getQuantityFromVirtualCart(productcode));
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
        productService.clearCart();
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
    @GetMapping("/add")
    public String addProduct(Model model) {
        System.out.println("/prodcut/add get is called" + productLineService.findAllProductLine().size());
        Set<String> productcodes = new HashSet<>();
        for (ProductLine pl : productLineService.findAllProductLine()) {
            productcodes.add(pl.getProductLine());

        }
        model.addAttribute("product", new Product());
        model.addAttribute("productlines", productcodes);
        return "productadd";
    }
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
        System.out.println(" Testing quantity at  modifications:" + productService.getQuantityFromVirtualCart(productcode));
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
        System.out.println(" testing quantity at  modifications:" + returnQuantity);
        List<ProductCartDTO> obj = productService.listAllCartItems();
        if (obj != null) {
            model.addAttribute("cc", productService.listAllCartItems());
        }
        return "productcart";

    }

    // CRUD CRUD CRUD STARTS
    @PostMapping("/add")
    public String saveProduct(@ModelAttribute("product") Product product, BindingResult result, Model model) {
        System.out.println("/product/add POST is called" + product.getProductCode());
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
//        return "adminproduct";
    }
    // Delete
//    @GetMapping("/delete/{productcode}")
//    public String deleteProduct(@PathVariable String productcode){
//        System.out.println("Delete is called ");
//        productService.deleteProduct(productcode);
//        return "redirect:/product/admin";
//    }
    // Update
//    @GetMapping()
//    public String updatProduct(@ModelAttribute("product") Product p){
//        // populate the product in a form
//        // update it
//        // send it to the post method
//
//
//    }
    // CRUD CRUD CRUD ENDS
    // PlaceOrder PlaceOrder PlaceOrder START
    //var username =  Util.getCookieValueByName(request,"userName");
    // get the customer name
    //
//    @PostMapping("/update/{productcode}")
//    public String updateProductPersist(@ModelAttribute("product") Product p){
//        System.out.println("Update is called");
//        Product u= productService.findProductByProductCode(p.getProductCode());
//        productService.updateProduct(
//                 p.getProductName()
//                ,p.getProductLine()
//                ,p.getProductScale()
//                ,p.getProductVendor()
//                ,p.getProductDescription()
//                ,p.getQuantityInStock()
//                ,p.getBuyPrice()
//                ,p.getMSRP());
//        return "redirect:/product/admin";
//    }
//    @PostMapping("/order")
//    public String saveMyOrders() {
//// use session get the  userid
//// get customerid using user id
//// get productcode and quanity form mymap {all productcode , with quantities }
//// insert productCode to order table
//// capture the orderId (Pk)
//// pair orderId with each productcode from mymap and orderID() insert into orderdetails
//// at the same time update product quantitry
//// empty mymap
//        return "redirect:/product/admin";
//    }

}