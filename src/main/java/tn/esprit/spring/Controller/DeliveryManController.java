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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import tn.esprit.spring.Entity.DeliveryMan;
import tn.esprit.spring.Repository.DeliveryManRepository;
import tn.esprit.spring.Service.DeliveryManService;
@RestController
@CrossOrigin(origins = "http://localhost:4200")

	public class DeliveryManController {
		@Autowired
		private DeliveryManService deliveryManService;
		@Autowired
		DeliveryManRepository deliveryManRepository;
		//GET
		@GetMapping(value = "/deliveryMans") 
		public List<DeliveryMan> getDeliveryMans() {
			return deliveryManService.getAllTheDeliveryMans();
		}
		//GET by id

		@GetMapping(value ="/deliveryMans/{id}")
		public DeliveryMan getDeliveryManById(@PathVariable int id) {
			return deliveryManService.getDeliveryMan(id);
		}

		//POST -> Save the data
		@PostMapping(value="/deliveryMans/save")
		public void saveDeliveryMan(@RequestBody DeliveryMan deliveryMan) {
			deliveryManService.saveDeliveryMan(deliveryMan);
		}
 
		//UPDATE
		@PutMapping(value="/deliveryMans/update")
		public void updateDeliveryMan(@RequestBody DeliveryMan deliveryMan) {
			deliveryManService.updateDeliveryMan(deliveryMan);
		}

		//DELETE
		@DeleteMapping(value = "/deliveryMans/delete/{id}")
		public void deleteDeliveryMan(@PathVariable int id) {
			deliveryManService.deleteDeliveryMan(id);
		}
		//DELETE ALL
		@DeleteMapping(value = "/deliveryMans/delete")
		public void deleteAllDeliveryMan() {
			deliveryManRepository.deleteAllDeliveryMan();
		}
		 
		//Men of the month 
		@GetMapping (value = "/deliveryMans/best")
		public DeliveryMan deliveryMenOfTheMounth() {
			return deliveryManService.DeliveryManOfTheMounth();
		}
	
		@PostMapping (value = "deliveryMans/affectDeliveryManToOrder/{DelvmanId}/{orderid}")
		
		public void affectDeliveryManToOrder(@PathVariable int orderId, @PathVariable int clientId) {
			deliveryManService.affectDeliveryManToOrder(orderId,clientId);
		}
		
		@GetMapping (value = "/distance/{delvmanId}/{clientId}")
		public  double distance(@PathVariable int delvmanId ,@PathVariable int clientId) {
			return deliveryManService.distance(delvmanId, clientId);
			}
		@GetMapping (value = "/nearest/{clientId}")
		public int nearestMan (@PathVariable int clientId) {
			return deliveryManService.nearestMan(clientId);
		}
//update
		
	    @PostMapping("/uploadFile/{id}")
	    	public void save(@RequestParam("file") MultipartFile[] files, @PathVariable int id) {
	    		deliveryManService.save(files, id);
	    }
	     	
}
