package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Data;

@Entity
@Data
public class ObjectA {
	@Id
	private String name;
	private String subject;

	@OneToMany
	@Cascade(CascadeType.ALL)
	private List<ObjectB> b;
}
