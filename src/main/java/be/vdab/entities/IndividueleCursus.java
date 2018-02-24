package be.vdab.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "individuelecursussen")
@DiscriminatorValue("I")
public class IndividueleCursus extends Cursus {
	private static final long serialVersionUID = 1L;
	private int duurtijd;

}
