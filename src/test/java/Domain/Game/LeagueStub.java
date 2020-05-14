package Domain.Game;

import java.util.ArrayList;
import java.util.List;

public class LeagueStub extends League {

    private int selector;
    /**
     * Constructor
     *
     * @param leagueName - String - League name
     */
    public LeagueStub(String leagueName, int selector) {
        super(leagueName);
        this.selector = selector;
    }

    @Override
    public List<Season> getSeasons() {
        if(selector == 1){
            List<Season> seasons = new ArrayList<>();
            seasons.add(new SeasonStub(this,"2020/21"));
            return seasons;
        } else {
            return super.getSeasons();
        }
    }

    public void setSelector(int selector) {
        this.selector = selector;
    }

    @Override
    public Season getLatestSeason() {
        if(selector == 91123 || selector == 91223){
            return null;
        }
        else if(selector == 91124 || selector == 91224){
            return new SeasonStub(this,"2020/21");
        }
        return super.getLatestSeason();
    }
}
