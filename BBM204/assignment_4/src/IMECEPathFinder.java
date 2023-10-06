import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class IMECEPathFinder{
	  public int[][] grid;
	  public int height, width;
	  public int maxFlyingHeight;
	  public double fuelCostPerUnit, climbingCostPerUnit;

	  public IMECEPathFinder(String filename, int rows, int cols, int maxFlyingHeight, double fuelCostPerUnit, double climbingCostPerUnit){

		  grid = new int[rows][cols];
		  this.height = rows;
		  this.width = cols;
		  this.maxFlyingHeight = maxFlyingHeight;
		  this.fuelCostPerUnit = fuelCostPerUnit;
		  this.climbingCostPerUnit = climbingCostPerUnit;

			// TODO: fill the grid variable using data from filename

		  try {
			  // TODO COMMENT BELOW LINE
			  // filename = "sample_IO\\sample_1\\" + filename;
			  File file = new File(filename);
			  Scanner scanner = new Scanner(file);

			  int colCounter = 0;
			  while(scanner.hasNextLine()) {
				  String[] line = scanner.nextLine().trim().split("[ \\t]+");
				  for(int i = 0; i < width; ++i)
					  grid[colCounter][i] = Integer.parseInt(line[i]);
				  ++colCounter;
			  }

		  } catch (FileNotFoundException e) {
			  e.printStackTrace();
		  }

	  }


	  /**
	   * Draws the grid using the given Graphics object.
	   * Colors should be grayscale values 0-255, scaled based on min/max elevation values in the grid
	   */
	  public void drawGrayscaleMap(Graphics g){

		  int min = Integer.MAX_VALUE;
		  int max = Integer.MIN_VALUE;
		  for (int i = 0; i < height; ++i)
			  for (int j = 0; j < width; ++j) {
				  if (grid[i][j] < min)
					  min = grid[i][j];
				  if (grid[i][j] > max)
					  max = grid[i][j];
			  }

		  double scale = 255.0 / (max - min);

		  // TODO: draw the grid, delete the sample drawing with random color values given below
		  for (int i = 0; i < grid.length; i++)
		  {
			  for (int j = 0; j < grid[0].length; j++) {
				  int value = (int) (scale * (grid[i][j] - min));
				  g.setColor(new Color(value, value, value));
				  g.fillRect(j, i, 1, 1);
			  }
		  }
	  }

	/**
	 * Get the most cost-efficient path from the source Point start to the destination Point end
	 * using Dijkstra's algorithm on pixels.
	 * @return the List of Points on the most cost-efficient path from start to end
	 */
	public List<Point> getMostEfficientPath(Point start, Point end) {

		List<Point> path = new ArrayList<>();

		// TODO: Your code goes here
		// TODO: Implement the Mission 0 algorithm here

		Point[][] pred = new Point[height][width];
		boolean[][] visited = new boolean[height][width];
		double[][] costs = new double[height][width];
		for (int i = 0; i < height; ++i)
			for (int j = 0; j < width; ++j)
				costs[i][j] = Double.POSITIVE_INFINITY;
		costs[start.y][start.x] = 0;
		start.cost = 0;

		Point cur = null;
		Queue<Point> queue = new PriorityQueue<>();
		queue.add(start);

		while (!queue.isEmpty()) {
			cur = queue.poll();
			if (cur.equals(end))
				break;
			if (visited[cur.y][cur.x])
				continue;

			for (Point n : cur.getNeighbours(width, height)) {
				if(grid[n.y][n.x] > maxFlyingHeight)
					continue;

				int d_height = grid[n.y][n.x];
				int s_height = grid[cur.y][cur.x];

				double cost = getCost(cur, n, d_height - s_height) + costs[cur.y][cur.x];
				if (cost < costs[n.y][n.x]) {
					queue.remove(n);
					n.cost = cost;
					costs[n.y][n.x] = cost;
					pred[n.y][n.x] = cur;
					queue.add(n);
				}
			}
			visited[cur.y][cur.x] = true;
		}

		if (!end.equals(cur))
			return path;

		do {
			path.add(cur);
			cur = pred[cur.y][cur.x];
		} while (!cur.equals(start));
		path.add(start);

		Collections.reverse(path);
		return path;
	}

	private double getCost(Point source, Point end, int deltaH) {
		double dist = Math.pow(Math.pow(source.y - end.y, 2) + Math.pow(source.x - end.x, 2), 0.5);
		int heightImpact = Math.max(0, deltaH);

		return dist * fuelCostPerUnit + climbingCostPerUnit * heightImpact;
	}


	/**
	 * Calculate the most cost-efficient path from source to destination.
	 * @return the total cost of this most cost-efficient path when traveling from source to destination
	 */
	public double getMostEfficientPathCost(List<Point> path){
		double totalCost = 0.0;

		// TODO: Your code goes here, use the output from the getMostEfficientPath() method

		totalCost = path.get(path.size() - 1).cost;

		return totalCost;
	}


	/**
	 * Draw the most cost-efficient path on top of the grayscale map from source to destination.
	 */
	public void drawMostEfficientPath(Graphics g, List<Point> path){
		// TODO: Your code goes here, use the output from the getMostEfficientPath() method

		for(Point p : path) {
			g.setColor(new Color(0, 255, 0));
			g.fillRect(p.x, p.y, 1, 1);
		}
	}

	/**
	 * Find an escape path from source towards East such that it has the lowest elevation change.
	 * Choose a forward step out of 3 possible forward locations, using greedy method described in the assignment instructions.
	 * @return the list of Points on the path
	 */
	public List<Point> getLowestElevationEscapePath(Point start){
		List<Point> pathPointsList = new ArrayList<>();

		// TODO: Your code goes here
		// TODO: Implement the Mission 1 greedy approach here

		start.cost = 0;
		Point cur = start;
		while (cur.x != width - 1) {
			pathPointsList.add(cur);

			int curHeight = grid[cur.y][cur.x];
			int minHeightDif = Integer.MAX_VALUE;
			Point next = cur;
			for (Point p : cur.getENeighbours(width, height)) {
				if (grid[p.y][p.x] > maxFlyingHeight)
					continue;

				int heightDif = Math.abs(curHeight - grid[p.y][p.x]);
				if (heightDif < minHeightDif) {
					minHeightDif = heightDif;
					next = p;
				}
			}
			next.cost = cur.cost + minHeightDif;
			cur = next;
		}
		pathPointsList.add(cur);

		return pathPointsList;
	}

	/**
	 * Calculate the escape path from source towards East such that it has the lowest elevation change.
	 * @return the total change in elevation for the entire path
	 */
	public int getLowestElevationEscapePathCost(List<Point> pathPointsList){
		int totalChange = 0;

		// TODO: Your code goes here, use the output from the getLowestElevationEscapePath() method

		totalChange = (int) pathPointsList.get(pathPointsList.size() - 1).cost;

		return totalChange;
	}


	/**
	 * Draw the escape path from source towards East on top of the grayscale map such that it has the lowest elevation change.
	 */
	public void drawLowestElevationEscapePath(Graphics g, List<Point> pathPointsList){
		// TODO: Your code goes here, use the output from the getLowestElevationEscapePath() method

		for(Point p : pathPointsList) {
			g.setColor(new Color(255, 255, 0));
			g.fillRect(p.x, p.y, 1, 1);
		}
	}

}
