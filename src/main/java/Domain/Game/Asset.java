package Domain.Game;

import Domain.Users.TeamOwner;

import java.util.List;

public interface Asset {

    List<String> getProperties();


    String getAssetName();

    /**
     *
     * @param team
     * @param teamOwner
     * @return
     */
    boolean addTeam(Team team, TeamOwner teamOwner);

    /**
     * change propertyToChange to newValue
     * @param propertyToChange
     * @param newValue
     * @return true - successfully change to new value
     */
    boolean changeProperty(Team teamOfAsset, String propertyToChange, String newValue);

    /**
     *
     * @param property
     * @return true if property is list type
     */
    boolean isListProperty(String property);

    /**
     *
     * @param property
     * @return true if property is String type
     */
    boolean isStringProperty(String property);

    /**
     *
     * @param property
     * @return true if property is list Enum
     */
    boolean isEnumProperty(String property);

    /**
     *
     * @param property
     * @return
     */
    boolean addProperty(Team teamOfAsset, String property);

    /**
     * @return true - all properties has been added successfully
     */
    boolean addAllProperties(Team teamOfAsset);

    /**
     *
     * @param property
     * @return
     */
    boolean removeProperty(Team teamOfAsset, String property);

    /**
     *
     * @param property - is Enum type
     * @return all the values Enum - property
     */
    List<Enum> getAllValues(String property);

    /**
     * Assumption - propertyName is Enum type
     * @param team
     * @param propertyName
     * @return List<Enum> - all values property the asset has from specific team
     */
    List<Enum> getAllPropertyList(Team team, String propertyName);

    /**
     * Assumptions - propertyName is Enum type , propertyName - list
     * add Enum to property list type
     * @param propertyName
     * @param anEnum
     * @param team
     * @return true - added successfully
     */
    boolean addProperty(String propertyName, Enum anEnum ,Team team);

    /**
     * Assumptions - propertyName is Enum type , propertyName - list
     * remove Enum to property list type
     * @param propertyName
     * @param anEnum
     * @param team
     * @return true - removed successfully
     */
    boolean removeProperty(String propertyName, Enum anEnum, Team team);

}
