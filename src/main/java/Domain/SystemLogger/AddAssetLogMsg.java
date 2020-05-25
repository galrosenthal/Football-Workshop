package Domain.SystemLogger;

public class AddAssetLogMsg extends LoggerInfoMessage {
    public AddAssetLogMsg(String usernamePerformed, String assetType, String assetName, String teamName){
        super(usernamePerformed, "Add Asset",
                "New "+assetType+", "+assetName+", added to team \""+teamName+"\"");
    }
}
