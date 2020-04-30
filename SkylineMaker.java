/*  Created April 29, 2020 Raiden van Bronkhorst
 *
 */

import java.util.Scanner;
import java.io.File;
import java.util.regex.*;
import java.util.ArrayList;

public class SkylineMaker {

  public Building[] buildings;

  public SkylineMaker(Building[] buildings) {
    this.buildings = buildings;
  }

  public Skyline getSkyline(Building[] b, int x0, int x1) {
    if (x0 == x1) {
      Skyline s = new Skyline();
      s.roofs.add(new Roof(b[x0].l, b[x0].h));
      s.roofs.add(new Roof(b[x0].r, 0));
      return s;
    }
    int mid = (x0 + x1) / 2;

    Skyline s0 = getSkyline(b, x0, mid);
    Skyline s1 = getSkyline(b, mid + 1, x1);
    return s0.merge(s1);
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("usage: java Skyline filename");
      System.exit(1);
    }
    if (!(new File(args[0]).exists())) {
      System.err.println("No such file " + args[0]);
      System.exit(1);
    }
    
    SkylineMaker skylineMaker = new SkylineMaker(parseFile(args[0]));
    Skyline skyline = skylineMaker.getSkyline(skylineMaker.buildings, 0, skylineMaker.buildings.length - 1);

    for (Roof r : skyline.roofs) {
      System.out.println(r.x + ", " + r.h);
    }
  }

  public static Building[] parseFile(String filename) {
    try {
      Scanner s = new Scanner(new File(filename));
      String line = s.nextLine();

      if (!Pattern.matches("(<\\d+ \\d+ \\d+>)+", line)) {
        System.err.println("Input file is not written in the correct format.\nFile should be formatted as <l0 h0 r0><l1 h1 r1> where li, hi, ri are integers.");
        System.exit(1);
      }

      String[] bStrings = (line.substring(1, line.length() - 1).split("><", 0));
      Building[] buildings = new Building[bStrings.length];

      for (int i = 0; i < bStrings.length; i++) {
        String[] entries = bStrings[i].split(" ");

        Building b = new Building();
        
        b.l = Integer.parseInt(entries[0]);
        b.h = Integer.parseInt(entries[1]);
        b.r = Integer.parseInt(entries[2]);

        buildings[i] = b;

      }
      return buildings;

    } catch (Exception e) {
      System.err.println(e.toString());
    }
    return new Building[0];
  }

  public static class Building {

    int l, h, r;

    public Building(int l, int h, int r) {
      this.l = l;
      this.h = h;
      this.r = r;
    }

    public Building() {
      l = 0;
      h = 0;
      r = 0;
    }
  }

  public class Skyline {
    
    public ArrayList<Roof> roofs = new ArrayList<>();

    public Skyline merge(Skyline s1) {
      Skyline merged = new Skyline();

      int h0 = 0;
      int h1 = 0;

      int i = 0;
      int j = 0;
      int oldValue = 0;

      while (i < roofs.size() && j < s1.roofs.size()) {
        if (roofs.get(i).x < s1.roofs.get(j).x) {
          h0 = roofs.get(i).h;

          if (oldValue != Math.max(h0, h1)) {
            merged.roofs.add(new Roof(roofs.get(i).x, Math.max(h0, h1)));
            oldValue = Math.max(h0, h1);
          }
          i++;
        } else {
          h1 = s1.roofs.get(j).h;

          if (oldValue != Math.max(h0, h1)) {
            merged.roofs.add(new Roof(s1.roofs.get(j).x, Math.max(h0, h1)));
            oldValue = Math.max(h0, h1);
          }
          j++;
        }
      }
      while (i < roofs.size()) {
        merged.roofs.add(roofs.get(i));
        i++;
      }
      while (j < s1.roofs.size()) {
        merged.roofs.add(s1.roofs.get(j));
        j++;
      }
      return merged;
    }

  }

  public class Roof {
    int x, h;

    public Roof(int x, int h) {
      this.x = x;
      this.h = h;
    }
  }
}
