package darkapi.data.bank;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.location.Coordinate;
import darkapi.script.utils.PlayerInfo;

/**
 * Created by Valkyr on 30/10/2015.
 */
public enum Bank {
    DEFAULT("Default", null, null),
    LUMBRIDGE_TOP("Lumbridge (Upper)", new Coordinate(3208, 3220, 2), new Coordinate(3208, 3220, 2)),
    ;

    private final String name;
    private final Coordinate osrsLocation;
    private final Coordinate rs3Location;

    Bank(String name, Coordinate osrsLocation, Coordinate rs3Location) {
        this.name = name;
        this.osrsLocation = osrsLocation;
        this.rs3Location = rs3Location;
    }

    @Override
    public String toString() {
        return name;
    }

    public Coordinate getLocation() {
        Coordinate location;
        if (Environment.isOSRS()) location = osrsLocation;
        else location = rs3Location;
        return location == null ? PlayerInfo.myPosition() : location;
    }
}
