package tn.esprit.spring.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.Entity.User;
import tn.esprit.spring.Entity.OrderDelivery;
import tn.esprit.spring.Repository.DeliveryManRepository;
import tn.esprit.spring.Repository.OrderDeliveryRepository;
import tn.esprit.spring.Repository.UserRepository;

import java.io.File;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
@Service
public class OrderDeliveryService implements ImpOrderDeliveryService{
	@Autowired 
    private OrderDeliveryRepository orderDeliveryRepository;   
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeliveryManRepository deliveryManRepository;
    @Autowired
    private DeliveryManService deliveryManService;
	@Autowired
    private JavaMailSender javaMailSender;
    
    
    
    
    
    //Return All the orderDeliverys
	@Override
	public List<OrderDelivery> getAllTheOrderDeliverys() {
		List<OrderDelivery> orderDeliveryList = new ArrayList<>();
		orderDeliveryRepository.findAll().forEach(orderDeliveryList::add);
		return orderDeliveryList;
	}

	//Return Single OrderDelivery
	@Override
	public OrderDelivery getOrderDelivery(int id) {
		OrderDelivery orderDelivery = orderDeliveryRepository.findById(id).get();
			return orderDelivery;
	}

	//affectation automatique the orderDelivery
	@Override
	public void saveOrderDelivery(OrderDelivery orderDelivery ,int userId){
		orderDelivery.setPaymentMode("At Delivery");
		orderDelivery.setOrderState("Not delivered");
		orderDeliveryRepository.save(orderDelivery);
		LocalDateTime now =LocalDateTime.now(); 
		orderDelivery.setDeliveryDate(now);			
		affectUserToOrder(userId,orderDelivery.getId()); 
	    deliveryManService.affectDeliveryManToOrder(deliveryManService.nearestMan(userId), orderDelivery.getId());
	    orderDeliveryRepository.save(orderDelivery);
	    deliveryCost(orderDelivery.getId());
	    orderDelivery.setCoustomerName(userRepository.findById(userId).get().getFirstName()+userRepository.findById(userId).get().getLastName());
	    orderDelivery.setCoustomerAdress(userRepository.findById(userId).get().getAdress());
	    orderDelivery.setCoustomerPhone(userRepository.findById(userId).get().getPhone());
	    orderDelivery.setDeliveryManName(deliveryManRepository.findById(orderDelivery.getDeliveryMan().getId()).get().getFirstName()+deliveryManRepository.findById(orderDelivery.getDeliveryMan().getId()).get().getLastName());
	    orderDelivery.setDistance(deliveryManService.distance(deliveryManRepository.findById(orderDelivery.getDeliveryMan().getId()).get().getId(), orderDeliveryRepository.findById(orderDelivery.getId()).get().getUser().getId()));
		orderDeliveryRepository.save(orderDelivery);

	}
	

	//update the orderDelivery
	@Override
	public void updateOrderDelivery(OrderDelivery orderDelivery) {
		orderDeliveryRepository.save(orderDelivery);
	}

	//Remove the OrderDelivery
	public void deleteOrderDelivery(int id) {
		orderDeliveryRepository.deleteById(id);
	}


	// Affect Order to user
	@Override

	public void affectUserToOrder(int userId, int orderId) {
		User d = userRepository.findById(userId).get();
		OrderDelivery e = orderDeliveryRepository.findById(orderId).get();
		e.setUser(d);
		orderDeliveryRepository.save(e);
		userRepository.save(d);
	
	}
//cost
	@Override
	public void deliveryCost(int orderId) {
	     int clientId=orderDeliveryRepository.findById(orderId).get().getUser().getId();
	     int delvmanId  =orderDeliveryRepository.findById(orderId).get().getDeliveryMan().getId();
		 double d = deliveryManService.distance(delvmanId, clientId);
		 if (d< 50) {
			 orderDeliveryRepository.findById(orderId).get().setDeliveryprice(10);}

		 else if (50<d && d<250)
 {
		 orderDeliveryRepository.findById(orderId).get().setDeliveryprice(30);}
			else
	{ orderDeliveryRepository.findById(orderId).get().setDeliveryprice(50);}
		 orderDeliveryRepository.save(orderDeliveryRepository.findById(orderId).get());
}
	
//email	
	@Override
	public void sendEmail(int userId) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(userRepository.findById(userId).get().getEmail());
        msg.setSubject("ConsomiTounsi");
        msg.setText("Delivery will come soon call us for more information \n Thank you for your confidence."
        		);    
        javaMailSender.send(msg);

    }
	

}
