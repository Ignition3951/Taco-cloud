package tacos.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import tacos.data.OrderRepository;
import tacos.model.TacoOrder;
import tacos.model.User;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
	
	private OrderRepository orderRepository;
	
	public OrderController(OrderRepository orderRepository) {
		this.orderRepository=orderRepository;
	}
	
	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus,
			@AuthenticationPrincipal User user) {
		
		if(errors.hasErrors()) {
			return "orderForm";
		}
		tacoOrder.setUser(user);
		orderRepository.save(tacoOrder);
		sessionStatus.setComplete();
		return "redirect:/";
	}

}
