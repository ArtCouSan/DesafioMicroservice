package br.com.ordered.endpoint.sevice.impl;

import br.com.core.model.OrderedEntity;
import br.com.core.model.OrderedItemEntity;
import br.com.core.model.ProductEntity;
import br.com.core.model.UserEntity;
import br.com.core.repository.OrderedItemRepository;
import br.com.core.repository.OrderedRepository;
import br.com.core.repository.ProductRepository;
import br.com.core.repository.UserRepository;
import br.com.ordered.endpoint.dto.OrderedSaveDTO;
import br.com.ordered.endpoint.sevice.OrderedService;
import br.com.product.endpoint.service.ProductService;
import br.com.users.endpoint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderedServiceImpl implements OrderedService {

    private OrderedRepository orderedRepository;
    private OrderedItemRepository orderedItemRepository;
    private UserService userService;
    private ProductService productService;

    public OrderedServiceImpl(OrderedRepository orderedRepository, OrderedItemRepository orderedItemRepository, UserService userService, ProductService productService) {
        this.orderedRepository = orderedRepository;
        this.orderedItemRepository = orderedItemRepository;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public OrderedEntity makeOrdered(OrderedSaveDTO orderedSaveDTO) {
        OrderedEntity orderedEntity = orderedSaveDTO.parseOrdered();

        // Valid user for order
        UserEntity user = this.userService.validateUserExist(orderedEntity.getUser().getId());

        // Verify shopping cart
        orderedEntity.setOrderedItem(validateShoppingCart(orderedEntity.getOrderedItem()));
        List<OrderedItemEntity> orderedItemEntitySave = orderedEntity.getOrderedItem();


        // Total order
        BigDecimal total = totalCart(orderedItemEntitySave);

        orderedEntity.setUser(user);
        orderedEntity.setTotal(total);

        // Save order
        orderedEntity.setOrderedItem(null);
        OrderedEntity orderedSaved = orderedRepository.save(orderedEntity);

        // Save shopping cart
        List<OrderedItemEntity> orderedItemEntity = makeShoppingCart(orderedItemEntitySave, orderedSaved);
        orderedItemRepository.saveAll(orderedItemEntity);

        return orderedEntity;
    }

    /**
     * Verify if shopping cart is valid quantity products
     * @param listTempShoppingCart
     * @return tempList
     */
    private List<OrderedItemEntity> validateShoppingCart(List<OrderedItemEntity> listTempShoppingCart) {
        listTempShoppingCart.forEach(item -> {
            ProductEntity productEntity = this.productService.validateQuantity(item.getId(), item.getQuantity());
            item.setPrice(productEntity.getPrice());
        });
        return listTempShoppingCart;
    }

    /**
     * Make shopping cart with temp in dto order
     *
     * @param listTempShoppingCart
     * @return shoppingCart
     */
    private List<OrderedItemEntity> makeShoppingCart(List<OrderedItemEntity> listTempShoppingCart, OrderedEntity orderedSaved) {

        List<OrderedItemEntity> orderedItemEntityList = new ArrayList<OrderedItemEntity>();
        List<ProductEntity> productEntityList = new ArrayList<ProductEntity>();

        for (OrderedItemEntity item :
                listTempShoppingCart) {

            OrderedItemEntity orderedItem = new OrderedItemEntity();

            ProductEntity productEntity = this.productService.validateQuantity(item.getId(), item.getQuantity());

            // New quantity product
            Integer total = Math.subtractExact(productEntity.getQuantity(), item.getQuantity());
            productEntity.setQuantity(total);
            productEntityList.add(productEntity);

            orderedItem.setProduct(productEntity);
            orderedItem.setQuantity(item.getQuantity());
            orderedItem.setPrice(item.getPrice());
            orderedItem.setOrdered(orderedSaved);

            orderedItemEntityList.add(orderedItem);
        }

        // Update quantity of products
        this.productService.saveAllProductUpdate(productEntityList);

        return orderedItemEntityList;
    }

    /**
     * Sum prices of order
     *
     * @param listShoppingCart
     * @return total
     */
    private BigDecimal totalCart(List<OrderedItemEntity> listShoppingCart) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderedItemEntity item :
                listShoppingCart) {
            total = total.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        return total;
    }

}
