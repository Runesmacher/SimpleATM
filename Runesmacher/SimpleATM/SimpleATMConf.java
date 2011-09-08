package Runesmacher.SimpleATM;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.util.config.Configuration;

public class SimpleATMConf {

    private final SimpleATM plugin;
    Configuration file;
    List<Double> depositvalueList = new ArrayList<Double>();
    List<Integer> depositBlocksList = new ArrayList<Integer>();
    double withdrawValue;
    int withdrawBlock;

    public SimpleATMConf(SimpleATM instance) {
        plugin = instance;
    }

    public void loadConfig() {
        file = new Configuration(new File(plugin.getDataFolder(), "properties.yml"));
        file.load();
        if (new File(plugin.getDataFolder(), "properties.yml").exists()) {
            System.out.println("SimpleATM Configuration file loaded!");
        } else {
            file.setHeader("#SimpleATM configuration\n"
                    + "#deposit.blocks : Standard is 266,41 to be able to deposit goldblock and ingot\n"
                    + "#deposit.values : Standard is 1,9 values in same order as blocks\n"
                    + "#withdraw.block : Standard is 41 to withdraw a goldblock\n"
                    + "#withdraw.value : Standard 9 to get 9credits when you deposit");
            depositvalueList.add(1.0);
            depositvalueList.add(9.0);

            depositBlocksList.add(266);
            depositBlocksList.add(41);
            file.setProperty("deposit.values", depositvalueList);
            file.setProperty("deposit.blocks", depositBlocksList);
            file.setProperty("withdraw.value", 9);
            file.setProperty("withdraw.block", 41);
            file.save();
            System.out.println("SimpleATM Configuration file created with default values!");
        }

        //Get configs   
        depositBlocksList = file.getIntList("deposit.blocks", null);
        depositvalueList = file.getDoubleList("deposit.values", null);

        withdrawValue = file.getDouble("withdraw.value", 9);
        withdrawBlock = file.getInt("withdraw.block", 41);
    }
}
