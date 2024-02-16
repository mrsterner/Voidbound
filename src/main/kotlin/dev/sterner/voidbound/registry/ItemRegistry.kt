package dev.sterner.voidbound.registry

import com.sammy.malum.registry.common.item.ItemRegistry.GEAR_PROPERTIES
import dev.sterner.voidbound.Voidbound.MODID
import dev.sterner.voidbound.common.item.AllBlackItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Tiers
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import java.util.function.Function


object ItemRegistry {

    private val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)

    val ALL_BLACK: RegistryObject<Item> =
        register("all_black", GEAR_PROPERTIES()) { p -> AllBlackItem(Tiers.IRON, 4, -2f, p!!.durability(500)) }


    fun <T : Item?> register(name: String?, properties: Item.Properties, function: Function<Item.Properties?, T>): RegistryObject<Item> {

        return ITEMS.register(name) { function.apply(properties) as Item }
    }

    fun register(bus: IEventBus) = ITEMS.register(bus)

}