package darkapi.script.utils.onymous;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.details.Interactable;
import com.runemate.game.api.hybrid.queries.results.QueryResults;

import java.util.Collection;

/**
 * InteractableQueryResults
 *
 * @author Ian
 * @version 1.0
 */
public class InteractableQueryResults extends QueryResults<Interactable, InteractableQueryResults> {
    public InteractableQueryResults(Collection<? extends Interactable> a) {
        super(a);
    }
}