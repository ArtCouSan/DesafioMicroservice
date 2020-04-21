package br.com.ordered.endpoint.dto;

import br.com.core.model.OrderedEntity;
import br.com.core.model.OrderedItemEntity;
import br.com.core.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class OrderedSaveDTO {

    private Long idUser;

    private List<OrderedItemDTO> listItems;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public List<OrderedItemDTO> getListItems() {
        return listItems;
    }

    public void setListItems(List<OrderedItemDTO> listItems) {
        this.listItems = listItems;
    }

    public OrderedEntity parseOrdered() {

        OrderedEntity orderedEntity = new OrderedEntity();

        UserEntity user = new UserEntity();
        user.setId(this.idUser);
        orderedEntity.setUser(user);

        List<OrderedItemEntity> orderedItemEntityList = new ArrayList<OrderedItemEntity>();
        this.listItems.forEach(item -> {
            OrderedItemEntity orderedItemEntity = new OrderedItemEntity();
            orderedItemEntity.setId(item.getIdProduct());
            orderedItemEntity.setQuantity(item.getQuantity());
            orderedItemEntityList.add(orderedItemEntity);
        });
        orderedEntity.setOrderedItem(orderedItemEntityList);

        return orderedEntity;
    }

}
