/*  Created April 29, 2020 Raiden van Bronkhorst
 *
 */

import java.util.Scanner;
import java.io.File;
import java.util.regex.*;

public class Skyline {

  private Building[] buildings;

  public Skyline(Building[] buildings) {
    this.buildings = buildings;
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("usage: java Skyline filename");
      System.exit(1);
    }
    Skyline skyline = new Skyline(parseFile(args[0]));
  }

  private static Building[] parseFile(String filename) {
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

  private static class Building {

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
}
