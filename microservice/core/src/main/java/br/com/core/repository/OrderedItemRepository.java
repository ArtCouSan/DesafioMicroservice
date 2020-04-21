package br.com.core.repository;

import br.com.core.model.OrderedItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedItemRepository extends PagingAndSortingRepository<OrderedItemEntity, Long> {
}
