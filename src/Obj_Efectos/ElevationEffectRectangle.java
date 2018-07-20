package Obj_Efectos;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.interpolators.SplineInterpolator;
import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * An elevation effect.
 */
public class ElevationEffectRectangle {
    private final SwingTimerTimingSource timer;
    protected final JComponent target;
    private Animator animator;
    protected final SafePropertySetter.Property<Double> level;
    protected int targetLevel = 1;

    private ElevationEffectRectangle(final JComponent component, int level) {
        this.target = component;

        timer = new SwingTimerTimingSource();
        timer.init();

        this.level = SafePropertySetter.animatableProperty(target, (double) level);
        this.targetLevel = level;
    }

    /**
     * Gets the elevation level.
     *
     * @return elevation level (0..5)
     */
    public int getLevel() {
        return targetLevel;
    }

    /**
     * Sets the elevation level.
     *
     * @param level elevation level (0..5)
     */
    public void setLevel(int level) {
        if (level != targetLevel) {
            if (animator != null) {
                animator.stop();
            }
            animator = new Animator.Builder(timer)
                    .setDuration(500, TimeUnit.MILLISECONDS)
                    .setEndBehavior(Animator.EndBehavior.HOLD)
                    .setInterpolator(new SplineInterpolator(0.55, 0, 0.1, 1))
                    .addTarget(SafePropertySetter.getTarget(this.level, this.level.getValue(), (double) level))
                    .build();
            animator.start();
        } else {
            animator = null;
        }
        targetLevel = level;
    }

    /**
     * Paints this effect.
     *
     * @param g canvas
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(target.getParent().getBackground());
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g.drawImage(MaterialShadowRectangle.renderShadow(target.getWidth(), target.getHeight(), level.getValue()), 0, 0, null);
    }

    /**
     * Creates an elevation effect for the given component. You need to call {@link #paint(Graphics)} in your
     * drawing method to actually paint this effect.
     *
     * @param target target component
     * @param level  initial elevation level (0..5)
     * @return elevation effect for that component
     * @see MaterialButton for an example of how the ripple effect is used
     */
    public static ElevationEffectRectangle applyTo(JComponent target, int level) {
        return new ElevationEffectRectangle(target, level);
    }

    /**
     * Creates an elevation effect with a circular shadow for the given component. You need to call
     * {@link #paint(Graphics)} in your drawing method to actually paint this effect.
     *
     * @param target target component
     * @param level  initial elevation level (0..5)
     * @return elevation effect for that component
     * @see MaterialButton for an example of how the ripple effect is used
     */
    public static ElevationEffectRectangle applyCirularTo(JComponent target, int level) {
        return new ElevationEffectRectangle.Circular(target, level);
    }

    /**
     * An elevation effect with a circular shadow.
     */
    public static class Circular extends ElevationEffectRectangle {
        private Circular(JComponent component, int level) {
            super(component, level);
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(MaterialShadowRectangle.renderCircularShadow(target.getWidth(), level.getValue()), 0, 0, null);
        }
    }
}
