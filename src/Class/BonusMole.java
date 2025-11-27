package Class;

import javax.swing.*;

public class BonusMole extends HoleOccupant {
    @Override
    public int whack() {
        return 500;
    }

    @Override
    public String getType() {
        return "BONUS";
    }

    @Override
    public Icon getImage() {
        Icon icon = new ImageIcon("Images/GoldMole.png");
        return icon;
    }
}