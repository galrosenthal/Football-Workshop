package Domain.Reports;

import Domain.Recommendation.RecommendationSystem;
import Domain.Users.Fan;
import Domain.Users.User;

import java.util.List;

public class Report {

    RecommendationSystem recSys;
    List<User> watchingUsers;
    List<Fan> subscribedUsers;
}
