package homework1;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class TestRunner {
    public static void main(String[] args) throws Exception {
        runTests(TestedClass.class);
    }

    public static void runTests(Class c) throws Exception {
        Method beforeSuiteMethod = null;
        Method afterSuiteMethod = null;
        Set<Method>[] testMethodsWithPriority = new Set[10];

        for (Method method : c.getMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("Метод " + method.getName() + " должен быть статичным");
                }
                if (beforeSuiteMethod != null) {
                    throw new RuntimeException("Аннотация " + BeforeSuite.class.getSimpleName() + " должна быть использована 1 раз");
                }
                beforeSuiteMethod = method;
            }

            if (method.isAnnotationPresent(AfterSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("Метод " + method.getName() + " должен быть статичным");
                }
                if (afterSuiteMethod != null) {
                    throw new RuntimeException("Аннотация " + AfterSuite.class.getSimpleName() + " должна быть использована 1 раз");
                }
                afterSuiteMethod = method;
            }

            if (method.isAnnotationPresent(Test.class)) {
                byte priority = method.getAnnotation(Test.class).priority();
                if (priority < 1 || priority > 10) {
                    throw new RuntimeException("Приоритет метода " + method.getName() + " должен быть в пределах от 1 до 10 включительно");
                }
                Set<Method> currentPrioritySet = testMethodsWithPriority[priority - 1];
                if (currentPrioritySet == null) {
                    currentPrioritySet = new HashSet<>();
                }
                currentPrioritySet.add(method);
                testMethodsWithPriority[priority - 1] = currentPrioritySet;
            }
        }

        if (beforeSuiteMethod != null) {
            beforeSuiteMethod.invoke(null);
        }

        for (int i = 10; i > 0; i--) {
            if (testMethodsWithPriority[i - 1] != null) {
                for (Method method1 : testMethodsWithPriority[i - 1]) {
                    method1.invoke(c.getConstructor().newInstance());
                }
            }
        }

        if (afterSuiteMethod != null) {
            afterSuiteMethod.invoke(null);
        }
    }
}
