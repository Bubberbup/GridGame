public class PlayerCharacter {
    private String characterName;
    private static int characterHP = 100; // Player's health points
    private boolean characterIsAlive = true;

    public void setCharacterName(String name) {
        characterName = name;
    }

    public void setCharacterHp(int hp) {
        characterHP = hp;
    }

    public void setcharacterIsAlive(boolean status) {
        characterIsAlive = status;
    }

    public String getCharacterName() {
        return characterName;
    }

    public int getCharacterHP() {
        return characterHP;
    }

    public boolean setCharacterIsAlive() {
        return characterIsAlive;
    }
}
