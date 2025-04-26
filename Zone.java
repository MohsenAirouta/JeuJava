//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AdventureGameJava.src.com.adventure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Zone implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private Map<String, Exit> exits;
    private List<Item> items;
    private List<Container> containers;
    private List<NPC> npcs;
    private List<Puzzle> puzzles;

    public Zone(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap();
        this.items = new ArrayList();
        this.containers = new ArrayList();
        this.npcs = new ArrayList();
        this.puzzles = new ArrayList();
    }

    public void setExit(String direction, Zone neighbor) {
        this.exits.put(direction, new Exit(neighbor));
    }

    public void setSpecialExit(String direction, Zone neighbor, boolean locked, String keyName, boolean visible) {
        this.exits.put(direction, new Exit(neighbor, locked, keyName, visible));
    }

    public void setOneWayExit(String direction, Zone neighbor) {
        this.exits.put(direction, new Exit(neighbor, false, (String)null, true, true));
    }

    public Exit getExit(String direction) {
        return (Exit)this.exits.get(direction);
    }

    public boolean unlockExit(String direction, Item key) {
        Exit exit = (Exit)this.exits.get(direction);
        return exit != null ? exit.unlock(key) : false;
    }

    public void revealExit(String direction) {
        Exit exit = (Exit)this.exits.get(direction);
        if (exit != null) {
            exit.setVisible(true);
        }

    }

    public String getExitString() {
        StringBuilder exitString = new StringBuilder("Sorties: ");
        boolean hasVisibleExits = false;

        for(Map.Entry<String, Exit> entry : this.exits.entrySet()) {
            if (((Exit)entry.getValue()).isVisible()) {
                exitString.append(this.getDirectionName((String)entry.getKey())).append(" ");
                hasVisibleExits = true;
            }
        }

        if (!hasVisibleExits) {
            return exitString.append("aucune").toString();
        } else {
            return exitString.toString();
        }
    }

    private String getDirectionName(String direction) {
        switch (direction) {
            case "B":
                return "bas";
            case "E":
                return "est";
            case "H":
                return "haut";
            case "N":
                return "nord";
            case "O":
                return "ouest";
            case "S":
                return "sud";
            case "UP":
                return "haut";
            case "DOWN":
                return "bas";
        }

        return direction;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public Item removeItem(String itemName) {
        for(int i = 0; i < this.items.size(); ++i) {
            if (((Item)this.items.get(i)).getName().equalsIgnoreCase(itemName)) {
                return (Item)this.items.remove(i);
            }
        }

        return null;
    }

    public void addContainer(Container container) {
        this.containers.add(container);
    }

    public Container getContainer(String containerName) {
        for(Container container : this.containers) {
            if (container.getName().equalsIgnoreCase(containerName)) {
                return container;
            }
        }

        return null;
    }

    public void addNPC(NPC npc) {
        this.npcs.add(npc);
    }

    public NPC getNPC(String npcName) {
        for(NPC npc : this.npcs) {
            if (npc.getName().equalsIgnoreCase(npcName)) {
                return npc;
            }
        }

        return null;
    }

    public void addPuzzle(Puzzle puzzle) {
        this.puzzles.add(puzzle);
    }

    public Puzzle getPuzzle(String puzzleName) {
        for(Puzzle puzzle : this.puzzles) {
            if (puzzle.getName().equalsIgnoreCase(puzzleName)) {
                return puzzle;
            }
        }

        return null;
    }

    public String getItemsString() {
        if (this.items.isEmpty()) {
            return "";
        } else {
            StringBuilder itemString = new StringBuilder();

            for(int i = 0; i < this.items.size(); ++i) {
                itemString.append(((Item)this.items.get(i)).getName());
                if (i < this.items.size() - 1) {
                    itemString.append(", ");
                }
            }

            return itemString.toString();
        }
    }

    public String getContainersString() {
        if (this.containers.isEmpty()) {
            return "";
        } else {
            StringBuilder containerString = new StringBuilder();

            for(int i = 0; i < this.containers.size(); ++i) {
                containerString.append(((Container)this.containers.get(i)).getName());
                if (i < this.containers.size() - 1) {
                    containerString.append(", ");
                }
            }

            return containerString.toString();
        }
    }

    public String getNPCsString() {
        if (this.npcs.isEmpty()) {
            return "";
        } else {
            StringBuilder npcString = new StringBuilder();

            for(int i = 0; i < this.npcs.size(); ++i) {
                npcString.append(((NPC)this.npcs.get(i)).getName());
                if (i < this.npcs.size() - 1) {
                    npcString.append(", ");
                }
            }

            return npcString.toString();
        }
    }

    public String getPuzzlesString() {
        if (this.puzzles.isEmpty()) {
            return "";
        } else {
            StringBuilder puzzleString = new StringBuilder();

            for(int i = 0; i < this.puzzles.size(); ++i) {
                puzzleString.append(((Puzzle)this.puzzles.get(i)).getName());
                if (i < this.puzzles.size() - 1) {
                    puzzleString.append(", ");
                }
            }

            return puzzleString.toString();
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Item> getItems() {
        return new ArrayList(this.items);
    }

    public List<Container> getContainers() {
        return new ArrayList(this.containers);
    }

    public List<NPC> getNPCs() {
        return new ArrayList(this.npcs);
    }

    public List<Puzzle> getPuzzles() {
        return new ArrayList(this.puzzles);
    }

    public Set<String> getExitDirections() {
        return this.exits.keySet();
    }
}
