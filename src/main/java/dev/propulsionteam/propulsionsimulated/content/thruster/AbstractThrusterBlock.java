package dev.propulsionteam.propulsionsimulated.content.thruster;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import dev.propulsionteam.propulsionsimulated.PropulsionConfig;
import dev.propulsionteam.propulsionsimulated.utility.DirectionalPlacement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractThrusterBlock extends DirectionalBlock implements IBE<AbstractThrusterBlockEntity>, IWrenchable {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    protected AbstractThrusterBlock(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        final Direction baseDirection = DirectionalPlacement.nearestLookingDirection(context);
        final Player player = context.getPlayer();
        final Direction placeDirection;
        if (player != null) {
            placeDirection = !player.isShiftKeyDown() ? baseDirection : baseDirection.getOpposite();
        } else {
            placeDirection = baseDirection.getOpposite();
        }
        return this.defaultBlockState().setValue(FACING, placeDirection);
    }

    @Override
    public BlockState rotate(final BlockState state, final Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(final BlockState state, final Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public RenderShape getRenderShape(final BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getCollisionShape(final BlockState state,
                                           final BlockGetter level,
                                           final BlockPos pos,
                                           final CollisionContext context) {
        return this.getShape(state, level, pos, context);
    }

    @Override
    public InteractionResult use(final BlockState state,
                                 final Level level,
                                 final BlockPos pos,
                                 final Player player,
                                 final InteractionHand hand,
                                 final BlockHitResult hitResult) {
        final ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty()) {
            String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
            if (PropulsionConfig.isDyeConfigured(itemId)) {
                if (!level.isClientSide()) {
                    if (level.getBlockEntity(pos) instanceof AbstractThrusterBlockEntity be) {
                        String currentDye = be.getDyeId();
                        if (currentDye != null && !currentDye.equals(itemId)) {
                            Block.popResource(level, pos, new ItemStack(
                                BuiltInRegistries.ITEM.get(ResourceLocation.parse(currentDye))));
                        }
                        if (!itemId.equals(currentDye)) {
                            be.setDyeId(itemId);
                            if (!player.isCreative()) {
                                stack.shrink(1);
                            }
                        }
                    }
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        final InteractionResult interactionResult = this.onBlockEntityUse(level, pos,
                be -> be.tryConsumeFuelBucket(player, hand, stack)
                        ? InteractionResult.sidedSuccess(level.isClientSide())
                        : InteractionResult.PASS);
        if (interactionResult != InteractionResult.PASS) {
            return interactionResult;
        }
        if (stack.isEmpty()) {
            if (!level.isClientSide()) {
                if (level.getBlockEntity(pos) instanceof AbstractThrusterBlockEntity be) {
                    String currentDye = be.getDyeId();
                    if (currentDye != null) {
                        be.setDyeId(null);
                        Block.popResource(level, pos, new ItemStack(
                                BuiltInRegistries.ITEM.get(new ResourceLocation(currentDye))));
                        return InteractionResult.SUCCESS;
                    }
                }
            } else if (level.getBlockEntity(pos) instanceof AbstractThrusterBlockEntity be
                    && be.getDyeId() != null) {
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public void onPlace(final BlockState state,
                        final Level level,
                        final BlockPos pos,
                        final BlockState oldState,
                        final boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide()) {
            this.withBlockEntityDo(level, pos, be -> be.setRedstonePower(level.getBestNeighborSignal(pos)));
        }
    }

    @Override
    public void neighborChanged(final BlockState state,
                                final Level level,
                                final BlockPos pos,
                                final Block neighborBlock,
                                final BlockPos neighborPos,
                                final boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        if (!level.isClientSide()) {
            this.withBlockEntityDo(level, pos, be -> be.setRedstonePower(level.getBestNeighborSignal(pos)));
        }
    }

    @Override
    public void onRemove(final BlockState state,
                         final Level level,
                         final BlockPos pos,
                         final BlockState newState,
                         final boolean movedByPiston) {
        IBE.onRemove(state, level, pos, newState);
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    public boolean isCreativeVariant() {
        return false;
    }

    public void doRedstoneCheck(Level level, BlockState state, BlockPos pos) {
        this.withBlockEntityDo(level, pos, be -> be.setRedstonePower(level.getBestNeighborSignal(pos)));
    }
}

