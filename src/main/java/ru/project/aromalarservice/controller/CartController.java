package ru.project.aromalarservice.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.project.aromalarservice.model.dto.Basket;
import ru.project.aromalarservice.model.dto.Cart;
import ru.project.aromalarservice.model.entity.Diffuser;
import ru.project.aromalarservice.repositiria.DiffuserRepository;

import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private Basket basket;
   private DiffuserRepository diffuserRepository;


    @PostMapping("/add")
    public String addToCart(@RequestParam Long id, HttpSession session) {
        try {
            Basket basket = getOrCreateBasket(session);
            Diffuser diffuser = diffuserRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));
            basket.getDiffusers().add(diffuser);
        } catch (Exception e) {
            // Логирование ошибки
        }
        return "redirect:/";
    }

    private Basket getOrCreateBasket(HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        if(basket == null) {
            basket = new Basket();
            session.setAttribute("basket", basket);
        }
        return basket;
    }



   @GetMapping("/basket")
   public String basket(Model model,HttpSession session){

       Basket basket = (Basket) session.getAttribute("basket");
       if(basket == null) {
           basket = new Basket();
           session.setAttribute("basket", basket);
       }

       System.out.println(basket.getDiffusers());
     List<Diffuser> diffusers = basket.getDiffusers();
       model.addAttribute("list",diffusers);
       return "cart";
   }


//
//    //    добвление дифузера в корзину
//    @PostMapping("/add")
//    public String addToCart(Basket basket,@RequestParam Long id) {
//
//
//        // Получаем диффузер по id
//        Diffuser diffuser = diffuserRepository.findById(id).orElse(null);
//        System.out.println(diffuser);
//
//        basket.getDiffusers().add(diffuser);
//        return "redirect:/";  // Перенаправляем обратно на страницу корзины
//    }



//
//    // Метод для отображения корзины
//    @GetMapping
//    public String viewCart(HttpSession session, Model model) {
//        Cart cart = getCartFromSession(session);
//        model.addAttribute("cart", cart);
//        return "cart";  // cart.html
//    }


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
