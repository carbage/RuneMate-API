package darkapi.script.utils.onymous;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.entities.definitions.GameObjectDefinition;
import com.runemate.game.api.hybrid.queries.LocatableEntityQueryBuilder;
import com.runemate.game.api.hybrid.queries.QueryBuilder;
import com.runemate.game.api.hybrid.queries.results.LocatableEntityQueryResults;
import com.runemate.game.api.hybrid.queries.results.QueryResults;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Valkyr on 18/10/2015.
 */
public class OnymousQueryBuilder extends LocatableEntityQueryBuilder<LocatableEntity, OnymousQueryBuilder> {
    private List<LocatableEntity> raw() {
        LocatableEntityQueryResults<Npc> npcs = Npcs.getLoaded();
        LocatableEntityQueryResults<GameObject> objects = GameObjects.getLoaded();
        List<LocatableEntity> list = new ArrayList<>();
        list.addAll(npcs);
        list.addAll(objects);
        return list;
    }

    public OnymousQueryBuilder string(Predicate<String> stringFilter) {
        return filter(new Predicate<LocatableEntity>() {
            @Override
            public boolean test(LocatableEntity locatableEntity) {
                if (locatableEntity instanceof GameObject) {
                    GameObjectDefinition definition = ((GameObject) locatableEntity).getDefinition();
                    if (definition == null)
                        return false;
                    return stringFilter.test(definition.getName());
                }
                Npc npc = (Npc) locatableEntity;
                return stringFilter.test(npc.getName());
            }
        });
    }
}