import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by freak on 8/18/17.
 */
public class BowlingTest {
    private Frame frame;
    private Game game;
    @Before
    public  void init(){
        frame = new Frame();
         game = new Game();
    }
    @Test
    public void bowlingFrame(){
        int[] knockedDown = {1,2};

        frame.firstThrow(1);
        frame.secondThrow(2);
        assertArrayEquals(knockedDown,frame.getKnockedDown());
    }
    @Test
    public void bowlingScore(){
        int[] knockedDown = {1,2};
        frame.firstThrow(1);
        frame.secondThrow(2);
        assertArrayEquals(frame.getKnockedDown(),knockedDown);
        assertEquals(3,frame.getScore());
    }

    @Test
    public void gameTest(){
        Frame[]  frames = {new Frame(1,5),new Frame(3,6),new Frame(7,2),new Frame(3,6),new Frame(4,4),new Frame(5,3),
                new Frame(3,3),new Frame(4,5),new Frame(8,1),new Frame(2,6)};
        game.setFrames(frames);
        int count=0;
        Frame[] gameFrames = game.getFrames();
        for (Frame frame:gameFrames) {
           if(frame!=null)
               count++;
        }
        assertEquals(10,count);
    }
    @Test
    public void gameScoreTest(){
        Frame[]  frames = {new Frame(1,5),new Frame(3,6),new Frame(7,2),new Frame(3,6),new Frame(4,4),new Frame(5,3),
                new Frame(3,3),new Frame(4,5),new Frame(8,1),new Frame(2,6)};
        game.setFrames(frames);
        assertEquals(81,game.getScore());
    }
    @Test
    public void strikeTest(){
        Frame[]  frames = {new Frame(10,0),new Frame(3,6),new Frame(7,2),new Frame(3,6),new Frame(4,4),new Frame(5,3),
                new Frame(3,3),new Frame(4,5),new Frame(8,1),new Frame(2,6)};
        game.setFrames(frames);

        assertEquals(94,game.getScore());

    }
    @Test
    public void spareTest(){
        Frame[]  frames = {new Frame(1,9),new Frame(3,6),new Frame(7,2),new Frame(3,6),new Frame(4,4),new Frame(5,3),
                new Frame(3,3),new Frame(4,5),new Frame(8,1),new Frame(2,6)};
        game.setFrames(frames);
        assertEquals(88,game.getScore());

    }
    @Test
    public void MultipleStrikeTest(){
        Frame[]  frames = {new Frame(10,0),new Frame(10,0),new Frame(7,2),new Frame(3,6),new Frame(4,4),new Frame(5,3),
                new Frame(3,3),new Frame(4,5),new Frame(8,1),new Frame(2,6)};
        game.setFrames(frames);
        assertEquals(112,game.getScore());

    }
    @Test
    public void StrikeSpareTest(){
        Game game = new Game();
        Frame[]  frames = {new Frame(10,0),new Frame(4,6),new Frame(7,2),new Frame(3,6),new Frame(4,4),new Frame(5,3),
                new Frame(3,3),new Frame(4,5),new Frame(8,1),new Frame(2,6)};
        game.setFrames(frames);
        assertEquals(103,game.getScore());

    }
    @Test
    public void MultipleSpareTest(){
        Frame[]  frames = {new Frame(8,2),new Frame(5,5),new Frame(7,2),new Frame(3,6),new Frame(4,4),new Frame(5,3),
                new Frame(3,3),new Frame(4,5),new Frame(8,1),new Frame(2,6)};
        game.setFrames(frames);
        assertEquals(98,game.getScore());
    }
    @Test
    public void lastSpareTest(){
        Frame[]  frames = {new Frame(1,5),new Frame(3,6),new Frame(7,2),new Frame(3,6),
                new Frame(4,4),new Frame(5,3),new Frame(3,3),new Frame(4,5),new Frame(8,1),new Frame(2,8)};
        game.setFrames(frames);
        game.setBonusthrow(new Frame(7,0));
        assertEquals(90,game.getScore());
    }
    @Test
    public void lastStrikeTest(){
        Frame[]  frames = {new Frame(1,5),new Frame(3,6),new Frame(7,2),new Frame(3,6),
                new Frame(4,4),new Frame(5,3),new Frame(3,3),new Frame(4,5),new Frame(8,1),new Frame(10,0)};
        game.setFrames(frames);
        game.setBonusthrow(new Frame(7,2));
        assertEquals(92,game.getScore());
    }
    @Test
    public void bonusStrikeTest(){
        Frame[]  frames = {new Frame(1,5),new Frame(3,6),new Frame(7,2),new Frame(3,6),
                new Frame(4,4),new Frame(5,3),new Frame(3,3),new Frame(4,5),new Frame(8,1),new Frame(2,8)};
        game.setFrames(frames);
        game.setBonusthrow(new Frame(10,0));
        assertEquals(93,game.getScore());
    }
    @Test
    public void perfectGame(){
        Frame[]  frames = {new Frame(10,0),new Frame(10,0),new Frame(10,0),new Frame(10,0),
                new Frame(10,0),new Frame(10,0),new Frame(10,0),new Frame(10,0),new Frame(10,0),new Frame(10,0)};
        game.setFrames(frames);
        game.setBonusthrow(new Frame(10,10));
        assertEquals(300,game.getScore());

    }
    @Test
    public void realGameTest(){
        Frame[]  frames = {new Frame(6,3),new Frame(7,1),new Frame(8,2),new Frame(7,2),
                new Frame(10,0),new Frame(6,2),new Frame(7,3),new Frame(10,0),new Frame(8,0),new Frame(7,3)};
        game.setFrames(frames);
        game.setBonusthrow(new Frame(10,0));
        assertEquals(135,game.getScore());

    }


}