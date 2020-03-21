import javafx.scene.control.Button;
public class Node{
    public Button b;
    private int x;
    private int y;
    public boolean isStart;
    public boolean isEnd;
    public boolean isObstacle;
    public int f_score;
    public int g_score;
    public int h_score;
    public Node parent;
    
    public Node(Button btn, int x, int y){
        b = btn;
        this.x = x;
        this.y = y;
        isStart = false;
        isEnd = false;
        isObstacle = false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setStart(){
        if(!isEnd && !isObstacle){
            isStart = true;
        }
    }

    public void setEnd(){
        if(!isStart && !isObstacle){
            isEnd = true;
        }
    }

    public void setObstacle(){
        if(!isStart && !isEnd){
            isObstacle = true;
        }
    }

    public void updateF(){
        f_score = g_score + h_score;
    }

}