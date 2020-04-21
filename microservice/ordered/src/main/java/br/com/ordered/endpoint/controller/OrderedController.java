package br.com.ordered.endpoint.controller;

import br.com.core.model.OrderedEntity;
import br.com.ordered.endpoint.dto.OrderedSaveDTO;
import br.com.ordered.endpoint.sevice.OrderedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/order")
@Api(value = "Endpoint to orders")
public class OrderedController {

    private OrderedService orderedService;

    public OrderedController(OrderedService orderedService) {
        this.orderedService = orderedService;
    }

    @PostMapping
    @ApiOperation(value = "Make order")
    public ResponseEntity<OrderedEntity> makeOrder(@RequestBody OrderedSaveDTO orderedSaveDTO){
        OrderedEntity orderedEntity = this.orderedService.makeOrdered(orderedSaveDTO);
        return new ResponseEntity<OrderedEntity>(orderedEntity, HttpStatus.CREATED);
    }

}
