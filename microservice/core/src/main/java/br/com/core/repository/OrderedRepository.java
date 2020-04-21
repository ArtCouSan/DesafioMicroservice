package br.com.core.repository;

import br.com.core.model.OrderedEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedRepository extends PagingAndSortingRepository<OrderedEntity, Long> {
}
