package devzeus.com.laptop_shop.controllers.admin;

import devzeus.com.laptop_shop.enums.OrderStatus;
import devzeus.com.laptop_shop.models.Order;
import devzeus.com.laptop_shop.models.Payment;
import devzeus.com.laptop_shop.services.interfaces.IOrderService;
import devzeus.com.laptop_shop.services.interfaces.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller()
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final IPaymentService paymentService;

    @GetMapping("") // localhost:8080/manager/orders
    public String index(Model model) {
        List<Order> orders = orderService.findAll();
        List<Payment> payments = paymentService.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("orderStatus", OrderStatus.values());
        model.addAttribute("payments", payments);
        return "admin/orders/page-list-order";
    }

    @GetMapping("/update")
    public String edit(Model model, @RequestParam Long id) {
        Order order = orderService.findById(id).get();
        model.addAttribute("orderStatus", OrderStatus.values());
        model.addAttribute("order", order);
        return "admin/orders/page-update-order";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam Long id) {
        orderService.deleteById(id);
        return "redirect:/admin/orders";
    }

    @PostMapping("/pending")
    public String pending(Model model, @RequestParam Long id) throws IOException {
        orderService.orderPending(id);
        return "redirect:/admin/orders";
    }

    @PostMapping("/shipping")
    public String update(Model model, @RequestParam Long id) throws IOException {
        orderService.orderShipping(id);
        return "redirect:/admin/orders";
    }

    @PostMapping("/cancelled")
    public String cancel(Model model, @RequestParam Long id) throws IOException {
        orderService.orderCancelled(id);
        return "redirect:/admin/orders";
    }
}
