package ru.rosbank.javaschool.web.product;

import ru.rosbank.javaschool.web.model.ProductModel;

public class Potato extends ProductModel {

    public Potato(int id, String name, int price, int quantity, String imageUrl,  String description) {
        super(id, name, price, quantity, imageUrl, description);
    }
}
