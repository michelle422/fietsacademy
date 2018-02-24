package be.vdab.entities;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "groepscursussen")
@DiscriminatorValue("G")
public class GroepsCursus extends Cursus {
	private static final long serialVersionUID = 1L;
	private LocalDate van;
	private LocalDate tot;
}
