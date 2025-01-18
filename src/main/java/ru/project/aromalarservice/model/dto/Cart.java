package ru.project.aromalarservice.model.dto;

import ru.project.aromalarservice.model.entity.Diffuser;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Long, CartItem> items = new HashMap<>();

    // Добавить товар в корзину
    public void addProduct(Diffuser diffuser) {
        CartItem item = items.get(diffuser.getId());
        if (item == null) {
            item = new CartItem(diffuser, 1);  // Начинаем с количества 1
            items.put(diffuser.getId(), item);
        } else {
            item.setQuantity(item.getQuantity() + 1);
        }
    }

    // Обновить количество товара
    public void updateQuantity(Long productId, int quantity) {
        CartItem item = items.get(productId);
        if (item != null && quantity > 0) {
            item.setQuantity(quantity);
        }
    }

    // Удалить товар из корзины
    public void removeProduct(Long productId) {
        items.remove(productId);
    }

    // Получить все товары из корзины
    public Map<Long, CartItem> getItems() {
        return items;
    }

    // Общая сумма корзины
    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getTotalPrice();
        }
        return total;
    }

    // Вложенный класс для представления товара в корзине
    public static class CartItem {
        private Diffuser diffuser;
        private int quantity;

        // Конструктор
        public CartItem(Diffuser diffuser, int quantity) {
            this.diffuser = diffuser;
            this.quantity = quantity;
        }

        // Получить цену товара
        public double getPrice() {
            return diffuser.getPrice();
        }

        // Получить название товара
        public String getName() {
            return diffuser.getName();
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        // Общая цена для этого товара (цена * количество)
        public double getTotalPrice() {
            return getPrice() * quantity;
        }
    }
}
