package com.egroc.controller;

import com.egroc.global.GlobalData;
import com.egroc.model.CartItem;
import com.egroc.repository.CategoryRepository;
import com.egroc.service.CategoryService;
import com.egroc.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.egroc.global.GlobalData.cart;

@Controller
public class HomeController{

    @GetMapping({"/", "index"})
    public String showHomePage(Model model, HttpSession session) {
        model.addAttribute("products", productService.getAllProduct());

        model.addAttribute("cartCount", cart.size());
        // Add cart details (count and total amount) to the model
        //model.addAttribute("cartCount", GlobalData.getCartCount());

        // Return to the cart view
        return "index";
    }

//    @GetMapping("/viewproduct/{id}")
//    public String viewProducts(Model model, @PathVariable int id) {
//
//        model.addAttribute("product", productService.getProductById(id).get());
//        return "viewProduct";
//    }

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;



    @GetMapping("/shop")
    public String shop(Model model ,HttpSession session){
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("products",productService.getAllProduct());
        // Add cart details (count and total amount) to the model
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>(); // or return empty list if null
        }
        model.addAttribute("cartCount", cart.size());
        model.addAttribute("cart", cart);

        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable int id,HttpSession session){

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>(); // or return empty list if null
        }
        model.addAttribute("cartCount", cart.size());
        model.addAttribute("cart", cart);
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("products",productService.getAllProductsByCategoryId(id));

        return "shop";
    }
//    @GetMapping("/shop/viewproduct/{id}")
//    public String viewProduct(Model model, @PathVariable int id){
//
//        model.addAttribute("product",productService.getProductById(id).get());
//        model.addAttribute("cartCount", GlobalData.cart.size());
//        return "viewProduct";
//    }


}