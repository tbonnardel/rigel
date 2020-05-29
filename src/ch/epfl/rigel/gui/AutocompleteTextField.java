package ch.epfl.rigel.gui;

import javafx.collections.FXCollections;
import javafx.geometry.Side;
import javafx.scene.control.*;

import java.util.*;


/**
 * Cette classe représente un champ de texte avec auto-completion.
 * Librement inspiré de https://gist.github.com/floralvikings/10290131
 *
 * @author Thomas Bonnardel (319827)
 */
public final class AutocompleteTextField extends TextField {

    private Set<String> suggestions;
    private ContextMenu autocompletionBlock;

    private final static int MAX_NAMES_ON_DIPLAY = 10;

    /**
     * Constructeur de champ de texte avec auto-completion.
     */
    public AutocompleteTextField() {
        this.suggestions = new TreeSet<>();
        autocompletionBlock = new ContextMenu();

        // Attache l'auditeur
        this.textProperty().addListener(
                (p, o, n) -> hydrateAutocompletionBlock(findMatchValues(n))
        );
    }

    /**
     * Méthode qui définie l'ensemble des suggestions à afficher.
     *
     * @param suggestions les suggestions à afficher
     */
    public void setSuggestions(Set<String> suggestions) {
        this.suggestions = suggestions;
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
        for (String s : suggestions) {
            if (set.size() > MAX_NAMES_ON_DIPLAY)
                break;

            if (s.startsWith(prefix.toUpperCase()))
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
