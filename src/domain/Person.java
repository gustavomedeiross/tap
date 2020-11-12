package domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	IntegerProperty id = new SimpleIntegerProperty(0);
	StringProperty name = new SimpleStringProperty("");
	StringProperty email = new SimpleStringProperty("");
	StringProperty phone = new SimpleStringProperty("");
	StringProperty birthDate = new SimpleStringProperty("");
	StringProperty gender = new SimpleStringProperty("");
	StringProperty type = new SimpleStringProperty("");
	BooleanProperty isActive = new SimpleBooleanProperty(true);

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
	
	public final StringProperty emailProperty() {
		return this.email;
	}
	
	public final String getEmail() {
		return this.emailProperty().get();
	}
	
	public final void setEmail(final String email) {
		this.emailProperty().set(email);
	}
	
	public final StringProperty phoneProperty() {
		return this.phone;
	}
	
	public final String getPhone() {
		return this.phoneProperty().get();
	}
	
	public final void setPhone(final String phone) {
		this.phoneProperty().set(phone);
	}
	
	public final StringProperty birthDateProperty() {
		return this.birthDate;
	}
	
	public final String getBirthDate() {
		return this.birthDateProperty().get();
	}
	
	public final void setBirthDate(final String birthDate) {
		this.birthDateProperty().set(birthDate);
	}
	
	public final StringProperty genderProperty() {
		return this.gender;
	}
	
	public final String getGender() {
		return this.genderProperty().get();
	}
	
	public final void setGender(final String gender) {
		this.genderProperty().set(gender);
	}
	
	public final StringProperty typeProperty() {
		return this.type;
	}
	
	public final String getType() {
		return this.typeProperty().get();
	}
	
	public final void setType(final String type) {
		this.typeProperty().set(type);
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

	@Override
	public String toString() {
		return getName();
	}
}
