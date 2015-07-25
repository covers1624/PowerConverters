package covers1624.powerconverters.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import covers1624.powerconverters.PowerConverters;
import covers1624.powerconverters.gui.PCCreativeTab;
import covers1624.powerconverters.item.DebugItem;
import covers1624.powerconverters.reference.Reference;
import covers1624.powerconverters.tile.main.TileEntityBridgeComponent;
import covers1624.powerconverters.tile.main.TileEntityCharger;
import covers1624.powerconverters.tile.main.TileEntityEnergyBridge;
import covers1624.powerconverters.util.INeighboorUpdateTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPowerConverterCommon extends BlockContainer {
	private IIcon _iconBridge;
	private IIcon _iconChargerOn;
	private IIcon _iconChargerOff;

	public BlockPowerConverterCommon() {
		super(Material.iron);
		setHardness(1.0F);
		setBlockName("powerconverters.common");
		setCreativeTab(PCCreativeTab.tab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir) {
		_iconBridge = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".bridge");
		_iconChargerOn = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".charger.on");
		_iconChargerOff = ir.registerIcon(Reference.MOD_PREFIX + getUnlocalizedName() + ".charger.off");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta == 0)
			return _iconBridge;
		else if (meta == 2)
			return _iconChargerOff;

		return null;
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityBridgeComponent<?>) {
			if (meta == 0) {
				return _iconBridge;
			} else if (meta == 2) {
				boolean isConnected = ((TileEntityBridgeComponent<?>) te).isSideConnectedClient(side);
				if (isConnected) {
					return _iconChargerOn;
				} else {
					return _iconChargerOff;
				}
			}
		}

		return getIcon(side, meta);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof INeighboorUpdateTile) {
			((INeighboorUpdateTile) te).onNeighboorChanged();
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int md) {
		if (md == 0)
			return new TileEntityEnergyBridge();
		if (md == 2)
			return new TileEntityCharger();
		return null;
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
		if (te != null && te instanceof TileEntityCharger) {
			player.openGui(PowerConverters.instance, 1, world, x, y, z);
			return true;
		}
		if (te != null && te instanceof TileEntityBridgeComponent<?>) {
			TileEntityEnergyBridge bridge = ((TileEntityBridgeComponent<?>) te).getFirstBridge();
			if (bridge != null) {
				player.openGui(PowerConverters.instance, 0, world, bridge.xCoord, bridge.yCoord, bridge.zCoord);
			}
		} else if (te != null && te instanceof TileEntityEnergyBridge) {
			player.openGui(PowerConverters.instance, 0, world, x, y, z);
		}
		return true;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 2) {
			float shrinkAmount = 0.125F;
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1 - shrinkAmount, z + 1);
		} else {
			return super.getCollisionBoundingBoxFromPool(world, x, y, z);
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		// if (entity instanceof EntityPlayer && world.getBlockMetadata(x, y, z) == 2) {
		// TileEntityCharger charger = (TileEntityCharger) world.getTileEntity(x, y, z);
		// charger.setPlayer((EntityPlayer) entity);
		// }
	}
}
