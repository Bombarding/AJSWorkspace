public class Game {
    private Board board;
    boolean finish = false;
    boolean win = false;
    int turn=0;
    
    public void Jogo(){
        board = new Board();
        Play(board);
    }
    
    public void Play(Board board){
        do{
            turn++;
            System.out.println("Turn "+turn);
            board.show();
            finish = board.setPosition();
            
            if(!finish){
                board.openNeighbors();
                finish = board.win();
            }
            
        }while(!finish);
        
        if(board.win()){
            System.out.println("Congratulations, you let the 10 fields with the mines in "+turn+" turns");
            board.showMines();
        } else {
            System.out.println("Mine! You lost!");
            board.showMines();
        }
    }
}
