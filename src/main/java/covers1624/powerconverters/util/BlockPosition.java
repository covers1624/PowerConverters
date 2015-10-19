package covers1624.powerconverters.util;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class BlockPosition {
	public int x;
	public int y;
	public int z;
	public ForgeDirection orientation;

	public static final int[][] SIDE_COORD_MOD = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };

	public BlockPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		orientation = ForgeDirection.UNKNOWN;
	}

	public BlockPosition(int x, int y, int z, ForgeDirection corientation) {
		this.x = x;
		this.y = y;
		this.z = z;
		orientation = corientation;
	}

	public BlockPosition(BlockPosition p) {
		x = p.x;
		y = p.y;
		z = p.z;
		orientation = p.orientation;
	}

	public BlockPosition(NBTTagCompound nbttagcompound) {
		x = nbttagcompound.getInteger("i");
		y = nbttagcompound.getInteger("j");
		z = nbttagcompound.getInteger("k");

		orientation = ForgeDirection.UNKNOWN;
	}

	public BlockPosition(TileEntity tile) {
		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
		orientation = ForgeDirection.UNKNOWN;
	}

	public BlockPosition copy() {
		return new BlockPosition(x, y, z, orientation);
	}

	public BlockPosition step(int dir, int dist) {

		int[] d = SIDE_COORD_MOD[dir];
		x += d[0] * dist;
		y += d[1] * dist;
		z += d[2] * dist;
		return this;
	}

	public BlockPosition step(ForgeDirection dir) {

		x += dir.offsetX;
		y += dir.offsetY;
		z += dir.offsetZ;
		return this;
	}

	public BlockPosition step(ForgeDirection dir, int dist) {

		x += dir.offsetX * dist;
		y += dir.offsetY * dist;
		z += dir.offsetZ * dist;
		return this;
	}

	public void moveRight(int step) {
		switch (orientation) {
		case SOUTH:
			x = x - step;
			break;
		case NORTH:
			x = x + step;
			break;
		case EAST:
			z = z + step;
			break;
		case WEST:
			z = z - step;
			break;
		default:
			break;
		}
	}

	public void moveLeft(int step) {
		moveRight(-step);
	}

	public void moveForwards(int step) {
		switch (orientation) {
		case UP:
			y = y + step;
			break;
		case DOWN:
			y = y - step;
			break;
		case SOUTH:
			z = z + step;
			break;
		case NORTH:
			z = z - step;
			break;
		case EAST:
			x = x + step;
			break;
		case WEST:
			x = x - step;
			break;
		default:
		}
	}

	public void moveBackwards(int step) {
		moveForwards(-step);
	}

	public void moveUp(int step) {
		switch (orientation) {
		case EAST:
		case WEST:
		case NORTH:
		case SOUTH:
			y = y + step;
			break;
		default:
			break;
		}

	}

	public void moveDown(int step) {
		moveUp(-step);
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setDouble("i", x);
		nbttagcompound.setDouble("j", y);
		nbttagcompound.setDouble("k", z);
	}

	@Override
	public String toString() {
		if (orientation == null) {
			return "{" + x + ", " + y + ", " + z + "}";
		}
		return "{" + x + ", " + y + ", " + z + ";" + orientation.toString() + "}";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BlockPosition)) {
			return false;
		}
		BlockPosition bp = (BlockPosition) obj;
		return bp.x == x && bp.y == y && bp.z == z && bp.orientation == orientation;
	}

	@Override
	public int hashCode() {
		return (x & 0xFFF) | (y & 0xFF << 8) | (z & 0xFFF << 12);
	}

	public BlockPosition min(BlockPosition p) {
		return new BlockPosition(p.x > x ? x : p.x, p.y > y ? y : p.y, p.z > z ? z : p.z);
	}

	public BlockPosition max(BlockPosition p) {
		return new BlockPosition(p.x < x ? x : p.x, p.y < y ? y : p.y, p.z < z ? z : p.z);
	}

	public List<BlockPosition> getAdjacent(boolean includeVertical) {
		List<BlockPosition> a = new ArrayList<BlockPosition>();
		a.add(new BlockPosition(x + 1, y, z, ForgeDirection.EAST));
		a.add(new BlockPosition(x - 1, y, z, ForgeDirection.WEST));
		a.add(new BlockPosition(x, y, z + 1, ForgeDirection.SOUTH));
		a.add(new BlockPosition(x, y, z - 1, ForgeDirection.NORTH));
		if (includeVertical) {
			a.add(new BlockPosition(x, y + 1, z, ForgeDirection.UP));
			a.add(new BlockPosition(x, y - 1, z, ForgeDirection.DOWN));
		}
		return a;
	}

	public TileEntity getTileEntity(World world) {
		return world.getTileEntity(x, y, z);
	}

	public Block getBlock(World world) {

		return world.getBlock(x, y, z);
	}

	@SuppressWarnings("unchecked")
	public <T> T getTileEntity(World world, Class<T> targetClass) {

		TileEntity te = world.getTileEntity(x, y, z);
		if (targetClass.isInstance(te)) {
			return (T) te;
		} else {
			return null;
		}
	}

	public static TileEntity getTileEntityRaw(World world, int x, int y, int z) {

		if (!world.blockExists(x, y, z)) {
			return null;
		}
		return world.getChunkFromBlockCoords(x, z).getTileEntityUnsafe(x & 15, y, z & 15);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getTileEntityRaw(World world, int x, int y, int z, Class<T> targetClass) {

		TileEntity te = getTileEntityRaw(world, x, y, z);
		if (targetClass.isInstance(te)) {
			return (T) te;
		} else {
			return null;
		}
	}

	public static boolean blockExists(TileEntity start, ForgeDirection dir) {

		final int x = start.xCoord + dir.offsetX, y = start.yCoord + dir.offsetY, z = start.zCoord + dir.offsetZ;
		return start.getWorldObj().blockExists(x, y, z);
	}

	public static TileEntity getAdjacentTileEntity(TileEntity start, ForgeDirection direction) {
		BlockPosition p = new BlockPosition(start);
		p.orientation = direction;
		p.moveForwards(1);
		return start.getWorldObj().getTileEntity(p.x, p.y, p.z);
	}

	public static <T> T getAdjacentTileEntity(TileEntity start, ForgeDirection direction, Class<T> targetClass) {
		TileEntity te = getAdjacentTileEntity(start, direction);
		if (targetClass.isInstance(te)) {
			return (T) te;
		} else {
			return null;
		}
	}
}