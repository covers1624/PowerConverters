package covers1624.powerconverters.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class FlatBedrockWorldGen implements IWorldGenerator {

	private static Block netherrack = Blocks.netherrack;
	private static Block stone = Blocks.stone;
	private static Block bedrock = Blocks.bedrock;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

		BiomeGenBase b = world.getBiomeGenForCoords(chunkX, chunkZ);
		boolean isNether = b.biomeName.toLowerCase().equals("hell");

		for (int blockX = 0; blockX < 16; blockX++) {

			for (int blockZ = 0; blockZ < 16; blockZ++) {

				if (isNether) {
					// Checks roof of nether
					for (int blockY = 126; blockY > 121; blockY--) {

						if (world.getBlock(chunkX * 16 + blockX, blockY, chunkZ * 16 + blockZ) == bedrock) {

							world.setBlock(chunkX * 16 + blockX, blockY, chunkZ * 16 + blockZ, netherrack, 0, 2);
						}
					}
				}
				// checks floor of world in nether and overworld
				for (int blockY = 5; blockY > 0; blockY--) {

					if (world.getBlock(chunkX * 16 + blockX, blockY, chunkZ * 16 + blockZ) == bedrock) {

						if (isNether) {

							world.setBlock(chunkX * 16 + blockX, blockY, chunkZ * 16 + blockZ, netherrack, 0, 2);
						} else {
							world.setBlock(chunkX * 16 + blockX, blockY, chunkZ * 16 + blockZ, stone, 0, 2);
						}
					}
				}

			}

		}

	}

}
