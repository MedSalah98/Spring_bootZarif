package tn.esprit.spring.Service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.Entity.DeliveryMan;

public interface ImpDeliveryManService {
	public  List<DeliveryMan> getAllTheDeliveryMans();

	public DeliveryMan getDeliveryMan(int id);

	public void saveDeliveryMan( DeliveryMan deliveryMan);

	public   void updateDeliveryMan( DeliveryMan deliveryMan) ;

	public void deleteDeliveryMan(int id) ;
	public void affectDeliveryManToOrder(int deliveryManId, int orderId); 

	public DeliveryMan DeliveryManOfTheMounth( ) ;

	public  double distance(int delvmanId ,int clientId);
	public static File fileConverter(MultipartFile file, String basePath) {
		return null;
	}
	int nearestMan(int clientId);
	public void save(MultipartFile[] files,int id);
}
