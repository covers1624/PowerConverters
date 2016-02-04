package covers1624.powerconverters.randomdebugshit;

import covers1624.lib.asm.ASMUtils;
import covers1624.powerconverters.util.LogHelper;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

/**
 * Created by covers1624 on 12/30/2015.
 * Hack class because for some reason CoFH Core's Access Transformer wont run in a dev env.
 */
public class CoFHDevEnvHacks implements IClassTransformer {
	@Override
	public byte[] transform(String name, String s1, byte[] bytes) {

		if (!ASMUtils.obfuscated) {
			try {
				if (name.equals("net.minecraft.world.World")) {
					LogHelper.info("Fixing CoFH Dev ENV Crash..");
					ClassNode classNode = new ClassNode();
					ClassReader classReader = new ClassReader(bytes);
					ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
					classReader.accept(classNode, 0);
					FieldNode fieldNode = null;
					for (FieldNode node : classNode.fields) {
						//LogHelper.info(node.name + "    " + node.access);
						if (node.name.equals("collidingBoundingBoxes")) {
							LogHelper.info("Found Field!");
							fieldNode = node;
							break;
						}
					}
					if (fieldNode != null) {
						fieldNode.access = Opcodes.ACC_PUBLIC;
					} else {
						LogHelper.error("Unable to transform field!");
						throw new Exception();
					}
					LogHelper.info("CoFH Fixes were a success.");
					classNode.accept(classWriter);
					return classWriter.toByteArray();
				}
			} catch (Exception ignored) {
				LogHelper.info("Something broked..");
				ignored.printStackTrace();
				return bytes;
			}
		}

		return bytes;
	}
}
