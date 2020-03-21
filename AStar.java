import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import javafx.scene.control.Button;
import java.util.Comparator;

public class AStar implements Runnable{

    class Compare implements Comparator<Node>{
        public int compare(Node a, Node b){
            return a.f_score - b.f_score;
        }
    }

    private Node[][] grid;
    private ArrayList<Button> buttons;
    private HashMap<Button, Node> map;
    private Node start;
    private Node end;

    public AStar(Node[][] g, ArrayList<Button> list, HashMap<Button, Node> m, Node s, Node e){
        grid = g;
        buttons = list;
        map = m;
        start = s;
        end = e;
    }

    public void run(){
        algorithm();
    }

    public void algorithm(){
        Compare c = new Compare();
        PriorityQueue<Node> open = new PriorityQueue<Node>(c);
        open.add(start);
        PriorityQueue<Node> closed = new PriorityQueue<Node>(c);
        while(open.peek() != end){
            Node current = open.poll();
            closed.add(current);
            ArrayList<Node> neighbors = neighbors(current);
            for(Node neighbor : neighbors){
                //if(neighbor != start && neighbor != end)
                    //neighbor.b.setStyle("-fx-background-color: #FFFF00");
                int cost = euclid(current, start) + euclid(current, neighbor);
                if(open.contains(neighbor) && cost < euclid(start, neighbor)){
                    open.remove(neighbor);
                }
                if(closed.contains(neighbor) && cost < euclid(start, neighbor)){
                    closed.remove(neighbor);
                }
                if(!closed.contains(neighbor) && !open.contains(neighbor)){
                    neighbor.g_score = cost;
                    neighbor.f_score = neighbor.g_score + euclid(neighbor, end);
                    open.add(neighbor);
                    neighbor.parent = current;
                    if(neighbor != start && neighbor != end)
                        neighbor.b.setStyle("-fx-background-color: #FFFF00");
                }
                try{
                    Thread.sleep(50);
                } catch(Exception e){

                }
            }
        }
        Node curr = end.parent;
        while(curr != start){
            curr.b.setStyle("-fx-background-color: #0000ff");
            curr = curr.parent;
            try{
                Thread.sleep(50);
            } catch(Exception e){

            }
        }
    }

    public ArrayList<Node> neighbors(Node n){
        ArrayList<Node> neighbors = new ArrayList<Node>();
        int x = n.getX();
        int y = n.getY();
        if(inside(x + 1, y) && !grid[x + 1][y].isObstacle)
            neighbors.add(grid[x + 1][y]);
        if(inside(x - 1, y) && !grid[x-1][y].isObstacle)
            neighbors.add(grid[x - 1][y]);
        if(inside(x, y + 1) && !grid[x][y+1].isObstacle)
            neighbors.add(grid[x][y + 1]);
        if(inside(x, y - 1) && !grid[x][y - 1].isObstacle)
            neighbors.add(grid[x][y - 1]);
        if(inside(x - 1, y - 1) && !grid[x-1][y-1].isObstacle)
            neighbors.add(grid[x - 1][y - 1]);
        if(inside(x - 1, y + 1) && !grid[x-1][y+1].isObstacle)
            neighbors.add(grid[x - 1][y + 1]);
        if(inside(x + 1, y + 1) && !grid[x+1][y+1].isObstacle)
            neighbors.add(grid[x + 1][y + 1]);
        if(inside(x + 1, y - 1) && !grid[x+1][y-1].isObstacle)
            neighbors.add(grid[x + 1][y - 1]);
        return neighbors;
    }

    public boolean inside(int x, int y){
        if(x >= 0 && x <= 19 && y >= 0 && y <= 19)
            return true;
        return false;
    }

    public int manhattan(Node t, Node other){
        return (Math.abs(t.getX() - other.getX()) + Math.abs(t.getY() - other.getY()));
    }

    public int euclid(Node t, Node other){
        return (int)Math.sqrt(Math.pow(t.getX() - other.getX(), 2) + Math.pow(t.getY() - other.getY(), 2));
    }
}