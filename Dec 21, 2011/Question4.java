import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Question4 {
	private static int steps, height, width, y, x, yb, xb, yc, xc;
	private static String[] fields; // store temporary input etc.
	private static char[][] map, clone; // map contains the original maze, clone is clone.

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("src/DATA4.txt"));
		BufferedWriter out = new BufferedWriter(new FileWriter("src/OUT4.txt"));

		for (int i = 0; i < 5; i++) {
			// reading in fields
			fields = in.readLine().split(" ");
			height = Integer.parseInt(fields[0]);
			width = Integer.parseInt(fields[1]);

			// initialize map
			map = new char[height][width];
			for (int h = 0; h < height; h++) {
				fields[0] = in.readLine();
				for (int w = 0; w < width; w++) {
					map[h][w] = fields[0].charAt(w);
					if (map[h][w] == 'A') {
						y = h;
						x = w;
						// System.out.println("A at ("+x+","+y+")");
					} else if (map[h][w] == 'B') {
						yb = h;
						xb = w;
						// System.out.println("B at ("+xb+","+yb+")");
					} else if (map[h][w] == 'C') {
						yc = h;
						xc = w;
						// System.out.println("C at ("+xc+","+yc+")");
					}
				}
			}

			remap();
			steps = maze('B', y, x) - 1; // -1 removes the last addition which does not count...
			//System.out.println("step 1:" + steps);

			remap();
			steps += maze('C', yb, xb) - 1;
			//System.out.println("step 1+2:" + steps);

			remap();
			steps += maze('A', yc, xc) - 1;
			//System.out.println("total:" + steps);

			out.write(steps + ""); // display random characters without forcing String.
			out.newLine();
		}
		out.close();
	}

	private static void remap() {
		clone = new char[map.length][map[0].length];
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				clone[y][x] = map[y][x];
			}
		}
	}

	private static int maze(char goal, int y, int x) {
		int n, e, s, w, out = -1;
		// System.out.println(clone[y][x] + " " + goal);

		if (clone[y][x] == goal) {
			return 1;
		} else if (clone[y][x] == '#' || clone[y][x] == '!') {
			return -1;
		} else {

			// mark path
			clone[y][x] = '!';

			// MAP
			/*
			 * for (int f = 0; f < map.length; f++) { for (int a = 0; a < map[0].length; a++) {
			 * System.out.print(clone[f][a]); } System.out.println(); }
			 */

			n = -1;
			e = -1;
			s = -1;
			w = -1;

			if (y - 1 >= 0)
				n = maze(goal, y - 1, x); // North
			if (x + 1 < map[0].length)
				e = maze(goal, y, x + 1); // East
			if (y + 1 < map.length)
				s = maze(goal, y + 1, x); // South
			if (x - 1 >= 0)
				w = maze(goal, y, x - 1); // West

			// The following line prints multiple time due to recursion
			// System.out.println("North: " + n + ", EAST: " + e + ", South: " + s + ", West: " + w);
		}

		// get max value
		int left = Math.max(n, e);
		int right = Math.max(s, w);
		out = Math.max(left, right);

		// get min value
		if (out != -1) {
			if (n > 0)
				out = Math.min(out, n);
			if (e > 0)
				out = Math.min(out, e);
			if (s > 0)
				out = Math.min(out, s);
			if (w > 0)
				out = Math.min(out, w);
		}

		// Make the current location available again
		// Note: it's after recursion
		// if (clone[y][x] == '!')
		clone[y][x] = '.';

		// return!
		if (out != -1) {
			return out + 1;
		} else {
			return -1;
		}
	}
}
