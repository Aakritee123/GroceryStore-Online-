package com.egroc.controller;

import com.egroc.DTO.ProductDTO;
import com.egroc.enums.OrderStatus;
import com.egroc.enums.UserRole;
import com.egroc.exception.ResourceNotFoundException;
import com.egroc.model.Category;
import com.egroc.model.Order;
import com.egroc.model.Product;
import com.egroc.model.User;
import com.egroc.repository.UserRepository;
import com.egroc.service.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController{
    //String uploadDir = System.getProperty("user.dir") + File.separator + "img" + File.separator + "productImages";

    //String uploadDir = System.getProperty("user.dir") + "/img/productImages";
  String uploadDir=System.getProperty("user.dir") + "/img/productImages";
   //private static final String uploadDir = "src/main/resources/static/img/productImages/";
  @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;
   @Autowired
   ProductService productService;
    //admin dashboard

    @Autowired
    private UserRepository userRepository;
//
@GetMapping("/admin")
public String adminHome(Model model, HttpServletRequest request) {
    String username = null;
    Cookie[] cookies = request.getCookies();

    // Extract username from cookies
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("username".equals(cookie.getName())) {
                username = cookie.getValue();
                break;
            }
        }
    }

    // If no username cookie, redirect to login
    if (username == null) {
        return "redirect:/login";
    }

    // Fetch user details based on the username
    User user = userService.findByUsername(username); // Assuming you have a UserService
    if (user == null || user.getRole() != UserRole.ADMIN) {
        // Redirect to access denied page if user is not admin
        return "redirect:/access-denied";
    }

    // Add username to model for display in the admin page
    model.addAttribute("username", username);
    return "admin";
}


    @GetMapping("/admin/categories")
    public String getCat(Model model){
    model.addAttribute("categories",categoryService.getAllCategory());
    return "categories";
    }



    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model){
    model.addAttribute("category",new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category){
       categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
        public String deleteCat(@PathVariable int id){
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
        }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id,Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category",category.get());
            return "categoriesAdd";
        }else{
            return "404";
        }
    }

    //Product Section
    @GetMapping("/admin/products")
    public String products(Model model){
        model.addAttribute("products",productService.getAllProduct());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String productAddGet(Model model){
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories",categoryService.getAllCategory());
        return "productsAdd";
    }

    @Autowired
    public FileService fileService;
    @PostMapping("/admin/products/add")
    public String productAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,
                                 @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName")String imgName) throws IOException{


   System.out.println("product added");

    Product product=new Product();
    product.setId(productDTO.getId());
    product.setName(productDTO.getName());
    product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
    product.setPrice(productDTO.getPrice());
    product.setDescription(productDTO.getDescription());



       // String imageUUID;
        String imageUUID = fileService.saveFile(file, uploadDir);

        product.setImageName(imageUUID);
        productService.addProduct(product);
    return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id){
    productService.removeProductById(id);
    return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable long id,Model model){
    Product product =productService.getProductById(id).get();
    ProductDTO productDTO =new ProductDTO();
    productDTO.setId(product.getId());
    productDTO.setName(product.getName());
    productDTO.setCategoryId(product.getCategory().getId());
    productDTO.setPrice(productDTO.getPrice());

        productDTO.setDescription(product.getDescription());
    productDTO.setImageName(product.getImageName());
    model.addAttribute("categories",categoryService.getAllCategory());
    model.addAttribute("productDTO",productDTO);
     return "productsAdd";
    }




    @Autowired
    private OrderService orderService;

    // List Orders with Optional Status Filter
    @GetMapping("/admin/admin-orders")
    public String listOrders(@RequestParam(required = false) String status, Model model) {
        List<Order> orders;
        if (status != null && !status.isEmpty()) {
            try {
                orders = orderService.getOrdersByStatus(OrderStatus.valueOf(status.toUpperCase())); // Ensure status matches enum
            } catch (IllegalArgumentException e) {
                model.addAttribute("error", "Invalid status filter.");
                orders = orderService.getAllOrders();  // Fallback to all orders if invalid status
            }
        } else {
            orders = orderService.getAllOrders();
        }
        model.addAttribute("orders", orders);
        return "admin-orders";  // Display orders to admin
    }

    // View Order Details with Error Handling for Invalid ID
    @GetMapping("/admin/admin-orders/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Order order = orderService.getOrderById(id);
            model.addAttribute("order", order);
            return "admin-order-details";  // View order details page
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Order not found.");
            return "redirect:/admin/admin-orders";  // Redirect with error message
        }
    }

    // Update Order Status
    @PostMapping("/admin/admin-orders/update-status/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status, RedirectAttributes redirectAttributes) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());  // Validate status
            orderService.updateOrderStatus(id, orderStatus);
            redirectAttributes.addFlashAttribute("message", "Order status updated successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid status: " + status);
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Order not found.");
        }
        return "redirect:/admin/admin-orders";  // Redirect to orders list
    }

}