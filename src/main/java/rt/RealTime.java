package rt;

import cn.nukkit.level.GameRule;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class RealTime extends PluginBase {

    @Override
    public void onEnable() {
        getServer().getScheduler().scheduleRepeatingTask(this, () -> {
            for (Level level : getServer().getLevels().values()) {
                if (level.getGameRules().getBoolean(GameRule.DO_DAYLIGHT_CYCLE)) {
                    level.stopTime = true;
                    level.getGameRules().setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                }
                double d = LocalTime.now().get(ChronoField.SECOND_OF_DAY) / 86399d;
                int time = (int) (d * 24000) - 6000;
                if (time < 0) {
                    time += 24000;
                }
                level.setTime(time);
            }
        }, 40);
    }
}
