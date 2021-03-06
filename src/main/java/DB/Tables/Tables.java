/*
 * This file is generated by jOOQ.
 */
package DB.Tables;


import DB.Tables.tables.Alert;
import DB.Tables.tables.AssociationRepresentative;
import DB.Tables.tables.Coach;
import DB.Tables.tables.CoachInTeam;
import DB.Tables.tables.ErrorLog;
import DB.Tables.tables.EventCard;
import DB.Tables.tables.EventGoal;
import DB.Tables.tables.EventInjury;
import DB.Tables.tables.EventLog;
import DB.Tables.tables.EventOffside;
import DB.Tables.tables.EventPenalty;
import DB.Tables.tables.EventSwitchPlayers;
import DB.Tables.tables.Game;
import DB.Tables.tables.GamesInSeason;
import DB.Tables.tables.League;
import DB.Tables.tables.ManagerInTeams;
import DB.Tables.tables.OwnedTeams;
import DB.Tables.tables.Player;
import DB.Tables.tables.PlayerInTeam;
import DB.Tables.tables.PointsPolicy;
import DB.Tables.tables.Referee;
import DB.Tables.tables.RefereeInGame;
import DB.Tables.tables.RefereeInSeason;
import DB.Tables.tables.SchedulingPolicy;
import DB.Tables.tables.Season;
import DB.Tables.tables.Stadium;
import DB.Tables.tables.StadiumHomeTeams;
import DB.Tables.tables.SystemAdmin;
import DB.Tables.tables.Systemuser;
import DB.Tables.tables.Team;
import DB.Tables.tables.TeamManager;
import DB.Tables.tables.TeamOwner;
import DB.Tables.tables.TeamsInSeason;
import DB.Tables.tables.UserComplaint;
import DB.Tables.tables.UserRoles;


/**
 * Convenience access to all tables in fwdb
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>fwdb.alert</code>.
     */
    public static final Alert ALERT = Alert.ALERT;

    /**
     * The table <code>fwdb.association_representative</code>.
     */
    public static final AssociationRepresentative ASSOCIATION_REPRESENTATIVE = AssociationRepresentative.ASSOCIATION_REPRESENTATIVE;

    /**
     * The table <code>fwdb.coach</code>.
     */
    public static final Coach COACH = Coach.COACH;

    /**
     * The table <code>fwdb.coach_in_team</code>.
     */
    public static final CoachInTeam COACH_IN_TEAM = CoachInTeam.COACH_IN_TEAM;

    /**
     * The table <code>fwdb.error_log</code>.
     */
    public static final ErrorLog ERROR_LOG = ErrorLog.ERROR_LOG;

    /**
     * The table <code>fwdb.event_card</code>.
     */
    public static final EventCard EVENT_CARD = EventCard.EVENT_CARD;

    /**
     * The table <code>fwdb.event_goal</code>.
     */
    public static final EventGoal EVENT_GOAL = EventGoal.EVENT_GOAL;

    /**
     * The table <code>fwdb.event_injury</code>.
     */
    public static final EventInjury EVENT_INJURY = EventInjury.EVENT_INJURY;

    /**
     * The table <code>fwdb.event_log</code>.
     */
    public static final EventLog EVENT_LOG = EventLog.EVENT_LOG;

    /**
     * The table <code>fwdb.event_offside</code>.
     */
    public static final EventOffside EVENT_OFFSIDE = EventOffside.EVENT_OFFSIDE;

    /**
     * The table <code>fwdb.event_penalty</code>.
     */
    public static final EventPenalty EVENT_PENALTY = EventPenalty.EVENT_PENALTY;

    /**
     * The table <code>fwdb.event_switch_players</code>.
     */
    public static final EventSwitchPlayers EVENT_SWITCH_PLAYERS = EventSwitchPlayers.EVENT_SWITCH_PLAYERS;

    /**
     * The table <code>fwdb.game</code>.
     */
    public static final Game GAME = Game.GAME;

    /**
     * The table <code>fwdb.games_in_season</code>.
     */
    public static final GamesInSeason GAMES_IN_SEASON = GamesInSeason.GAMES_IN_SEASON;

    /**
     * The table <code>fwdb.league</code>.
     */
    public static final League LEAGUE = League.LEAGUE;

    /**
     * The table <code>fwdb.manager_in_teams</code>.
     */
    public static final ManagerInTeams MANAGER_IN_TEAMS = ManagerInTeams.MANAGER_IN_TEAMS;

    /**
     * The table <code>fwdb.owned_teams</code>.
     */
    public static final OwnedTeams OWNED_TEAMS = OwnedTeams.OWNED_TEAMS;

    /**
     * The table <code>fwdb.player</code>.
     */
    public static final Player PLAYER = Player.PLAYER;

    /**
     * The table <code>fwdb.player_in_team</code>.
     */
    public static final PlayerInTeam PLAYER_IN_TEAM = PlayerInTeam.PLAYER_IN_TEAM;

    /**
     * The table <code>fwdb.points_policy</code>.
     */
    public static final PointsPolicy POINTS_POLICY = PointsPolicy.POINTS_POLICY;

    /**
     * The table <code>fwdb.referee</code>.
     */
    public static final Referee REFEREE = Referee.REFEREE;

    /**
     * The table <code>fwdb.referee_in_game</code>.
     */
    public static final RefereeInGame REFEREE_IN_GAME = RefereeInGame.REFEREE_IN_GAME;

    /**
     * The table <code>fwdb.referee_in_season</code>.
     */
    public static final RefereeInSeason REFEREE_IN_SEASON = RefereeInSeason.REFEREE_IN_SEASON;

    /**
     * The table <code>fwdb.scheduling_policy</code>.
     */
    public static final SchedulingPolicy SCHEDULING_POLICY = SchedulingPolicy.SCHEDULING_POLICY;

    /**
     * The table <code>fwdb.season</code>.
     */
    public static final Season SEASON = Season.SEASON;

    /**
     * The table <code>fwdb.stadium</code>.
     */
    public static final Stadium STADIUM = Stadium.STADIUM;

    /**
     * The table <code>fwdb.stadium_home_teams</code>.
     */
    public static final StadiumHomeTeams STADIUM_HOME_TEAMS = StadiumHomeTeams.STADIUM_HOME_TEAMS;

    /**
     * The table <code>fwdb.system_admin</code>.
     */
    public static final SystemAdmin SYSTEM_ADMIN = SystemAdmin.SYSTEM_ADMIN;

    /**
     * The table <code>fwdb.systemuser</code>.
     */
    public static final Systemuser SYSTEMUSER = Systemuser.SYSTEMUSER;

    /**
     * The table <code>fwdb.team</code>.
     */
    public static final Team TEAM = Team.TEAM;

    /**
     * The table <code>fwdb.team_manager</code>.
     */
    public static final TeamManager TEAM_MANAGER = TeamManager.TEAM_MANAGER;

    /**
     * The table <code>fwdb.team_owner</code>.
     */
    public static final TeamOwner TEAM_OWNER = TeamOwner.TEAM_OWNER;

    /**
     * The table <code>fwdb.teams_in_season</code>.
     */
    public static final TeamsInSeason TEAMS_IN_SEASON = TeamsInSeason.TEAMS_IN_SEASON;

    /**
     * The table <code>fwdb.user_complaint</code>.
     */
    public static final UserComplaint USER_COMPLAINT = UserComplaint.USER_COMPLAINT;

    /**
     * The table <code>fwdb.user_roles</code>.
     */
    public static final UserRoles USER_ROLES = UserRoles.USER_ROLES;
}
