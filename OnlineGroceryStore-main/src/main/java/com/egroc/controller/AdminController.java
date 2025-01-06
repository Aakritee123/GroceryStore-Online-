package com.egroc.controller;

import com.egroc.DTO.ProductDTO;
import com.egroc.enums.OrderStatus;
import com.egroc.exception.ResourceNotFoundException;
import com.egroc.model.Category;
import com.egroc.model.Order;
import com.egroc.model.Product;
import com.egroc.service.CategoryService;

import com.egroc.service.FileService;
import com.egroc.service.OrderService;
import com.egroc.service.ProductService;

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
    CategoryService categoryService;
   @Autowired
   ProductService productService;
    //admin dashboard
@GetMapping("/admin")
    public String adminHome(){
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

    @GetMapping("/admin/admin-orders")
    public String listOrders(@RequestParam(required = false) String status, Model model) {
        List<Order> orders;
        if (status != null) {
            orders = orderService.getOrdersByStatus(OrderStatus.valueOf(status.toUpperCase())); // Add error handling for invalid status
        } else {
            orders = orderService.getAllOrders();
        }
        model.addAttribute("orders", orders);
        return "admin-orders";
    }




    @GetMapping("/admin/admin-orders/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Order order = orderService.getOrderById(id); // Assuming it throws an exception if not found
            model.addAttribute("order", order);
            return "admin-order-details";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Order not found.");
            return "redirect:/admin/admin-orders"; // Redirect to the list with an error message
        }
    }



    @PostMapping("/admin/admin-orders/update-status/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status, RedirectAttributes redirectAttributes) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase()); // Validate the status
            orderService.updateOrderStatus(id, orderStatus);
            redirectAttributes.addFlashAttribute("message", "Order status updated successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid status: " + status);
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Order not found.");
        }
        return "redirect:/admin/admin-orders";
    }


}