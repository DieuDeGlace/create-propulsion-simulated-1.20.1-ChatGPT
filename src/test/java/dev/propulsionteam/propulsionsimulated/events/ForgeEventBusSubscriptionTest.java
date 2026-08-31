package dev.propulsionteam.propulsionsimulated.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import dev.propulsionteam.propulsionsimulated.client.render.plume.PlumeShaders;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.IModBusEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

class ForgeEventBusSubscriptionTest {
    @Test
    void forgeModBusHandlersKeepExplicitModBusRouting() throws IOException {
        assertModBusHandler(PlumeShaders.class, "registerShaders", RegisterShadersEvent.class);
        assertModBusHandler(
                PropulsionModConfigEvents.class,
                "onCommonConfigReload",
                ModConfigEvent.Reloading.class
        );
    }

    private static void assertModBusHandler(
            Class<?> owner,
            String methodName,
            Class<? extends Event> eventType
    ) throws IOException {
        assertTrue(
                IModBusEvent.class.isAssignableFrom(eventType),
                () -> eventType.getName() + " must remain a Forge mod-bus event"
        );

        ClassNode classNode = readClass(owner);
        AnnotationNode subscriber = findAnnotation(
                classNode.visibleAnnotations,
                Type.getDescriptor(EventBusSubscriber.class)
        );
        assertNotNull(subscriber, () -> owner.getName() + " must remain an automatic subscriber");

        Object busValue = annotationValue(subscriber, "bus");
        assertTrue(
                busValue instanceof String[],
                () -> owner.getName() + " must explicitly select MOD because Forge 47 defaults to FORGE"
        );
        assertEquals("MOD", ((String[]) busValue)[1]);

        String descriptor = Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(eventType));
        MethodNode handler = classNode.methods.stream()
                .filter(method -> method.name.equals(methodName) && method.desc.equals(descriptor))
                .findFirst()
                .orElse(null);
        assertNotNull(handler, () -> owner.getName() + " must retain handler " + methodName);
        assertTrue((handler.access & Opcodes.ACC_STATIC) != 0);
        assertNotNull(findAnnotation(handler.visibleAnnotations, Type.getDescriptor(SubscribeEvent.class)));
    }

    private static ClassNode readClass(Class<?> type) throws IOException {
        String resourceName = "/" + Type.getInternalName(type) + ".class";
        try (InputStream input = type.getResourceAsStream(resourceName)) {
            assertNotNull(input, () -> "Missing class resource " + resourceName);
            ClassNode node = new ClassNode();
            new ClassReader(input).accept(
                    node,
                    ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES
            );
            return node;
        }
    }

    private static AnnotationNode findAnnotation(List<AnnotationNode> annotations, String descriptor) {
        if (annotations == null) {
            return null;
        }
        return annotations.stream()
                .filter(annotation -> annotation.desc.equals(descriptor))
                .findFirst()
                .orElse(null);
    }

    private static Object annotationValue(AnnotationNode annotation, String name) {
        if (annotation.values == null) {
            return null;
        }
        for (int index = 0; index < annotation.values.size(); index += 2) {
            if (name.equals(annotation.values.get(index))) {
                return annotation.values.get(index + 1);
            }
        }
        return null;
    }
}
