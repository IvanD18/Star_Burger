package ru.rosbank.javaschool.web.service;

import lombok.RequiredArgsConstructor;
import ru.rosbank.javaschool.web.exception.DataAccessException;
import ru.rosbank.javaschool.web.exception.NotFoundException;
import ru.rosbank.javaschool.web.model.OrderModel;
import ru.rosbank.javaschool.web.model.OrderPositionModel;
import ru.rosbank.javaschool.web.model.ProductModel;
import ru.rosbank.javaschool.web.repository.OrderPositionRepository;
import ru.rosbank.javaschool.web.repository.OrderRepository;
import ru.rosbank.javaschool.web.repository.ProductRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class BurgerUserService {
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final OrderPositionRepository orderPositionRepository;

  public List<ProductModel> getAll() {
    return productRepository.getAll();
  }

  public ProductModel getById(int id) {
    return productRepository.getById(id).orElseThrow(NotFoundException::new);
  }


  public int createOrder() {
    OrderModel model = new OrderModel(0);
    orderRepository.save(model);
    return model.getId();
  }


  public void order(int orderId, int id, int quantity) {
    ProductModel productModel = productRepository.getById(id).orElseThrow(NotFoundException::new);
    OrderPositionModel orderPositionModel = new OrderPositionModel(
        0,
        orderId,
        productModel.getId(),
        productModel.getName(),
        productModel.getPrice(),
        quantity
    );
    orderPositionRepository.save(orderPositionModel);
  }

  public List<OrderPositionModel> getAllOrderPosition(int orderId) {
      return orderPositionRepository.getAllByOrderId(orderId);
  }

  public BurgerUserService(ProductRepository productRepository, OrderRepository orderRepository, OrderPositionRepository orderPositionRepository) {
    this.productRepository = productRepository;
    this.orderRepository = orderRepository;
    this.orderPositionRepository = orderPositionRepository;
  }
  public ProductModel getDescriptionById(int id){
        return productRepository.getDescriptionById(id).orElseThrow(NotFoundException::new);
  }
  public List<ProductModel> getAllDescription(){
    return productRepository.getAllDescription();
  }
  public void removeById(int id) {
    orderPositionRepository.removeById(id);
  }

}
