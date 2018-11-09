package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Farm {
	private final ObservableList<Animal> animals;

	public Farm() {
		super();
		animals = FXCollections.observableArrayList();
	}

	public void addAnimal(final Animal animal) {
		animals.add(animal);
	}
}
