package de.nekeras.borderless.config;

import java.util.Arrays;
import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final General GENERAL = new General(BUILDER);

    public static final ForgeConfigSpec CONFIG_SPEC = BUILDER.build();

    public static class General {

        public final ForgeConfigSpec.EnumValue<FullscreenModeConfig> fullscreenMode;

        public General(ForgeConfigSpec.Builder builder) {
            builder.push("general");

            fullscreenMode = builder
                .comment(Arrays.stream(FullscreenModeConfig.values())
                    .map(mode -> String.format("%s - %s", mode.name(), mode.getComment()))
                    .toArray(String[]::new))
                .defineEnum("fullscreenMode", FullscreenModeConfig.DEFAULT);

            builder.pop();
        }

    }

}
