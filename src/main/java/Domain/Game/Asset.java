package Domain.Game;

import java.util.List;

public interface Asset {

    List<String> getProperties();

    boolean changeProperty(String propertyToChange, String newValue);

    boolean isListProperty(String property);

    boolean isStringProperty(String property);

    boolean isEnumProperty(String property);

    void addProperty();

    void removeProperty();

    List<Enum> getAllValues(String property);
}
