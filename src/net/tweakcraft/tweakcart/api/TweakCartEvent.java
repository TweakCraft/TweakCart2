package net.tweakcraft.tweakcart.api;

public class TweakCartEvent {
    public enum Block {
        VehicleBlockChangeEvent,
        VehicleBlockCollisionEvent,
        VehicleBlockDetectEvent,
        VehicleDispenseEvent,
        VehicleCollectEvent
    }

    public enum Sign {
        VehiclePassesSignEvent,
        VehicleCollidesWithSignEvent
    }
    
    public enum Collect {
        VehicleSlabInDispenserEvent;
    }
}
