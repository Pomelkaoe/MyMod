package com.example.mymod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

@Mod("mymod")
public class MyMod {
    private BlockEvent.BreakEvent event;

    public MyMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.@NotNull BreakEvent event) {
        this.event = event;
        if (event.getState().getBlock() == Blocks.DIRT) {
            World world = event.getWorld();
            BlockPos spawnPos = new BlockPos(0, -60, 0);
            CowEntity cow = new CowEntity(EntityType.COW, world);
            cow.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
            world.addEntity(cow);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onEntityInteract(EntityEvent event) {
        if (event instanceof PlayerEvent.Interaction) {
            PlayerEvent.Interaction interaction = (PlayerEvent.Interaction) event;
            PlayerEntity player = interaction.getPlayer();
            if (interaction.getTarget() instanceof CowEntity) {
                cow = (CowEntity) interaction.getTarget();
                player.sendMessage(new StringTextComponent("Привет, " + player.getDisplayName().getString() + "!"), player.getUUID());
            }
        }
    }
}
