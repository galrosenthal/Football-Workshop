package Domain.Game;

import java.util.List;

public interface Asset {

    public List<String> getProperties();
    public boolean changeProperty(String property);
}
