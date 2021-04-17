package de.hglabor.listener;

import de.hglabor.utils.Renderer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;

public class MapInitializeListener implements Listener {

    @EventHandler
    public void onMapInitialize(MapInitializeEvent event) {
        MapView mapView = event.getMap();
        mapView.setScale(MapView.Scale.FARTHEST);
        mapView.setUnlimitedTracking(true);
        mapView.getRenderers().clear();
        mapView.addRenderer(new Renderer());
        mapView.setLocked(true);
        //Bukkit.getOnlinePlayers().forEach(all -> {all.sendMap(mapView);});
    }

}
