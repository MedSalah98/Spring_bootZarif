package tn.esprit.spring.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.Entity.DeliveryMan;
import tn.esprit.spring.Entity.OrderDelivery;
import tn.esprit.spring.Entity.Role;
import tn.esprit.spring.Repository.DeliveryManRepository;
import tn.esprit.spring.Repository.OrderDeliveryRepository;
import tn.esprit.spring.Repository.UserRepository;

@Service
public class DeliveryManService implements ImpDeliveryManService {

	@Autowired
	private DeliveryManRepository deliveryManRepository;
	@Autowired
	private OrderDeliveryRepository orderDeliveryRepository;
	@Autowired
	private UserRepository userRepository;
	// Return Single DeliveryMan
	@Override
	public DeliveryMan getDeliveryMan(int id) {
		DeliveryMan deliveryMan = deliveryManRepository.findById(id).get();
		return deliveryMan;
	}

	// Save the deliveryMan
	@Override
	public void saveDeliveryMan(DeliveryMan deliveryMan) {
		deliveryMan.setRole(Role.DELIVERYMANN);
		deliveryMan.setPoint(0);
		deliveryMan.setDisponibility(true);
		deliveryManRepository.save(deliveryMan);
	}

	// update the deliveryMan
	@Override
	public void updateDeliveryMan(DeliveryMan deliveryMan) {
		deliveryMan.setRole(Role.DELIVERYMANN);
		deliveryManRepository.save(deliveryMan);
	}

	// Remove the DeliveryMan
	@Override
	public void deleteDeliveryMan(int id) {
		deliveryManRepository.deleteById(id);
	}
	//  the nearest DeliveryMan

	@Override
	public int nearestMan (int clientId) {
		List<DeliveryMan> deliveryManList =getAllTheDeliveryMans();
		int closestDeliveryManId = deliveryManList.get(0).getId();		
		double closetDistance=distance( closestDeliveryManId,clientId);
		for (int i = 1; i <= deliveryManList.size() - 1; i++) {
			if (closetDistance > distance(deliveryManList.get(i).getId(),clientId)) {
				closestDeliveryManId = deliveryManList.get(i).getId();
				
			}
		}
		return closestDeliveryManId; 
	}


	// Affect Order to DeliveryMan
	@Override
	public void affectDeliveryManToOrder(int deliveryManId, int orderId) {
		DeliveryMan d = deliveryManRepository.findById(deliveryManId).get();
		OrderDelivery e = orderDeliveryRepository.findById(orderId).get() ;
		e.setDeliveryMan(d);
		d.setDisponibility(false);
		d.setPoint(d.getPoint() + 1);
		orderDeliveryRepository.save(e);
		deliveryManRepository.save(d);
 	}

	// DeliveryMan of the month

	@Override
	public DeliveryMan DeliveryManOfTheMounth() {
		List<DeliveryMan> deliveryManList = getAllTheDeliveryMans();
		DeliveryMan manOfTheMonth = deliveryManList.get(0);
		for (int i = 0; i < deliveryManList.size() - 1; i++) {
			if (deliveryManList.get(i).getPoint() <= deliveryManList.get(i + 1).getPoint()) {
				manOfTheMonth = deliveryManList.get(i + 1);
			}
		};
		if(manOfTheMonth.getPoint()<5) {
			manOfTheMonth.setPrime(20);
		}
		else if(manOfTheMonth.getPoint()>5 && manOfTheMonth.getPoint()<20 ) {
		manOfTheMonth.setPrime(50);}
		else {
		manOfTheMonth.setPrime(70);}
		return manOfTheMonth;
	}

	// Return All the deliveryMans
	@Override
	public List<DeliveryMan> getAllTheDeliveryMans() {
		List<DeliveryMan> deliveryManList = new ArrayList<>();
		deliveryManRepository.findAll().forEach(deliveryManList::add);
		return deliveryManList;
	}

	// distance using haversine
	@Override
	public double distance(int delvmanId, int clientId) {
		double startLat = deliveryManRepository.findById(delvmanId).get().getLatitude();
		double startLong = deliveryManRepository.findById(delvmanId).get().getLongitude();
		double endLat = userRepository.findById(clientId).get().getLatitude();
		double endLong = userRepository.findById(clientId).get().getLongitude();
        double dLat = Math.toRadians(endLat - startLat);
        double dLon = Math.toRadians(endLong - startLong);
  
        // convert to radians
        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);
  
        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) + 
                   Math.pow(Math.sin(dLon / 2), 2) * 
                   Math.cos(startLat) * 
                   Math.cos(endLat);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;

	}
	// upload image
	 @Autowired
	    private Environment environment;	    
	    @Override
		public void save(MultipartFile[] files,int id) {
			if (files != null) {
		    	DeliveryMan deliveryMan=getDeliveryMan(id);
				for (MultipartFile multipartFile : files) {
				File file = fileConverter(multipartFile,environment.getProperty("spring.servlet.multipart.location"));
				deliveryMan.setPicture(file.getName());
		    	deliveryManRepository.save(deliveryMan);}	}}
		
		public static File fileConverter(MultipartFile file, String basePath) {
			String fileName= basePath + file.getOriginalFilename();
			File convertedFile =new File(fileName);
			FileOutputStream fileOutputStream = null;
			try {
				boolean createdFile = convertedFile.createNewFile();
				if (createdFile) {
					fileOutputStream = new FileOutputStream(convertedFile);
					fileOutputStream.write(file.getBytes());
					fileOutputStream.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
				}
				catch (IOException e) {
					e.printStackTrace();	}
			return convertedFile;
			
		}
		}

  
}
