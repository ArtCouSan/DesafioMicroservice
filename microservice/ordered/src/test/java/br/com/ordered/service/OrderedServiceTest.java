package br.com.ordered.service;

import br.com.core.model.OrderedEntity;
import br.com.core.model.OrderedItemEntity;
import br.com.core.model.ProductEntity;
import br.com.core.model.UserEntity;
import br.com.core.repository.OrderedRepository;
import br.com.ordered.endpoint.dto.OrderedItemDTO;
import br.com.ordered.endpoint.dto.OrderedSaveDTO;
import br.com.ordered.endpoint.sevice.impl.OrderedServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderedServiceTest {

    @Mock
    private OrderedRepository orderedRepository;

    @InjectMocks
    private OrderedServiceImpl orderedService;

    private OrderedEntity getOrderedEntity(){
        OrderedEntity orderedEntity = new OrderedEntity();
        orderedEntity.setId(1l);
        orderedEntity.setTotal(new BigDecimal(100));

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1l);
        userEntity.setUsername("MockUser");
        userEntity.setPassword("MockPassword");
        userEntity.setRole("USER");
        orderedEntity.setUser(userEntity);

        OrderedItemEntity orderedItemEntity = new OrderedItemEntity();
        orderedItemEntity.setId(1l);
        orderedItemEntity.setPrice(new BigDecimal(10));
        orderedItemEntity.setQuantity(10);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1l);
        productEntity.setName("MockName");
        productEntity.setDescription("MockDescription");
        productEntity.setQuantity(10);
        productEntity.setPrice(new BigDecimal(10));
        productEntity.setStatus(true);
        orderedItemEntity.setProduct(productEntity);

        orderedEntity.setOrderedItem(List.of(orderedItemEntity));

        return orderedEntity;
    }

    private OrderedSaveDTO getOrderedSaveDTO(){
        OrderedSaveDTO orderedSaveDTO = new OrderedSaveDTO();

        OrderedItemDTO orderedItemDTO = new OrderedItemDTO();
        orderedItemDTO.setIdProduct(1l);
        orderedItemDTO.setQuantity(5);

        List<OrderedItemDTO> orderedItemDTOList = new ArrayList<OrderedItemDTO>();
        orderedItemDTOList.add(orderedItemDTO);

        orderedSaveDTO.setIdUser(1l);
        orderedSaveDTO.setListItems(orderedItemDTOList);

        return orderedSaveDTO;
    }

}
