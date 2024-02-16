package dev.sterner.voidbound;

import com.sammy.malum.MalumMod;
import dev.sterner.voidbound.common.packet.ParticleEffectPacket;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;

@Mod.EventBusSubscriber(modid = MalumMod.MALUM, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PacketRegistry {
    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel VOIDBOUND_CHANNEL = NetworkRegistry.newSimpleChannel(Voidbound.INSTANCE.id("main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void registerNetworkStuff(FMLCommonSetupEvent event) {
        int index = 0;
        VOIDBOUND_CHANNEL.registerMessage(index++, ParticleEffectPacket.class, ParticleEffectPacket::encode, ParticleEffectPacket::decode, LodestoneClientPacket::handle);

    }
}