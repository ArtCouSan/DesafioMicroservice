package br.com.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "TB_ORDERED")
public class OrderedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERED_ID")
    private Long id;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordered", fetch = FetchType.EAGER)
    private List<OrderedItemEntity> orderedItem;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDERED_ID_USER")
    private UserEntity user;

    @Column(name = "ORDERED_TOTAL")
    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderedItemEntity> getOrderedItem() {
        return orderedItem;
    }

    public void setOrderedItem(List<OrderedItemEntity> orderedItem) {
        this.orderedItem = orderedItem;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
