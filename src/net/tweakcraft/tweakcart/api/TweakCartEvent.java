package net.tweakcraft.tweakcart.api;

public class TweakCartEvent {
    public enum Block {
        VehicleBlockChangeEvent,
        VehicleBlockCollisionEvent,
        VehicleBlockDetectEvent,
        VehicleDispenseEvent
    }

    public enum Sign {
        VehiclePassesSignEvent,
        VehicleCollidesWithSignEvent
    }
}
