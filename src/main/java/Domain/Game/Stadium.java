package Domain.Game;

import java.util.Objects;

public class Stadium {

    String stadName;
    String stadLocation;

    public Stadium(String stadName, String stadLocation) {
        this.stadName = stadName;
        this.stadLocation = stadLocation;
    }

    public String[] getProperties() {
        String [] properties = new String[3];
        properties[0] = "Name";
        properties[1] = "Location";
        return properties;
    }

    public boolean changeProperty(String property)
    {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stadium)) return false;
        Stadium stadium = (Stadium) o;
        return stadName.equals(stadium.stadName) &&
                stadLocation.equals(stadium.stadLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stadName, stadLocation);
    }


    public String getStadName() {
        return stadName;
    }

    public String getStadLocation() {
        return stadLocation;
    }
}
