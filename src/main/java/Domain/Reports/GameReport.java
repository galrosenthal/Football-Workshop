package Domain.Reports;

import Domain.Alert;
import Domain.EntityManager;
import Domain.Game.*;
import Domain.Subject;
import Domain.Users.Referee;
import Domain.Users.SystemUser;
import com.itextpdf.layout.element.Text;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameReport extends Report implements Subject {

    Game game;
    List<SystemUser> fans;

    /**
     *
     * constructor
     * @param game
     */
    public GameReport(Game game) {
        this.game = game;
        this.fans = new ArrayList<>();
    }

    /**
     * Add fan to fans list
     * @param systemUser - system user who wants to get alert of this game
     * @return true - if the fan added successfully to fans list and false otherwise
     */
    public boolean addSubscriber(SystemUser systemUser)
    {
        if(fans.contains(systemUser))
        {
            return false;
        }
        else
        {
            fans.add(systemUser);
            return true;
        }
    }

    /**
     * notify fans and referees - game has been start
     */
    public void getGameDate() {
        if(java.time.LocalDate.now().equals(this.game.getGameDate())) {
            //notify referees and fans
            notifyReferees("Game has been started");
            notifyFans("Game has been started");
        }
    }

    /**
     * notify fans when game score has been updated
     *
     */
    public void setScore(Score score) {
        //notify fans!
        notifyFans("Score has been update: "+ score);

    }


    /**
     * notify referees
     */
    private void notifyReferees(String alert)
    {
        List<SystemUser> systemUsers = new ArrayList<>();
        List<Referee> referees = new ArrayList<>();
        for (int i = 0; i < referees.size(); i++) {
            systemUsers.add(referees.get(i).getSystemUser());
        }
        notifyObserver(systemUsers , alert);
    }

    /**
     * notify fans
     */
    private void notifyFans(String alert)
    {
        notifyObserver(fans , alert);

    }

    @Override
    public void notifyObserver(List<SystemUser> systemUsers, String alert) {
        Alert alertInstance = Alert.getInstance();
        alertInstance.update(systemUsers , alert);

    }


    public void produceReport(String folderPath) throws Exception {
        if(!game.hasFinished()){
            throw new Exception("error, the game is not finished yet");
        }
        Team homeTeam = game.getHomeTeam();
        Team awayTeam = game.getAwayTeam();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String startingDateInString = simpleDateFormat.format( game.getGameDate());
        String homeTeamName = homeTeam.getTeamName();
        String awayTeamName = awayTeam.getTeamName();
        String docPath = folderPath+"/GameReport_"+homeTeamName+"_vs_"+awayTeamName+"_"+startingDateInString+".pdf";
        //StringBuilder docContent = new StringBuilder();
        /*docContent.append("Game Report");
        docContent.append("\n\n"+homeTeamName+"\tvs.\t"+awayTeamName);
        docContent.append("\nStadium: "+game.getStadium().getName());
        docContent.append("\nStarting Date: "+game.getGameDate());
        docContent.append("\nEnding Date: "+game.getEndDate());
        docContent.append("\nEvents:");
        for(String event : game.getGameEventsStringList()){
            docContent.append("\n"+event);
        }
        Score gameScore = game.getScore();
        docContent.append("\n\n"+gameScore.toString());*/

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(docPath));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        //document.add(new Text("Game Report"));
        document.add(new Chunk("\n\n"+homeTeamName+"\tvs.\t"+awayTeamName,font));
        /*chunk.append("\n\n"+homeTeamName+"\tvs.\t"+awayTeamName);
        chunk.append("\nStadium: "+game.getStadium().getName());
        chunk.append("\nStarting Date: "+game.getGameDate());
        chunk.append("\nEnding Date: "+game.getEndDate());
        chunk.append("\nEvents:");
        for(String event : game.getGameEventsStringList()){
            chunk.append("\n"+event);
        }
        Score gameScore = game.getScore();
        chunk.append("\n\n"+gameScore.toString());
        document.add(chunk);*/
        document.close();
    }
}
