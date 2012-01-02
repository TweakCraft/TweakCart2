package net.tweakcraft.tweakcart.api.event;

public interface CancelableVehicleEvent {
    public void setCanceled(boolean canceled);

    public boolean isCanceled();
}
