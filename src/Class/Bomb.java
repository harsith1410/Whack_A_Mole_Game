package Class;

import javax.swing.*;
import java.awt.Color;

public class Bomb extends HoleOccupant {
    @Override
    public int whack() {
        return -500; // Penalty
    }

    @Override
    public String getType() {
        return "BOMB";
    }

    @Override
    public Icon getImage() {
        Icon icon = new ImageIcon("Images/Bomb.png");
        return icon;
    }
}