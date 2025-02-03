import java.awt.*;
import javax.swing.*;

public class RoundedPanel extends JPanel
{
    private int arcWidth;
    private int arcHeight;

    public RoundedPanel(int arcWidth, int arcHeight)
    {
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        // Draw the rounded rectangle with specified arc width and height
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
    }
}
