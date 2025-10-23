package com.example.thesis3.model;

public class FabConfig {
    private int iconResId;
    private int gravity;
    private int marginDp;
    private boolean visible;

    public FabConfig(int iconResId, int gravity, int marginDp) {
        this.iconResId = iconResId;
        this.gravity = gravity;
        this.marginDp = marginDp;
        this.visible = true;
    }

    public int getIconResId() {
        return iconResId;
    }

    public int getGravity() {
        return gravity;
    }

    public int getMarginDp() {
        return marginDp;
    }

    public boolean isVisible() {
        return visible;
    }
}
