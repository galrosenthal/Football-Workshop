package Domain.Game;

import Domain.Users.TeamOwner;

import java.util.List;

public interface Asset {

    List<String> getProperties();

    boolean addTeam(Team team, TeamOwner teamOwner);

    boolean changeProperty(String propertyToChange, String newValue);

    boolean isListProperty(String property);

    boolean isStringProperty(String property);

    boolean isEnumProperty(String property);

    boolean addProperty(String property);

    boolean addAllProperties();

    boolean removeProperty(String property);

    List<Enum> getAllValues(String property);
}
