package domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subject {
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty name = new SimpleStringProperty("");
	private IntegerProperty workload = new SimpleIntegerProperty(0);
	private BooleanProperty isActive = new SimpleBooleanProperty(false);
	
	@Override
	public String toString() {
		return getName();
	}

	public final IntegerProperty idProperty() {
		return this.id;
	}

	public final int getId() {
		return this.idProperty().get();
	}

	public final void setId(final int id) {
		this.idProperty().set(id);
	}

	public final StringProperty nameProperty() {
		return this.name;
	}
	
	public final String getName() {
		return this.nameProperty().get();
	}
	
	public final void setName(final String name) {
		this.nameProperty().set(name);
	}
	
	public final IntegerProperty workloadProperty() {
		return this.workload;
	}
	
	public final int getWorkload() {
		return this.workloadProperty().get();
	}
	
	public final void setWorkload(final int workload) {
		this.workloadProperty().set(workload);
	}

	public final BooleanProperty isActiveProperty() {
		return this.isActive;
	}

	public final boolean getIsActive() {
		return this.isActiveProperty().get();
	}

	public final void setIsActive(final boolean isActive) {
		this.isActiveProperty().set(isActive);
	}
}
