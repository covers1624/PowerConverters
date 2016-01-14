package covers1624.powerconverters.util;

import net.minecraft.launchwrapper.LaunchClassLoader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by covers1624 on 10/24/2015.
 * TODO Move to lib.
 */
public class ASMUtils {

	public static Boolean obfuscated = null;

	public static void printInsnList(InsnList list) {
		for (int i = 0; i < list.size(); i++) {
			AbstractInsnNode node = list.get(i);
			if (node instanceof LabelNode) {
				LabelNode labelNode = (LabelNode) node;
				LogHelper.info("LabelNode: Opcode: %s Type: %s", labelNode.getOpcode(), labelNode.getType());
				continue;
			}
			if (node instanceof LineNumberNode) {
				LineNumberNode numberNode = (LineNumberNode) node;
				LogHelper.info("LineNumberNode: Opcode: %s Line: %s", numberNode.getOpcode(), numberNode.line);
				continue;
			}
			if (node instanceof VarInsnNode) {
				VarInsnNode insnNode = (VarInsnNode) node;
				LogHelper.info("VarInsnNode: Opcode: %s Var: ", insnNode.getOpcode(), insnNode.var);
				continue;
			}
			if (node instanceof FieldInsnNode) {
				FieldInsnNode fieldInsnNode = (FieldInsnNode) node;
				LogHelper.info("FieldInsnNode: Opcode: Name: %s Desc: %s", fieldInsnNode.getOpcode(), fieldInsnNode.name, fieldInsnNode.desc);
				continue;
			}
			if (node instanceof MethodInsnNode) {
				MethodInsnNode methodInsnNode = (MethodInsnNode) node;
				LogHelper.info("MethodInsnNode: Opcode: %s Name: %s", methodInsnNode.getOpcode(), methodInsnNode.name);
				continue;
			}
			if (node instanceof InsnNode) {
				InsnNode insnNode = (InsnNode) node;
				LogHelper.info("InsnNode: Opcode: %s", insnNode.getOpcode());
				continue;
			}
			LogHelper.info(node);
		}
	}

	public static MethodNode findMethodNodeOfClass(ClassNode classNode, String methodName, String methodDesc) {
		for (MethodNode method : classNode.methods) {
			if (method.name.equals(methodName) && (methodDesc == null || method.desc.equals(methodDesc))) {
				return method;
			}
		}
		return null;
	}

	public static void writeClassToFile(ClassWriter classWriter, String name) {
		try {
			File classFolder = new File(".", "tempClasses");
			if (!classFolder.exists()) {
				classFolder.mkdir();
			}
			File classFile = new File(classFolder, name + ".class");
			FileOutputStream outputStream = new FileOutputStream(classFile);
			outputStream.write(classWriter.toByteArray());
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isObfuscated() {
		if (obfuscated == null) {
			try {
				byte[] bytes = ((LaunchClassLoader) ASMUtils.class.getClassLoader()).getClassBytes("net.minecraft.world.World");
				obfuscated = bytes == null;
			} catch (IOException ignored) {
				obfuscated = false;
			}
		}
		return obfuscated;
	}
}
