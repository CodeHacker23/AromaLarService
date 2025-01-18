package ru.project.aromalarservice.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.project.aromalarservice.model.dto.Cart;
import ru.project.aromalarservice.model.entity.Diffuser;

public class CartController {


//    //    добвление дифузера в корзину
//    @PostMapping("/add")
//    public String addToCart(@RequestParam Long id, HttpSession session) {
//        // Получаем диффузер по id
//        Diffuser diffuser = findDiffuserById(id);  // Предположим, у нас есть метод для поиска диффузера по id
//        Cart cart = getCartFromSession(session);
//        cart.addProduct(diffuser);
//        return "redirect:/cart";  // Перенаправляем обратно на страницу корзины
//    }




    // Метод для отображения корзины
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Cart cart = getCartFromSession(session);
        model.addAttribute("cart", cart);
        return "cart";  // cart.html
    }


    // Метод для обновления количества товара в корзине
    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        Cart cart = getCartFromSession(session);
        cart.updateQuantity(productId, quantity);
        return "redirect:/cart";  // Перенаправляем обратно на страницу корзины
    }

    // Метод для удаления товара из корзины
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        Cart cart = getCartFromSession(session);
        cart.removeProduct(productId);
        return "redirect:/cart";  // Перенаправляем обратно на страницу корзины
    }

    // Метод для оформления заказа
    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        Cart cart = getCartFromSession(session);

        // тут можно добавить логику для отправки заказа в Telegram или на почту

        // Очищаем корзину после оформления заказа
        session.removeAttribute("cart");

        return "redirect:/order-success";  // Перенаправляем на страницу успеха
    }

    // Метод для получения корзины из сессии
    private Cart getCartFromSession(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }


}
