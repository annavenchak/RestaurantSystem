package com.example.demo.service;
import com.example.demo.repository.MenuRepository;
import com.example.demo.domain.Menu;
import com.example.demo.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class MenuManager implements CRUDOperations<Menu> {
    private final MenuRepository menuRepository;
    @Autowired
    public MenuManager(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
        initializeMenu();
    }
    private void initializeMenu() {
        if (menuRepository.count() == 0) {
            menuRepository.save(new Menu("French Fries", 200, 65));
            menuRepository.save(new Menu("Steamed Rice", 200, 60));
            menuRepository.save(new Menu("Bread Basket", 150, 30));
            menuRepository.save(new Menu("Garlic Pampushky", 100, 40));
            menuRepository.save(new Menu("Ukrainian Borsch", 300, 110));
            menuRepository.save(new Menu("Chicken Bouillon", 300, 85));
            menuRepository.save(new Menu("Chicken Chop", 150, 125));
            menuRepository.save(new Menu("Chicken Kiev", 180, 145));
            menuRepository.save(new Menu("Cheese Pancakes", 200, 95));
            menuRepository.save(new Menu("Homemade Compote", 250, 35));
            menuRepository.save(new Menu("Orange Juice", 250, 45));
            menuRepository.save(new Menu("Americano", 150, 40));
            menuRepository.save(new Menu("Cappuccino", 250, 60));
            menuRepository.save(new Menu("Hot Cocoa", 250, 55));
        }
    }

    @Override
    public void create(Menu item) {
        menuRepository.save(item);
    }

    @Override
    public List<Menu> read() {
        return menuRepository.findAll();
    }

    public Menu readById(int id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public boolean update(int id, Menu newItem) {
        if (menuRepository.existsById(id)) {
            newItem.setId(id);
            menuRepository.save(newItem);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (menuRepository.existsById(id)) {
            menuRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Order createOrder(int menuIndex, int portions) {
        List<Menu> allItems = read();
        if (menuIndex > 0 && menuIndex <= allItems.size()) {
            Menu item = allItems.get(menuIndex - 1);
            return new Order(item, portions);
        }
        return null;
    }
}
