package bssentials.fabric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;

import bssentials.api.User;
import bssentials.commands.BCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class CommandWrapper implements com.mojang.brigadier.Command<ServerCommandSource>, Predicate<ServerCommandSource>, SuggestionProvider<ServerCommandSource> {

    private static final Pattern PATTERN_ON_SPACE = Pattern.compile(" ", Pattern.LITERAL);

    private final String name;
    private final BCommand ex;

    public CommandWrapper(String name, BCommand command) {
        this.name = name;
        this.ex = command;
    }

    public LiteralCommandNode<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher, String label) {
        return dispatcher.register(
                LiteralArgumentBuilder.<ServerCommandSource>literal(label).requires(this).executes(this)
                .then(RequiredArgumentBuilder.<ServerCommandSource, String>argument("args", StringArgumentType.greedyString()).suggests(this).executes(this))
        );
    }

    @Override
    public boolean test(ServerCommandSource wrapper) {
        return wrapper.hasPermissionLevel(1);
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try {
            if (ex.onlyPlayer && !(context.getSource().getEntity() instanceof ServerPlayerEntity)){
                context.getSource().getEntity().sendSystemMessage(new LiteralText("Player only command!"), UUID.randomUUID());
                return 0;
            }
            String[] args = PATTERN_ON_SPACE.split(context.getInput());
            String sentCommandLabel = args[0].toLowerCase();

            User user = context.getSource().getEntity() != null ? ex.getUserByName(context.getSource().getName()) : BssentialsMod.CONSOLE_USER;
            if (!ex.hasPerm(user, name)){
                user.sendMessage("&4No permission for command.");
                return 0;
            }

            return ex.onCommand(user, sentCommandLabel, Arrays.copyOfRange(args, 1, args.length)) ? 1 : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        List<String> results = new ArrayList<>(); // todo tab complete

        // Defaults to sub nodes, but we have just one giant args node, so offset accordingly
        builder = builder.createOffset(builder.getInput().lastIndexOf(' ') + 1);

        for (String s : results)
            builder.suggest(s);

        return builder.buildFuture();
    }

}