package com.mygame.handlers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Used to store animation that contain set of frames, information about delay between them,
 * which one is current frame, times played, is animation looped or if it has ended
 */
public class Animation {
    private TextureRegion[] frames;
    private float           time;
    private float           delay;
    private int             currentFrame;
    private int             timesPlayed;
    private boolean         loop;
    private boolean         ended;

    /**
     * @param frames array of frames
     * @param delay delay between frames
     */
    public Animation(TextureRegion[] frames, float delay) {
        this.frames  = frames;
        this.delay   = delay;
        time         = 0;
        currentFrame = 0;
        timesPlayed  = 0;
        loop = true;
        ended = false;
    }

    public void update(float dt) {
        if(delay <= 0) return;

        time += dt;
        while(time >= delay) step();
    }

    private void step() {
        time -= delay;
        ++currentFrame;
        if(currentFrame == frames.length) {
            if(loop) {
                currentFrame = 0;
                ++timesPlayed;
            }
            else {
                currentFrame = frames.length - 1;
                ended = true;
            }
        }
    }

    public TextureRegion getFrame() {
        return frames[currentFrame];
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public float getTime() {
        return time;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void synchronize(Animation anim) {
        this.time = anim.getTime();
        this.currentFrame = anim.getCurrentFrame();
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public boolean isLoop() {
        return loop;
    }

    /**
     * @param x flip on x axis
     * @param y flip on y axis
     */
    public void flip(boolean x, boolean y) {
        for(TextureRegion i : frames) {
            i.flip(x, y);
        }
    }

    public boolean hasEnded() {
        if(ended) {
            ended = false;
            timesPlayed  += 1;
            time         = 0;
            currentFrame = 0;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Change order of frames to opposite
     */
    public void reverse() {
        TextureRegion[] tmp = new TextureRegion[frames.length];

        for(int i = 0; i < frames.length; ++i) {
            tmp[i] = frames[i];
        }

        int j = frames.length - 1;
        for(int i = 0; i < frames.length; ++i) {
            frames[i] = tmp[j--];
        }
    }

    public void reset() {
        time         = 0;
        currentFrame = 0;
        ended = false;
    }
}
