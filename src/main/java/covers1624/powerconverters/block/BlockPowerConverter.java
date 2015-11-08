package covers1624.powerconverters.block;

import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.item.DebugItem;
import covers1624.powerconverters.network.PacketPipeline;
import covers1624.powerconverters.tile.main.TileEntityBridgeComponent;
import covers1624.powerconverters.tile.main.TileEntityEnergyBridge;
import covers1624.powerconverters.util.INeighboorUpdateTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPowerConverter extends BlockContainer {
	protected IIcon[] _icons;

	protected boolean isGettingRedstone;

	public BlockPowerConverter(int metaCount) {
		super(Material.iron);
		setHardness(1.0F);
		_icons = new IIcon[metaCount * 2];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int offset = ((TileEntityBridgeComponent<?>) world.getTileEntity(x, y, z)).isSideConnectedClient(side) ? 1 : 0;
		return _icons[world.getBlockMetadata(x, y, z) * 2 + offset];
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		return _icons[metadata * 2];
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par1) {
		return null;
	}

	@Override
	public int damageDropped(int par1) {
		return par1;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		checkRedstone(world, x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof INeighboorUpdateTile) {
			((INeighboorUpdateTile) te).onNeighboorChanged();
			world.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {

		try {
			if (player.getHeldItem().getItem() instanceof DebugItem) {
				return false;
			}
		} catch (Exception e) {
		}
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityBridgeComponent<?>) {
			TileEntityEnergyBridge bridge = ((TileEntityBridgeComponent<?>) te).getFirstBridge();
			if (bridge != null) {
				if (!world.isRemote) {
					PacketPipeline.INSTANCE.sendTo(bridge.getNetPacket(), (EntityPlayerMP) player);
				}
				player.openGui(PowerConverters.instance, 0, world, bridge.xCoord, bridge.yCoord, bridge.zCoord);
			}
		}
		return true;
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean getWeakChanges(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		checkRedstone(world, x, y, z);
	}

	public boolean isGettingRedstone() {
		return isGettingRedstone;
	}

	private void checkRedstone(World world, int x, int y, int z) {
		if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
			isGettingRedstone = true;
		} else {
			isGettingRedstone = false;
		}
	}

}
