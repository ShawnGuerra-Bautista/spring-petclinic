package org.springframework.samples.petclinic.toggles;

/**
 * Represents a feature toggle which can enable new features and block old
 */
public class Toggle {

    private boolean enabled;

    /**
     * Determines the ratio of users that should get the new feature
     */
    private float rolloutRatio;

    public Toggle() {
        this(0);
    }

    public Toggle(float ratio) {
        setRolloutRatio(ratio);
        setToggleUsingRolloutRatio();
    }

    public boolean isOn() {
        return enabled;
    }

    public void turnOn() {
        enabled = true;
    }

    public void turnOff() {
        enabled = false;
    }

    public void setToggleUsingRolloutRatio() {
        enabled = rolloutRatio > Math.random();
    }

    public void setRolloutRatio(float ratio) {
        if (ratio <= 0) {
            rolloutRatio = 0;
            return;
        }

        if (ratio > 1) {
            rolloutRatio = 1;
            return;
        }

        rolloutRatio = ratio;
    }

    public float getRolloutRatio() {
        return rolloutRatio;
    }

}
