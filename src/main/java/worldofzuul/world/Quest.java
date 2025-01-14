package worldofzuul.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import worldofzuul.item.Item;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Quest {
    private final BooleanProperty questComplete = new SimpleBooleanProperty(false);
    private List<Item> rewards = new LinkedList<>();
    private String questTitle;
    private String introductionMessage;
    private String completionMessage;
    private Class<?> turnInClass;
    private Float turnInQuantity;

    public Quest() {
    }

    public Command[] turnIn(Item item) {
        List<Command> commands = new LinkedList<>();

        if (turnInClass.isInstance(item)) {

            if (item.getRemaining() >= turnInQuantity) {
                item.deplete(turnInQuantity);
                commands.addAll(completeQuest());
            } else if (item.getRemaining() > 0) {
                float amountTurnedIn = item.getRemaining();
                System.out.println("You turned in " + amountTurnedIn + " " + item.getName() + "s.");
                item.deplete(amountTurnedIn);
                turnInQuantity -= amountTurnedIn;


            } else {
                System.out.println("You can't turn in nothing!");
            }

            if (item.getRemaining() <= 0) {
                commands.add(new Command(CommandWord.REMOVE_ITEM, null, item));
            }

        } else {
            System.out.println("Bring me " + turnInQuantity + " " + turnInClass.getSimpleName() + "s instead.");
        }

        return commands.toArray(new Command[0]);
    }

    private List<Command> completeQuest() {
        turnInQuantity = 0f;
        questComplete.set(true);

        List<Command> commands = new LinkedList<>();

        for (Item reward : rewards) {
            commands.add(new Command(CommandWord.ADD_ITEM, null, reward));
        }

        System.out.println(completionMessage);

        return commands;
    }


    public List<Item> getRewards() {
        return rewards;
    }

    public void setRewards(List<Item> rewards) {
        this.rewards = rewards;
    }

    public String getQuestTitle() {
        return questTitle;
    }

    public void setQuestTitle(String questTitle) {
        this.questTitle = questTitle;
    }

    public String getIntroductionMessage() {
        return introductionMessage;
    }

    public void setIntroductionMessage(String introductionMessage) {
        this.introductionMessage = introductionMessage;
    }

    public String getCompletionMessage() {
        return completionMessage;
    }

    public void setCompletionMessage(String completionMessage) {
        this.completionMessage = completionMessage;
    }

    @JsonIgnore
    public Class<?> getTurnInClass() {
        return turnInClass;
    }

    @JsonIgnore
    public void setTurnInClass(Class<?> turnInClass) {
        this.turnInClass = turnInClass;
    }

    public String getTurnInType() {
        return turnInClass.getName();
    }

    public void setTurnInType(String turnInType) {
        try {
            turnInClass = Class.forName(turnInType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Float getTurnInQuantity() {
        return turnInQuantity;
    }

    public void setTurnInQuantity(Float turnInQuantity) {
        this.turnInQuantity = turnInQuantity;
    }

    @JsonIgnore
    public boolean isQuestComplete() {
        return questComplete.get();
    }

    @JsonIgnore
    public void setQuestComplete(boolean questComplete) {
        this.questComplete.set(questComplete);
    }

    @JsonIgnore
    public BooleanProperty questCompleteProperty() {
        return questComplete;
    }

    public void configureImages(HashMap<String, Image> images) {
        for (Item reward : rewards) {
            reward.configureImages(images);
        }
    }


}
