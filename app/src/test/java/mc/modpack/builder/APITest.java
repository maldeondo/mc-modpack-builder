package mc.modpack.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

import mc.modpack.builder.misc.APIKey;
import mc.modpack.builder.network.NetworkManager;

public class APITest {
    private String getKey() {
        try {
            return APIKey.fetchKey();
        }
        catch(Exception ex) {
            System.out.println("ERROR getting the key");
            return "";
        }
    }

    @Test void modNameFromAPI() {
        //Getting the API key, and setting everything up
        String key = getKey();
        NetworkManager manager = new NetworkManager(key);

        //Checking if the API key was fetched. If not, the tests won't work
        if(key.isEmpty()) {
            fail("The API key isn't defined");
        }
        else {
            //Trying with mods that exist via ID
            try {
                //Checking on various mods we now the name by their ID
                assertEquals("Just Enough Items (JEI)", manager.getModName("238222"), "Should retrieve 'Just Enough Items' with its mod ID (238222)");
                assertEquals("Cloth Config API (Fabric/Forge/NeoForge)", manager.getModName("348521"), "Should retrieve 'Cloth Config API' via its mod ID (348521)");
                assertEquals("Bookshelf", manager.getModName("228525"), "Should retrieve 'Bookshelf' based on its API ID (228525)");
                
            }
            catch(IOException | InterruptedException ex) {
                fail("An unhandled exception occured while checking for mods that are avaiable via their IDs");
            }

            //Trying with mods that exist via JSON object
            try {
                //Checking for Just Enough Items
                JsonObject gecko = manager.getRawInfo("388172");
                assertEquals("GeckoLib", manager.getModName(gecko), "Should retrieve 'GeckoLib' via its JSON info");

                //Checking for Sodium
                JsonObject sodium = manager.getRawInfo("394468");
                assertEquals("Sodium", manager.getModName(sodium), "Should retrieve 'Sodium' via its JSON info");
                
                //Checking for sophisticated backpacks
                JsonObject backp = manager.getRawInfo("422301");
                assertEquals("Sophisticated Backpacks", manager.getModName(backp), "Should retrieve 'Sophisticated Backpacks' based on its JSON info");
                
            }
            catch(IOException | InterruptedException ex) {
                fail("An unhandled exception occured while checking for the mod's name via their JSON info previously returned by the API");
            }

            //Trying with mods that don't exist
            try {
                //Checking for Just Enough Items
                JsonObject dont = manager.getRawInfo("000000");
                assertEquals("", manager.getModName(dont), "Testing plausible ID");

                //Checking for Sodium
                dont = manager.getRawInfo("lol");
                assertEquals("", manager.getModName(dont), "Testing non-numeric ID");
                
                //Checking for sophisticated backpacks
                dont = manager.getRawInfo("888888888888");
                assertEquals("", manager.getModName(dont), "Testing really long ID");
                
                dont = manager.getRawInfo("");
                assertEquals("", manager.getModName(dont), "Testing null ID");
            }
            catch(IOException | InterruptedException ex) {
                fail("An unhandled exception occured while checking for the name of mods that don't exist");
            }
        }
    }
}
