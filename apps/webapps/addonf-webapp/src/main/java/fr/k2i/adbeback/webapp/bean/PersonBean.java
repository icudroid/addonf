package fr.k2i.adbeback.webapp.bean;

import lombok.Data;

@Data
public class PersonBean {
    private Long id;
	private String firstName;
	private String lastName;

    public String getFullName(){
        return firstName + " " + lastName;
    }

	public PersonBean(Long id, String firstName, String lastName) {
		super();
        this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
