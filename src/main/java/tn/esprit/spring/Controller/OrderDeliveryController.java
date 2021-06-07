package tn.esprit.spring.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.Entity.OrderDelivery;
import tn.esprit.spring.Repository.OrderDeliveryRepository;
import tn.esprit.spring.Service.OrderDeliveryService;
@CrossOrigin(origins = "*")
@RestController
public class OrderDeliveryController {
	@Autowired
	private OrderDeliveryService orderDeliveryService;
	@Autowired
	OrderDeliveryRepository orderDeliveryRepository;
	//GET
	@GetMapping(value = "/orderDeliverys") 
	public List<OrderDelivery> getOrderDeliverys() {
		return orderDeliveryService.getAllTheOrderDeliverys();
	}
	//GET by id
	@GetMapping(value ="/orderDeliverys/{id}")
	public OrderDelivery getOrderDeliveryById(@PathVariable int id) {
		return orderDeliveryService.getOrderDelivery(id);
	}
	//put -> Save the data
	@PutMapping(value="/orderDeliverys/save/{userId}")
	public void saveOrderDelivery(@RequestBody OrderDelivery orderDelivery ,@PathVariable int userId) {
		orderDeliveryService.saveOrderDelivery(orderDelivery, userId);
	}
	//PUT
	@PutMapping(value="/orderDeliverys/update")
	public void updateOrderDelivery(@RequestBody OrderDelivery orderDelivery) {
		orderDeliveryService.updateOrderDelivery(orderDelivery);
	}
	//DELETE
	@DeleteMapping(value = "orderDeliverys/delete/{id}")
	public void deleteOrderDelivery(@PathVariable int id) {
		orderDeliveryService.deleteOrderDelivery(id);
	}
	
	//DELETE
	@DeleteMapping(value = "orderDeliverys/delete")
	public void deleteAllOrderDelivery() {
		orderDeliveryRepository.deleteAllOrderDelivery();
	}
	//affect User to order
	@PostMapping (value = "orderDeliverys/affectUserToOrder/{userId}/{orderId}")
	
	public void affectUserToOrder(@PathVariable int userId, @PathVariable int orderId) {
		orderDeliveryService.affectUserToOrder(userId, orderId);
	}

	@GetMapping (value = "orderDeliverys/cost/{orderId}")	
	public void deliveryCost( @PathVariable int orderId) {
		orderDeliveryService.deliveryCost(orderId);
	}
	
	@PutMapping(value = "/sendemail/{userId}")
	public String send( @PathVariable int userId) {
		orderDeliveryService.sendEmail(userId);
	   return "Email sent successfully";   
	}
}
