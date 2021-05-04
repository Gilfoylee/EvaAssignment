package eva.assignment.controller;

import eva.assignment.dao.ShareRepo;
import eva.assignment.dao.UserRepo;
import eva.assignment.model.Share;
import eva.assignment.service.ShareService;
import eva.assignment.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    UserService userService;
    
    @Autowired
    ShareService shareService;

    @RequestMapping("/home")
    public String home(){
        return "Eva Home";
    }
    
    @GetMapping(path="/buy")
    public ResponseEntity buyOp(@RequestParam String symbol, @RequestParam int userid,
    		@RequestParam int quantity) 
    {
    	if(!shareService.findById(symbol).isPresent())
    		return ResponseEntity.badRequest().body("Unknown Share Type");
    	if(!userService.findById(userid).isPresent())
    		return ResponseEntity.badRequest().body("Unregistered User");
    	if (!userService.buyShare(userid, symbol, quantity))
    		return ResponseEntity.badRequest().body("No Enough Budget");
		
    	return new ResponseEntity<>( "Share is bought",HttpStatus.OK);
    }
    
    @GetMapping(path="/sell")
    public ResponseEntity sellOp(@RequestParam String symbol, @RequestParam int userid,
    		@RequestParam int quantity) 
    {
    	if(!userService.findById(userid).isPresent())
    		return ResponseEntity.badRequest().body("Unregistered User");
    	if(!shareService.findById(symbol).isPresent())
    		return ResponseEntity.badRequest().body("Unknown Share Type");
    
    	String error = userService.sellShare(userid, symbol, quantity);
    	
    	if(!error.equalsIgnoreCase(""))
    		return ResponseEntity.badRequest().body(error);
		
    	return new ResponseEntity<>("Share is sold",HttpStatus.OK);
    }
}
