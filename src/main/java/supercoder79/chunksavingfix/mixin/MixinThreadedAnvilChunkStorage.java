package supercoder79.chunksavingfix.mixin;

import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.ReadOnlyChunk;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(ThreadedAnvilChunkStorage.class)
public class MixinThreadedAnvilChunkStorage {
    // TODO: hits both at the moment- check and re-evaluate
    @ModifyArg(method = "save(Z)V", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;", ordinal = 0), require = 0)
    private Predicate<ChunkHolder> alwaysAccessibleFlush(Predicate<ChunkHolder> chunkHolder) {
        return c -> true;
    }
    @ModifyArg(method = "save(Z)V", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;", ordinal = 1), require = 0)
    private Predicate<Chunk> allowProtoChunkFlush(Predicate<Chunk> chunk) {
        return c -> c instanceof ProtoChunk || c instanceof ReadOnlyChunk || c instanceof WorldChunk;
    }

    @ModifyArg(method = "save(Z)V", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;", ordinal = 3), require = 0)
    private Predicate<ChunkHolder> alwaysAccessible(Predicate<ChunkHolder> chunkHolder) {
        return c -> true;
    }
}
