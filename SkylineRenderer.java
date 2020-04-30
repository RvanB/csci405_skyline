/* Created April 29, 2020 Raiden van Bronkhorst
 *
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

public class SkylineRenderer extends JPanel{
  
  private int w, h;
  private SkylineMaker.Skyline skyline;
  private SkylineMaker maker;

  public static void main(String[] args) {
    int tileSize = 20;
    SkylineMaker maker = new SkylineMaker(SkylineMaker.parseFile(args[0]));
    SkylineMaker.Skyline skyline = maker.getSkyline(maker.buildings, 0, maker.buildings.length - 1);

    int w = tileSize * skyline.roofs.get(skyline.roofs.size() - 1).x;

    int h = skyline.roofs.get(0).h;
    for (int i = 1; i < skyline.roofs.size(); i++) {
      if (skyline.roofs.get(i).h > h) {
        h = skyline.roofs.get(i).h + 2;
      }
    }
    h *= tileSize;
    SkylineRenderer renderer = new SkylineRenderer(w, h, skyline, maker);
    
    JFrame frame = new JFrame("Skyline Renderer");
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(renderer);
    frame.pack();
    frame.setVisible(true);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, this.w, this.h);
    
    for (SkylineMaker.Building b : this.maker.buildings) {
      g.setColor(new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255), 75));
      g.fillRect(b.l * 20, this.h - 20 * b.h, 20 * (b.r - b.l), 20 * b.h);
    }

    g.setColor(Color.RED);
    for (int i = 0; i < skyline.roofs.size() - 1; i++) {
      SkylineMaker.Roof r = skyline.roofs.get(i);
      SkylineMaker.Roof n = skyline.roofs.get(i + 1);
      g.fillRect(r.x * 20, this.h - 20 * r.h, 20 * (n.x - r.x), 5);
    }
  }

  public SkylineRenderer(int w, int h, SkylineMaker.Skyline skyline, SkylineMaker maker) {
    this.maker = maker;
    this.skyline = skyline;
    this.w = w;
    this.h = h;
    setMinimumSize(new Dimension(w, h));
    setPreferredSize(new Dimension(w, h));
    setMaximumSize(new Dimension(w, h));
  }
  

}
