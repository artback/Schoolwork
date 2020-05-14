/**
 * Created by freak on 8/18/17.
 */
public class Frame {
    private int firstThrow;
    private int secondThrow;

    public Frame(){
    }
    public Frame(int firstThrow,int secondThrow){
       this.firstThrow=firstThrow;
       this.secondThrow=secondThrow;
    }
    public void firstThrow(int knockedDown){
        firstThrow=knockedDown;
    }
    public int getFirstThrow(){
       return firstThrow;
    }
    public void secondThrow(int knockedDown){
        secondThrow=knockedDown;
    }
    public int[] getKnockedDown(){
        return new int[]{firstThrow, secondThrow};
    }
    public boolean isStrike(){
        boolean retur = false;
        if(firstThrow == 10){
           retur = true;
        }
        return retur;
    }
    public int getScore() {
       return firstThrow+secondThrow;
    }
}
