package ch.epfl.rigel.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;

/**
 * Code provenant de la section 3 de l'énoncé de l'étape 10 du projet.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class UseCreateBinding {
    public static void main(String[] args) {
        StringProperty s = new SimpleStringProperty("Hi!");
        IntegerProperty b = new SimpleIntegerProperty(0);
        IntegerProperty e = new SimpleIntegerProperty(3);

        ObservableStringValue ss = Bindings.createStringBinding(
                () -> s.getValue().substring(b.get(), e.get()),
                s, b, e);
        ss.addListener(o -> System.out.println(ss.get()));

        System.out.println("----");
        s.set("Hello, world!");
        s.setValue("Bonjour, monde !");
        e.set(16);
        b.set(9);
        System.out.println("----");
    }
}
