package worldofzuul;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import worldofzuul.item.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Market {
    private List<Item> stock = new LinkedList<>();


    public Market() {
        this(null);



    }

   public Market(HashMap<Item, Double> stock){
       this.stock.clear();

       this.stock.addAll(Arrays.asList(
                fertilizer,
                harvester1,
                harvester2,
                harvester3,
                irrigator1,
                irrigator2,
                irrigator3,
                plant1,
                plant2,
                plant3,
                plant4,
                plant5,
                seed1,
                seed2,
                seed3,
                seed4,
                seed5
        ));




   }
   Harvester harvester1 = new Harvester("Hands", 0.0, 0.0);
   Harvester harvester2 = new Harvester("Sickle", 230.0, 0.0);
   Harvester harvester3 = new Harvester("Scythe", 2000.0,0.0);
    Fertilizer fertilizer = new Fertilizer("Fertilizer", 2, 320.0, 0.975);
    Irrigator irrigator1 = new Irrigator("Bucket", 320.0, 0.0);
    Irrigator irrigator2 = new Irrigator("Watering Can", 300.0, 0.0 );
    Irrigator irrigator3 = new Irrigator("Hose", 400.0, 0.0);
    Plant plant1 = new Plant("Corn", 2.0, 0.30);
    Plant plant2 = new Plant("Cashew", 2.0, 0.40);
    Plant plant3 = new Plant("Rice", 4.0, 0.20);
    Plant plant4 = new Plant("Mango", 10.0, 0.70);
    Plant plant5 = new Plant("Cowpea", 20.0, 0.01);
    Seed seed1 = new Seed("CornSeeds",5, 13.0, 0.98);
    Seed seed2 = new Seed("CashewSeeds", 5, 32.0, 0.98);
    Seed seed3 = new Seed("RiceSeeds" , 5, 42.0 , 0.98);
    Seed seed4 = new Seed("MangoSeeds", 5,93.0,0.98);
    Seed seed5 = new Seed("CowpeaSeeds",5,10.0, 0.98);


    public void purchaseItem(Item item, Player player) {
        if(item instanceof Sellable){
            if (player.getBalance() >= ((Sellable) item).getValue()) {
                player.setBalance(player.getBalance() - ((Sellable) item).getValue());
                player.getInventory().addItem(item);
            } else {
                System.out.println("Not enough money!");
            }
        }
    }

    public void purchaseUpgradeToItems(Item item, Player player){
        if (item instanceof Sellable) {
            if (player.getInventory().getItems().contains(irrigator1)) {
                if (player.getBalance() >= ((Sellable) item).getValue()) {
                    player.setBalance(player.getBalance() - ((Sellable) item).getValue());
                    player.getInventory().addItem(irrigator2);
                    player.getInventory().removeItem(irrigator1);
                }
            }
            if (player.getInventory().getItems().contains(irrigator2)) {
                if (player.getBalance() >= ((Sellable) item).getValue()) {
                    player.setBalance(player.getBalance() - ((Sellable) item).getValue());
                    player.getInventory().addItem(irrigator3);
                    player.getInventory().removeItem(irrigator2);
                }
            }
            if (player.getInventory().getItems().contains(harvester2)) {
                if (player.getBalance() >= ((Sellable) item).getValue()) {
                    player.setBalance(player.getBalance() - ((Sellable) item).getValue());
                    player.getInventory().addItem(harvester2);
                    player.getInventory().removeItem(harvester1);
                }
            }
            if (player.getInventory().getItems().contains(harvester3)) {
                if (player.getBalance() >= ((Sellable) item).getValue()) {
                    player.setBalance(player.getBalance() - ((Sellable) item).getValue());
                    player.getInventory().addItem(harvester3);
                    player.getInventory().removeItem(harvester2);
                }
            } else {
                System.out.println("Cannot purchase that!!!");
            }
        } else {
            return;
        }

    }

    @JsonIgnore
   public void sellItem(Item item, Player player){
        if(player.getInventory().getItems().contains(item) && item instanceof Sellable) {
            player.setBalance(player.getBalance() + (((Sellable) item).getValue() * ((Sellable) item).getSellBackRate()));
            player.getInventory().removeItem(item);
        }
        else {
            System.out.println("Player does not have that item");
        }
    }
    @JsonIgnore
   public Double getItems(Item item){
        if(item instanceof Sellable){
            for (Item value : stock) {
                System.out.println(value);
            }
            return  ((Sellable) item).getValue();
        }
        else {
            return 0d;
        }

   }


    public List<Item> getStock() {
        return stock;
    }

    public void setStock(List<Item> stock) {
        this.stock = stock;
    }
}