package net.snowshowgames.macro;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class MacroCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("macro")
                        .then(CommandManager.literal("start")
                                .then(CommandManager.argument("name", StringArgumentType.word())
                                        .executes(ctx -> {
                                            String name = StringArgumentType.getString(ctx, "name");
                                            MacroRecorder.start(name);
                                            ctx.getSource().sendFeedback(new net.minecraft.text.LiteralText("Macro started: " + name), false);
                                            return 1;
                                        })))
                        .then(CommandManager.literal("stop")
                                .executes(ctx -> {
                                    MacroRecorder.stop();
                                    ctx.getSource().sendFeedback(new net.minecraft.text.LiteralText("Macro stopped"), false);
                                    return 1;
                                }))
                        .then(CommandManager.literal("play")
                                .then(CommandManager.argument("name", StringArgumentType.word())
                                        .executes(ctx -> {
                                            String name = StringArgumentType.getString(ctx, "name");
                                            MacroRecorder.play(name);
                                            ctx.getSource().sendFeedback(new net.minecraft.text.LiteralText("Playing macro: " + name), false);
                                            return 1;
                                        })))
                        .then(CommandManager.literal("list")
                                .executes(ctx -> {
                                    java.nio.file.Path dir = net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir().resolve("macrorecorder");
                                    try (java.util.stream.Stream<java.nio.file.Path> s = java.nio.file.Files.list(dir)) {
                                        StringBuilder sb = new StringBuilder();
                                        s.filter(p -> p.toString().endsWith(".json")).forEach(p -> sb.append(p.getFileName().toString()).append("\n"));
                                        ctx.getSource().sendFeedback(new net.minecraft.text.LiteralText(sb.toString()), false);
                                    } catch (Exception e) {
                                        ctx.getSource().sendFeedback(new net.minecraft.text.LiteralText("Could not list macros"), false);
                                    }
                                    return 1;
                                }))
        );
    }
}
