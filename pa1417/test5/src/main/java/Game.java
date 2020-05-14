/**
 * Created by freak on 8/18/17.
 */
public class Game {
    private Frame[] frames;
    private Frame bonus;
    public Game(){
        frames = new Frame[12];
    }
    public Frame[] getFrames(){
        return frames;
    }
    public void setFrames(Frame[] frames){
        for (int i = 0; i < frames.length; i++) {
           this.frames[i] = frames[i];
        }
    }
    public int getScore() {
        int score=0, i=0;
        while(frames[i] != null) {
            if (frames[i].isStrike()) {
                    if(i < 9)
                    score += getStrikeScore(i);
            } else if (frames[i].getScore() == 10 && i < 9) {
                score +=  frames[i+1].getFirstThrow();
            }
            score += frames[i].getScore();
            i++;
        }
        return score;
    }
    private int getStrikeScore(int pos){
        int score = 0;
        if (frames[pos+1] != null) {
            score += frames[pos + 1].getScore();
            if (frames[pos + 1].isStrike())
                score += frames[pos + 2].getFirstThrow();
        }
        return score;
    }

    public void setBonusthrow(Frame frame) {
        if (frames[9].getScore() == 10 )
            frames[10] = frame;
    }
}
