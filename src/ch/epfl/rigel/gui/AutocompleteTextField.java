package ch.epfl.rigel.gui;

import javafx.collections.FXCollections;
import javafx.geometry.Side;
import javafx.scene.control.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * Cette classe représente un champ de texte avec auto-completion.
 * Librement inspiré de https://gist.github.com/floralvikings/10290131
 *
 * @author Thomas Bonnardel (319827)
 */
public final class AutocompleteTextField extends TextField {
    private final Set<String> values;
    private ContextMenu autocompletionBlock;

    /**
     * Constructeur de champ de texte avec auto-completion.
     *
     * @param values l'ensemble des valeurs acceptables
     */
    public AutocompleteTextField(Set<String> values) {
        this.values = values;
        autocompletionBlock = new ContextMenu();

        // Attache l'auditeur
        this.textProperty().addListener(
                (p, o, n) -> {
                    System.out.println(findMatchValues(n));
                    hydrateAutocompletionBlock(findMatchValues(n));
                }
        );
    }

    /**
     * Méthode privée qui retourne l'ensemble des valeurs possibles
     * commençant par le même préfixe.
     * Si le préfixe est nul ou blanc, elle retourne null.
     *
     * @param prefix le préfixe
     * @return l'ensemble des valeurs possibles commençant par le préfixe donné
     */
    private Set<String> findMatchValues(String prefix) {
        if (prefix == null || prefix.isEmpty())
            return null;

        Set<String> set = new TreeSet<>();
        for (String s : values) {
            if (s.startsWith(prefix))
                set.add(s);
        }

        return set;
    }

    /**
     * Méthode privée qui hydrate et affiche le bloc d'auto-completion en fonction
     * de l'ensemble des propositions données.
     *
     * @param propositions l'ensemble des propositions à afficher
     */
    private void hydrateAutocompletionBlock(Set<String> propositions) {
        if (propositions == null || propositions.isEmpty()) {
            autocompletionBlock.getItems().clear();
            autocompletionBlock.hide();
            return;
        }
        List<CustomMenuItem> items = new LinkedList<>();
        for (String name : propositions) {
            Label itemLabel = new Label(name);
            CustomMenuItem item = new CustomMenuItem(itemLabel, true);
            item.setOnAction(e -> {
                this.setText(name);
            });
            items.add(item);
        }

        autocompletionBlock.getItems().clear();
        autocompletionBlock.getItems().addAll(FXCollections.observableList(items));
        autocompletionBlock.show(this, Side.BOTTOM, 0, 0);
    }




}
