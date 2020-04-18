package Domain.Game;

public class SeasonStub extends Season{

    /**
     * Constructor
     *
     * @param league - League - the league that the season belongs to
     * @param years  - String - the season's years in the format of "yyyy/yy"
     */
    public SeasonStub(League league, String years) {
        super(league, years);
    }
}
