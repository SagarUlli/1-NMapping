package model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ObjectB {
	@Id
	private String name;
	private int age;
}
