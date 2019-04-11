package com.tfar.nolavabucketing;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
@Config(modid = NoLavaBucketing.MODID)
@Mod.EventBusSubscriber(modid = NoLavaBucketing.MODID)
@Mod(modid = NoLavaBucketing.MODID, name = NoLavaBucketing.NAME, version = NoLavaBucketing.VERSION)
public class NoLavaBucketing {
  @Config.Ignore
  public static final String MODID = "nolavabucketing";
  @Config.Ignore
  public static final String NAME = "No Lava Bucketing";
  @Config.Ignore
  public static final String VERSION = "@VERSION@";

  @Config.Name("Blacklisted Fluids")
  public static String[] blacklistedFluids = {Blocks.LAVA.getRegistryName().toString()};

  @SubscribeEvent
  public static void denyLava(FillBucketEvent e) {
    RayTraceResult target = e.getTarget();
    if (target == null) return;
    if (isBlacklisted(e.getWorld().getBlockState(target.getBlockPos()).getBlock().getRegistryName().toString()) && !e.getEntityPlayer().capabilities.isCreativeMode) {
      e.setCanceled(true);
      e.getEntityPlayer().sendStatusMessage(new TextComponentString(TextFormatting.RED + "That fluid cannot picked up"),true);
    }
  }

  public static boolean isBlacklisted(String s){
    for(String str: blacklistedFluids) {
      if(str.trim().contains(s))
        return true;
    }
    return false;}
}
