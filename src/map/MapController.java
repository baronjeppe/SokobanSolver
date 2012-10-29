package map;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import boxSolver.BoxSolver;


public class MapController {
	
	final static int U = 1;
	final static int R = 2;
	final static int D = 3;
	final static int L = 4;
	
	public Map map;
	private String map_path;
	public Viewer viewer;

	public MapController(String mapPath) {
		map_path = mapPath;
		
		map = loader();
		
		viewer = new Viewer(map.map_width, map.map_height);
				
		viewer.drawMap(map.map);
	}
	
	private int[] findSolver(Map map)
	{
		int[] r = new int[2];
		for (int i = 0; i<map.map_width; i++)
			for (int j = 0; j<map.map_height; j++)
				if (map.map[i][j] == 10)
				{
					r[0] = i;
					r[1] = j;
					return r;
				}
		r[0] = -1;
		r[1] = -1;
		return r;
	}
	
	public String solve()
	{
		BoxSolver BS = new BoxSolver();
		
		return BS.solve(map.map, findSolver(map));
	}
	
	public String calcRouteForSokobandit(String sol, char start_orientation, boolean show_route, int wait)
	{
		Map _map = loader();
		int[] solution = new int[sol.length()];
		int last_robot_orientation = -1;
		int[] robot_position = findSolver(_map);
		int[] last_robot_position = new int[2];
		last_robot_position[0] = -1;
		last_robot_position[1] = -1;


		ArrayList<Integer> r = new ArrayList<Integer>();
		
		for (int i = 0; i < sol.length(); i++)
		{
			switch (sol.charAt(i)) {
			case 'u':
			case 'U':
				solution[i] = U;
				break;
			case 'r':
			case 'R':
				solution[i] = R;
				break;
			case 'd':
			case 'D':
				solution[i] = D;
				break;
			case 'l':
			case 'L':
				solution[i] = L;
				break;
			default:
				break;
			}
		}		
		switch (start_orientation) {
		case 'u':
		case 'U':
			last_robot_orientation = U;
			break;
		case 'r':
		case 'R':
			last_robot_orientation = R;
			break;
		case 'd':
		case 'D':
			last_robot_orientation = D;
			break;
		case 'l':
		case 'L':
			last_robot_orientation = L;
			break;
		default:
			break;
		}
		
		for (int i = 0; i < solution.length; i++)
		{
			
			int next_move = last_robot_orientation - solution[i];
			if(_map.map[robot_position[0]][robot_position[1]] == 1000)
			{
				_map.map[robot_position[0]][robot_position[1]] = 2;
				switch (last_robot_orientation) {
				case U:
					_map.map[robot_position[0]][robot_position[1] - 1] = 1000;
					break;
				case R:
					_map.map[robot_position[0] + 1][robot_position[1]] = 1000;
					break;
				case D:
					_map.map[robot_position[0]][robot_position[1] + 1] = 1000;
					break;
				case L:
					_map.map[robot_position[0] - 1][robot_position[1]] = 1000;
					break;

				default:
					break;
				}
				if(next_move != 0)
				{
					r.add(0);
					r.add(2);
					switch (next_move) {
					case 1:
					case -3: // left turn
						r.add(3);
						break;
					case -1:
					case 3: // right turn
						r.add(-3);
						break;
					case 2:
					case -2: // Turn around
						r.add(0);				
						break;

					default:
						break;
					}
				}
				else
				{
					r.add(next_move);
				}

			}
			else
			{
				r.add(next_move);
			}

			if (last_robot_position[0] != -1)
				if (map.map[last_robot_position[0]][last_robot_position[1]] == 100)
					_map.map[last_robot_position[0]][last_robot_position[1]] = 100;
				else
					_map.map[last_robot_position[0]][last_robot_position[1]] = 2;
			last_robot_position[0] = robot_position[0];
			last_robot_position[1] = robot_position[1];
			_map.map[robot_position[0]][robot_position[1]] = 10;
			switch (solution[i]) {
			case U:
				robot_position[1] -= 1;
				break;
			case R:
				robot_position[0] += 1;
				break;
			case D:
				robot_position[1] += 1;
				break;
			case L:
				robot_position[0] -= 1;
				break;

			default:
				break;
			}

			last_robot_orientation = solution[i];
			
			if (show_route)
			{
				viewer.updateMap(_map.map);
				try {
					Thread.sleep(wait);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		
		if(_map.map[robot_position[0]][robot_position[1]] == 1000)
		{
			_map.map[robot_position[0]][robot_position[1]] = 2;
			switch (last_robot_orientation) {
			case U:
				_map.map[robot_position[0]][robot_position[1] - 1] = 1000;
				break;
			case R:
				_map.map[robot_position[0] + 1][robot_position[1]] = 1000;
				break;
			case D:
				_map.map[robot_position[0]][robot_position[1] + 1] = 1000;
				break;
			case L:
				_map.map[robot_position[0] - 1][robot_position[1]] = 1000;
				break;

			default:
				break;
			}
			r.add(0);
			r.add(2);
		}
		
		if (last_robot_position[0] != -1)
			if (map.map[last_robot_position[0]][last_robot_position[1]] == 100)
				_map.map[last_robot_position[0]][last_robot_position[1]] = 100;
			else
				_map.map[last_robot_position[0]][last_robot_position[1]] = 2;


		last_robot_position[0] = robot_position[0];
		last_robot_position[1] = robot_position[1];
		_map.map[robot_position[0]][robot_position[1]] = 10;
		
		if (show_route)
		{
			viewer.updateMap(_map.map);
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < r.size(); i++)
		{
			switch (r.get(i)) {
			case 0: // forward
				ret.append('f');
				break;
			case 1:
			case -3: // left turn
				ret.append('l');
				break;
			case -1:
			case 3: // right turn
				ret.append('r');
				break;
			case 2:
			case -2: // Turn around
				ret.append('b');				
				break;

			default:
				break;
			}
		}		
		map.map = _map.map;
		return ret.toString();
	}
	
	private int[][] emptyMap(int[][] mapIn){
		for(int i = 0 ; i < mapIn.length; i++)
			for(int j = 0; j < mapIn[0].length; j++)
				mapIn[i][j] = 0;
		return mapIn;
	}

	private Map loader(){
		
		Map r = new Map();
		int movers = 10;
		int boxes = 1000;
		int goals = 100;
		
		try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream(map_path);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  strLine = br.readLine();
			  
			  // Do something to the map specs
			  String delimiter = " ";
			  String[] temp = strLine.split(delimiter);
			 
			  if (temp.length == 3)
			  {
				  r.map_width = Integer.parseInt(temp[0]);
				  r.map_height = Integer.parseInt(temp[1]);
				  r.no_of_goals = Integer.parseInt(temp[2]);				  
			  }
			  else
				  System.out.println("ERROR: Map specs has wrong format");


			  
			  r.map = new int[r.map_width][r.map_height];
			  r.map = emptyMap(r.map);
			  
			  int j = 0;
			  //Read File Line By Line
			  
			  while ((strLine = br.readLine()) != null)   {
				  // Print the content on the console
				 // System.out.println (strLine);
				  for( int i = 0 ; i < strLine.length()  ; i++){
					  switch (strLine.charAt(i)) {
					case 'X':
					case 'x':
						r.map[i][j] = 1;
						break;
						  
					case '.':
						r.map[i][j] = 2;
						break;
						
					case 'M':
					case 'm':
						r.map[i][j] = movers;
						break;
						
					case 'G':
					case 'g':
						r.map[i][j] = goals;
						break;
						
					case 'J':
					case 'j':
						r.map[i][j] = boxes;
						break;
						
					default:
						break;
					}
				  }
				  j++;
			  }
			  //Close the input stream
			  in.close();
			    }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		r.no_of_boxes = boxes - 1000;
		r.no_of_goals = goals - 100;
		r.no_of_movers = movers - 10;
		return r;
	}
}
