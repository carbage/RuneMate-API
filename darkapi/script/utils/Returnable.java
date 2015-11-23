package darkapi.script.utils;

/**
 * Created by Valkyr on 29/10/2015.
 */
public abstract class Returnable<T> {

    public abstract T get();

    @Override
    public String toString() {
        if (get() != null)
            return get().toString();
        return null;
    }
}