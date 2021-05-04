package eva.assignment.service;

import eva.assignment.dao.ShareQuantityRepo;
import eva.assignment.dao.ShareRepo;
import eva.assignment.dao.UserRepo;
import eva.assignment.model.Share;
import eva.assignment.model.ShareQuantity;
import eva.assignment.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ShareQuantityRepo shareQuantityRepo;
    
    @Autowired
    ShareRepo shareRepo;
    
    @PersistenceContext
	EntityManager entityManager;

    public Optional<User> findById(int id){
    	return userRepo.findById(id);
    }
    
 
    
    // Buying Part
    public boolean buyShare(int id, String symbol, int quantity){

    	User user = findById(id).get();
    	Share share = shareRepo.findById(symbol).get();
    	
    	if(user.getBudget() < share.getRate() * quantity) // Not enough budget
    		return false;
    	else {
    		addQuantity(user, symbol, quantity);
    		user.setBudget(user.getBudget()-share.getRate()*quantity); // decrease budget
    		userRepo.save(user);
    	}
    		
    		
    	return true;
    	
    }
    
    public boolean addQuantity(User user, String symbol, int quantity) {
    	
    	List<ShareQuantity> share =  entityManager.createQuery("from ShareQuantity where symbol = :symb and user = :uid", ShareQuantity.class)
    	    	.setParameter("symb", symbol).setParameter("uid", user).getResultList();
    	   
    				if(share.size() == 1) { // if the person already has the share type (just increase)
    					share.get(0).setQuantity(share.get(0).getQuantity()+quantity);
    					shareQuantityRepo.save(share.get(0));
    				}
    				else { // if it's the first trade of the share type
    					ShareQuantity newShare = new ShareQuantity();
    					newShare.setQuantity(quantity);
    					newShare.setSymbol(symbol);
    					newShare.setUser(user);
    					shareQuantityRepo.save(newShare);
    				}
    			
    	        return true;
    }
    
    
    // Selling Part
    
    public String sellShare(int id, String symbol, int quantity) {
    	
    	User user = findById(id).get();
    	
    	List<ShareQuantity> share =  entityManager.createQuery("from ShareQuantity where symbol = :symb and user = :uid", ShareQuantity.class)
    	    	.setParameter("symb", symbol).setParameter("uid", user).getResultList();
    	
    	if(share.size()!=1) // if the user never have had the share type
    		return "The share couldn't be found in the portfolio";
    	else {
			ShareQuantity newShare = share.get(0);
			if (newShare.getQuantity() < quantity) // if the number of share is less than given quantity
				return "The number of shares not enough to be sold";
			else {
				newShare.setQuantity(newShare.getQuantity()-quantity);
				shareQuantityRepo.save(newShare);
				
				double price = shareRepo.findById(symbol).get().getRate();
				user.setBudget(user.getBudget()+price*quantity);
				userRepo.save(user);
			}	
		}
   
    	return "";
    }
    
    /* implement other functions */
}
