package ml.x1carbon.concetration_3;

public class AddPlayer {

    String playerName;
    String playerScore;
    String playerGameMode;
    String playerGameDifficulty;
    String playerID;



    public AddPlayer() {
    }

    public AddPlayer(String playerName, String playerScore, String playerGameMode, String playerGameDifficulty, String playerID) {
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.playerGameMode = playerGameMode;
        this.playerGameDifficulty = playerGameDifficulty;
        this.playerID = playerID;
    }


    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(String playerScore) {
        this.playerScore = playerScore;
    }

    public String getPlayerGameMode() {
        return playerGameMode;
    }

    public void setPlayerGameMode(String playerGameMode) {
        this.playerGameMode = playerGameMode;
    }

    public String getPlayerGameDifficulty() {
        return playerGameDifficulty;
    }

    public void setPlayerGameDifficulty(String playerGameDifficulty) {
        this.playerGameDifficulty = playerGameDifficulty;
    }
}
