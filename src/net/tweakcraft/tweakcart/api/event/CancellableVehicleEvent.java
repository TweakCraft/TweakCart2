package net.tweakcraft.tweakcart.api.event;

public interface CancellableVehicleEvent {
    public void setCancelled(boolean cancelled);

    public boolean isCancelled();
}
