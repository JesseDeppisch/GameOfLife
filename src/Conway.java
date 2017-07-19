import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Conway extends JPanel implements ActionListener {

	JFrame f;
	Timer timer;

	int time = 0; // Current time, in seconds, in the simulation
	int delay = 75; // Delay in MS

	final int N_ITER = 1000;
	final int N = 50;
	final int M = 50;
	int grid[][] = new int[N][M];

	public static void main(String[] args) {
		JFrame frame = new JFrame("Conway's Game of Life");
		frame.add(new Conway());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public Conway() {
		timer = new Timer(delay, this); // First parameter is delay
		timer.start();

		// Fill the grid with random numbers
//		populateGrid();
		
		gliderGun();
	}

	public void actionPerformed(ActionEvent e) {
		if (time < N_ITER) { // Stops the simulation from going past day length
			// Tick
			tickGrid();
			// Repaint graphics
			repaint();
			
			// Update time
			time++;
		}
	}

	public void paint(Graphics g) {
		// Clear old stuff
		g.clearRect(0, 0, getWidth(), getHeight());

		// Paint the thing
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				// Make the color green if cell is alive
				if (grid[i][j] == 1) {
					g.setColor(Color.GREEN);
				} else {
					g.setColor(Color.WHITE);
				}

				// Paint the cell
				g.fillRect(i * 10 + 10, j * 10 + 20, 10, 10);
			}
		}

		// Paint grid lines
//		g.setColor(Color.BLACK);
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < M; j++) {
//				// Vertical line
//				g.drawLine(i*10+10, 20, i*10+10, 10*N+20);
//				
//				// Horizontal line
//				g.drawLine(10, j*10+20, M*10+10, j*10+20);
//			}
//		}

		// Display the tick information
		g.setColor(Color.BLACK);
		g.drawString("Tick: " + time, 1, 10); // TODO - display
	}

	/*
	 * Applies a tick of Conway's game of life
	 */
	private void tickGrid() {
		/* Copy the grid to read from */
		int temp[][] = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				temp[i][j] = grid[i][j];
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				/* Count living neighbors */
				int neigh = 0;

				// Iterate through neighbors
				for (int x = -1; x < 2; x++) {
					for (int y = -1; y < 2; y++) {
						// If checking initial cell or past border, skip
						if (i + x < 0 || j + y < 0 || i + x >= N || j + y >= M || (x == 0 && y == 0)) {
							continue;
						} else if (temp[i + x][j + y] == 1) {
							neigh++;
						}
					}
				}

				/* Apply rules */
				int state = temp[i][j];

				if (state == 1 && neigh < 2) {
					grid[i][j] = 0;
				} else if (state == 1 && (neigh == 2 || neigh == 3)) {
					continue; // Lives on
				} else if (state == 1 && neigh > 3) {
					grid[i][j] = 0;
				} else if (state == 0 && neigh == 3) {
					grid[i][j] = 1;
				}
			}
		}
	}

	/*
	 * Populates the grid randomly with either 0 or 1
	 */
	private void populateGrid() {
		Random rand = new Random();
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				grid[i][j] = rand.nextInt(2);
			}
		}
	}
	
	/*
	 * Sets the initial array to be a glider gun
	 */
	private void gliderGun() {
		// Left block
		grid[1][5] = 1;
		grid[1][6] = 1;
		grid[2][5] = 1;
		grid[2][6] = 1;

		// Left section
		grid[11][5] = 1;
		grid[11][6] = 1;
		grid[11][7] = 1;
		grid[12][8] = 1;
		grid[13][9] = 1;
		grid[14][9] = 1;
		grid[12][4] = 1;
		grid[13][3] = 1;
		grid[14][3] = 1;
		grid[15][6] = 1;
		grid[16][4] = 1;
		grid[16][8] = 1;
		grid[17][5] = 1;
		grid[17][6] = 1;
		grid[17][7] = 1;
		grid[18][6] = 1;
		
		// Right section
		grid[21][5] = 1;
		grid[21][4] = 1;
		grid[21][3] = 1;
		grid[22][5] = 1;
		grid[22][4] = 1;
		grid[22][3] = 1;
		grid[23][2] = 1;
		grid[23][6] = 1;
		grid[25][6] = 1;
		grid[25][7] = 1;
		grid[25][2] = 1;
		grid[25][1] = 1;
		
		// Right block
		grid[35][3] = 1;
		grid[35][4] = 1;
		grid[36][3] = 1;
		grid[36][4] = 1;

	}

}
