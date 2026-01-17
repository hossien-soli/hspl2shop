package dev.hspl.hspl2shop.shop.web;

import dev.hspl.hspl2shop.shop.service.write.OrderManagementCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderCustomerController {
    private final OrderManagementCustomerService managementService;

//    @GetMapping("/order")
//    @ResponseStatus(HttpStatus.OK)
//    public Object fetchAllCustomerOrders(
//            Authentication authentication
//    ) {
//
//    }

//    @PostMapping("/order")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Object registerNewCustomerOrder(
//            Authentication authentication
//    ) {
//
//    }
}
