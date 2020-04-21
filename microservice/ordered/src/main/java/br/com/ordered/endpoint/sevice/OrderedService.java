package br.com.ordered.endpoint.sevice;

import br.com.core.model.OrderedEntity;
import br.com.ordered.endpoint.dto.OrderedSaveDTO;

public interface OrderedService {

    public OrderedEntity makeOrdered(OrderedSaveDTO orderedEntity);

}
