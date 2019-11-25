package ru.rosbank.javaschool.web.product;

import ru.rosbank.javaschool.web.model.ProductModel;

public class Drink extends ProductModel {

    public Drink(int id, String name, int price,  int quantity, String imageUrl, String description) {
        super(id, name, price, quantity, imageUrl,description);
    }
}
