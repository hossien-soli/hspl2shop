package dev.hspl.hspl2shop.shop.model.write.entity;

import lombok.Getter;
import org.jspecify.annotations.NullMarked;

// first registers the order with custom endpoint
// the request a payment attempt(with order_id) link and client redirects user to that link
// client receive data and send data to server for payment verification
// payment verification endpoint should record all changes as well as actual verification outbox record
// in the outbox we try to verify payment and move the money
// if outbox doesn't successful a rollback operation should be happened
// and we notice the user that we failed to process the payment
// after first payment verificiation try we should only see this message to user: your order receivevd
// like digikala and we shouldn't directly tell him that everything is successful!!

@Getter
@NullMarked
public class Order {
}
