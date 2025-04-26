//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package AdventureGameJava.src.com.adventure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int MAX_INVENTORY_WEIGHT = 10;
    private List<Item> inventory = new ArrayList();
    private int currentInventoryWeight = 0;
    private List<String> visitedZones = new ArrayList();
    private List<String> notes = new ArrayList();

    public Player() {
    }

    public boolean addItem(Item item) {
        if (this.currentInventoryWeight + item.getWeight() <= 10) {
            this.inventory.add(item);
            this.currentInventoryWeight += item.getWeight();
            return true;
        } else {
            return false;
        }
    }

    public Item removeItem(String itemName) {
        for(int i = 0; i < this.inventory.size(); ++i) {
            if (((Item)this.inventory.get(i)).getName().equalsIgnoreCase(itemName)) {
                Item item = (Item)this.inventory.remove(i);
                this.currentInventoryWeight -= item.getWeight();
                return item;
            }
        }

        return null;
    }

    public Item getItem(String itemName) {
        for(Item item : this.inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }

        return null;
    }

    public boolean hasItem(String itemName) {
        for(Item item : this.inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }

        return false;
    }

    public String getInventoryString() {
        if (this.inventory.isEmpty()) {
            return "Votre inventaire est vide.";
        } else {
            StringBuilder sb = new StringBuilder("Inventaire (");
            sb.append(this.currentInventoryWeight).append("/").append(10);
            sb.append(" poids):\n");

            for(Item item : this.inventory) {
                sb.append("- ").append(item.toString()).append("\n");
            }

            return sb.toString();
        }
    }

    public List<Item> getInventory() {
        return new ArrayList(this.inventory);
    }

    public int getCurrentInventoryWeight() {
        return this.currentInventoryWeight;
    }

    public void visitZone(String zoneName) {
        if (!this.visitedZones.contains(zoneName)) {
            this.visitedZones.add(zoneName);
        }

    }

    public boolean hasVisitedZone(String zoneName) {
        return this.visitedZones.contains(zoneName);
    }

    public void addNote(String note) {
        if (!this.notes.contains(note)) {
            this.notes.add(note);
        }

    }

    public List<String> getNotes() {
        return this.notes;
    }

    public String getNotesString() {
        if (this.notes.isEmpty()) {
            return "Vous n'avez pas encore de notes.";
        } else {
            StringBuilder sb = new StringBuilder("Vos notes:\n");

            for(String note : this.notes) {
                sb.append("- ").append(note).append("\n");
            }

            return sb.toString();
        }
    }
}
