package Domain.Game;

import Domain.Users.TeamOwner;

public class StadiumStub extends Stadium{

    private int selector;

    /**
     * Latest Selector number: 1
     * @param name
     * @param location
     */
    public StadiumStub(String name,String location) {
        super(name,location);
        selector = 0;
    }

    public void setSelector(int selector) {
        this.selector = selector;
    }

    @Override
    public boolean addAllProperties() {
        return true;
    }

    @Override
    public boolean addTeam(Team team, TeamOwner teamOwner) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (selector == 0){
            return true;
        }else if (selector == 1){
            return false;
        }
        else
        {
            return false;
        }
    }
}
