package eva.assignment.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    private String name;
    private double budget;
    
    @OneToMany(mappedBy="user")
    private Set<ShareQuantity> wallet = new HashSet<ShareQuantity>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public Set<ShareQuantity> getWallet() {
		return wallet;
	}

	public void setWallet(Set<ShareQuantity> wallet) {
		this.wallet = wallet;
	}

    
    
    

}
