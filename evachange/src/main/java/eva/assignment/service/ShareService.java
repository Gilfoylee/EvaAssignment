package eva.assignment.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import eva.assignment.dao.ShareRepo;
import eva.assignment.model.Share;

@Service
public class ShareService {
	
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	
	@Autowired
	ShareRepo shareRepo;
	
	
	public Optional<Share> findById(String id) {
		return shareRepo.findById(id);
	}
	
	public List<Share> findAll(){
		return (List<Share>) shareRepo.findAll();
	}
	
	 @Scheduled(fixedRate = 1000)
	 public void delayTask() throws InterruptedException{
	    
	    Thread.sleep(3600*1000); // 1 hour
	    
	    editRates();
	    
	  }
	 
	 // Update price of the share on an hourly basis
	 private void editRates() {
		 List<Share> shList = findAll();
		 Random rn = new Random();
		 
		 // for each share
		 for (int i = 0; i < shList.size(); i++) {
			 Share sh = shList.get(i);
			 
			 // add random value between (-0.5,0.5)
			 double newRate = sh.getRate()+rn.nextDouble()-0.50;
			 if(newRate <= 0) {
				 i--;
				 continue;
			 }
			 String str = df2.format(newRate);
			 str = str.substring(0,1)+"."+str.substring(2); //The rate of the share should be exactly 2 decimal digits.
			 newRate = Double.parseDouble(str);
			 sh.setRate(newRate);
			 shareRepo.save(sh);
			 
		}
	 }
	 
	/* implement other functions */

}
