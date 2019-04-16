package org.springframework.samples.petclinic.toggles;

/**
 * Represents a feature toggle which can enable new features and block old
 */
public class Toggle {

    public static float ALWAYS_ON_WHEN_ENABLED = 1;
    public static float ALWAYS_OFF = 0;

    private boolean enabled;

    /**
     * Determines the ratio of users that should get the new feature
     */
    private float rolloutRatio;

    public Toggle(float ratio) {
        setRolloutRatio(ratio);
        setToggleUsingRolloutRatio();
    }

    public Toggle(boolean enabled, float ratio) {
        this.enabled = enabled;
        setRolloutRatio(ratio);
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
            rolloutRatio = ALWAYS_OFF;
            return;
        }

        if (ratio > 1) {
            rolloutRatio = ALWAYS_ON_WHEN_ENABLED;
            return;
        }

        rolloutRatio = ratio;
    }

    public float getRolloutRatio() {
        return rolloutRatio;
    }

}
