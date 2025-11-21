package Class;

import javax.swing.*;


public class Mole extends HoleOccupant {
    @Override
    public int whack() {
        return 100; // Standard points
    }

    @Override
    public String getType() {
        return "MOLE";
    }

    @Override
    public Icon getImage() {
        Icon icon = new ImageIcon("Images/Mole.png");
        return icon;
    }
}